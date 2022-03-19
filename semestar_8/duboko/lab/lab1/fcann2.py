from scipy.special import softmax
import numpy as np
from data import *

class FCANN2:
    def __init__(self, hldim=5, param_niter=1000, param_delta=0.05) -> None:
        self.param_niter = param_niter
        self.param_delta = param_delta
        self.hldim = hldim



    def fcann2_train(self, X, y):
        Yoh = class_to_onehot(y)
        D = X.shape[1]
        N = Yoh.shape[0]
        C = Yoh.shape[1]
        
        # mozda kasnije: normalizirati podatke

        #inicijaliziraj parametre
        self.W1 = np.random.randn(self.hldim, D)
        self.b1 = np.zeros(self.hldim)
        self.W2 = np.random.randn(C, self.hldim)
        self.b2 = np.zeros(C)

        # petlja ucenja
        for epoch in range(self.param_niter):
            # izracunaj sve mjere
            S1 = np.dot(X, np.matrix.transpose(self.W1)) + self.b1
            H1 = relu(S1)
            S2 = np.dot(H1, np.matrix.transpose(self.W2)) + self.b2
            probs = softmax(S2)

            #izracunaj gradijente
            Gs2 = probs - Yoh
            GW2 = np.dot(np.matrix.transpose(Gs2), H1) / N
            Gb2 = np.sum(np.matrix.transpose(Gs2), axis=1) / N
            #Gh1 = None
            Gs1 = np.dot(Gs2, self.W2)
            Gs1[H1 <= 0] = 0
            GW1 = np.dot(np.matrix.transpose(Gs1), X) / N
            Gb1 = np.sum(np.matrix.transpose(Gs1), axis=1) / N
            
            #podesi parametre u smjeru negativnog gradijenta
            self.W1 -= self.param_delta * GW1
            self.b1 -= self.param_delta * Gb1
            self.W2 -= self.param_delta * GW2
            self.b2 -= self.param_delta * Gb2




    def fcann2_classify(self, X):
        S1 = np.dot(X, np.matrix.transpose(self.W1)) + self.b1
        H1 = relu(S1)
        S2 = np.dot(H1, np.matrix.transpose(self.W2)) + self.b2
        return softmax(S2)



def relu(x):
    return np.where(x>0, x, 0)

if __name__ == "__main__":
    

    np.random.seed(100)
  
    # get data
    X,Y_ = sample_gmm_2d(ncomponents=6, nclasses=2, nsamples=10)
    # X,Y_ = sample_gauss_2d(2, 100)

    # get the class predictions
    model = FCANN2()
    model.fcann2_train(X, Y_)
    Y = np.argmax(model.fcann2_classify(X), axis=-1)  

    # graph the decision surface
    rect=(np.min(X, axis=0), np.max(X, axis=0))
    graph_surface(model.fcann2_classify, rect, offset=0)
    
    # graph the data points
    graph_data(X, Y_, Y, special=[])

    plt.show()
    