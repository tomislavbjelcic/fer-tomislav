import numpy as np
from sklearn import svm
import data
import matplotlib.pyplot as plt
import pt_deep
import torch

class KSVMWrap:
    def __init__(self, X, Y_, param_svm_c=1, param_svm_gamma='auto'):
        '''
        Konstruira omotač i uči RBF SVM klasifikator
        X, Y_:           podatci i točni indeksi razreda
        param_svm_c:     relativni značaj podatkovne cijene
        param_svm_gamma: širina RBF jezgre
        '''
        self.svm = svm.SVC(C=param_svm_c, kernel="rbf", gamma=param_svm_gamma)
        self.svm.fit(X, Y_)

    def predict(self, X):
        return self.svm.predict(X)

    def get_scores(self, X):
        return self.svm.decision_function(X)

    def support(self):
        return self.svm.support_



if __name__ == "__main__":
    # inicijaliziraj generatore slučajnih brojeva
    np.random.seed(100)
    #torch.manual_seed(43) #sheeeeesh

    # instanciraj podatke X i labele Yoh_
    X,Y_ = data.sample_gmm_2d(ncomponents=6, nclasses=2, nsamples=10)

    
    svm_model = KSVMWrap(X, Y_)
    Y = svm_model.predict(X)

    # iscrtaj rezultate, decizijsku plohu
    # graph the decision surface
    rect=(np.min(X, axis=0), np.max(X, axis=0))
    classifier = svm_model.get_scores
    data.graph_surface(classifier, rect, offset=0)
    
    # graph the data points
    data.graph_data(X, Y_, Y, special=svm_model.support())
    

    '''
    Yoh_ = data.class_to_onehot(Y_)
    X_tensor = torch.tensor(X)
    Yoh_tensor = torch.tensor(Yoh_)
    
    

    # definiraj model:
    config = [2, 10, 10, 2]
    ptdeep = pt_deep.PTDeep(config, torch.relu)

    # nauči parametre (X i Yoh_ moraju biti tipa torch.Tensor):
    train_params = {
        "param_niter":1000, 
        "param_delta":0.1, 
        "param_lambda":1e-4
    }
    pt_deep.train(ptdeep, X_tensor, Yoh_tensor, **train_params)
    

    # dohvati vjerojatnosti na skupu za učenje
    probs = pt_deep.eval(ptdeep, X)
    Y = np.argmax(probs, axis=1)
    rect=(np.min(X, axis=0), np.max(X, axis=0))
    classifier = lambda X: np.argmax(pt_deep.eval(ptdeep, X), axis=1)
    data.graph_surface(classifier, rect, offset=0.5)
    
    # graph the data points
    data.graph_data(X, Y_, Y, special=[])
    '''
    

    plt.show()
