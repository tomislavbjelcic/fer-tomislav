import numpy as np
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
import data
import matplotlib.pyplot as plt


class PTLogreg(nn.Module):
    def __init__(self, D, C):
        """Arguments:
           - D: dimensions of each datapoint 
           - C: number of classes
        """

        # inicijalizirati parametre (koristite nn.Parameter):
        # imena mogu biti self.W, self.b
        # ...
        super().__init__()
        self.W = nn.Parameter(torch.randn(C, D, dtype=torch.float64))
        self.b = nn.Parameter(torch.zeros(C, dtype=torch.float64))

    def forward(self, X):
        # unaprijedni prolaz modela: izračunati vjerojatnosti
        #   koristiti: torch.mm, torch.softmax
        # ...
        return torch.softmax(self.forward_before_softmax(X), dim=1)
    
    def forward_before_softmax(self, X):
        return torch.mm(X, torch.transpose(self.W, 0, 1)) + self.b

    def get_loss(self, X, Yoh_):
        # formulacija gubitka
        #   koristiti: torch.log, torch.mean, torch.sum
        # ...
        log_probs = F.log_softmax(self.forward_before_softmax(X), dim=1) * Yoh_
        loss_tensor = torch.sum(log_probs, dim=1)
        return -torch.mean(loss_tensor)



def train(model, X, Yoh_, param_niter, param_delta, param_lambda=0.001):
    """Arguments:
       - X: model inputs [NxD], type: torch.Tensor
       - Yoh_: ground truth [NxC], type: torch.Tensor
       - param_niter: number of training iterations
       - param_delta: learning rate
    """

    # inicijalizacija optimizatora
    # ...
    optimizer = optim.SGD([model.W, model.b], lr=param_delta)

    # petlja učenja
    # ispisujte gubitak tijekom učenja
    # ...
    for i in range(param_niter):
        loss = model.get_loss(X, Yoh_) + param_lambda * torch.norm(model.W)
        print(f"step {i} - loss:{loss}")
        #print(f"step {i} - loss:{loss} W:{model.W} b:{model.b}")

        loss.backward()

        optimizer.step()
        optimizer.zero_grad()


def eval(model, X):
    """Arguments:
       - model: type: PTLogreg
       - X: actual datapoints [NxD], type: np.array
       Returns: predicted class probabilites [NxC], type: np.array
    """
    # ulaz je potrebno pretvoriti u torch.Tensor
    # izlaze je potrebno pretvoriti u numpy.array
    # koristite torch.Tensor.detach() i torch.Tensor.numpy()
    X_tensor = torch.tensor(X)
    probs_tensor = model.forward(X_tensor).detach()
    X_tensor.detach()
    return probs_tensor.numpy()


if __name__ == "__main__":
    # inicijaliziraj generatore slučajnih brojeva
    np.random.seed(200)
    torch.manual_seed(200)

    # instanciraj podatke X i labele Yoh_
    X,Y_ = data.sample_gauss_2d(nclasses=3, nsamples=100)
    Yoh_ = data.class_to_onehot(Y_)
    X_tensor = torch.tensor(X)
    Yoh_tensor = torch.tensor(Yoh_)
    
    

    # definiraj model:
    ptlr = PTLogreg(X.shape[1], Yoh_.shape[1])

    # nauči parametre (X i Yoh_ moraju biti tipa torch.Tensor):
    train_params = {
        "param_niter":1000, 
        "param_delta":0.2, 
        "param_lambda":0.0
    }
    train(ptlr, X_tensor, Yoh_tensor, **train_params)
    print(ptlr.W)
    print(ptlr.b)

    # dohvati vjerojatnosti na skupu za učenje
    probs = eval(ptlr, X)
    Y = np.argmax(probs, axis=1)

    # ispiši performansu (preciznost i odziv po razredima)

    # iscrtaj rezultate, decizijsku plohu
    # graph the decision surface
    rect=(np.min(X, axis=0), np.max(X, axis=0))

    classifier = lambda X: np.argmax(eval(ptlr, X), axis=1)
    # classifier = lambda X: eval(ptlr, X)[:,1]

    data.graph_surface(classifier, rect, offset=0.5)
    
    # graph the data points
    data.graph_data(X, Y_, Y, special=[])

    plt.show()
