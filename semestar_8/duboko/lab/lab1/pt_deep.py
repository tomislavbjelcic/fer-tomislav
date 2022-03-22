import numpy as np
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
import data
import matplotlib.pyplot as plt


class PTDeep(nn.Module):
    def __init__(self, config, nonlinearity):
        
        # inicijalizirati parametre (koristite nn.Parameter):
        # ...
        super().__init__()

        self.nonlinearity = nonlinearity
        weights = []
        biases = []
        self.n_layers = len(config)-1
        for i in range(len(config)):
            if i == len(config)-1:
                break
            dim_in = config[i]
            dim_out = config[i+1]
            weights.append(nn.Parameter(torch.randn(dim_out, dim_in, dtype=torch.float64)))
            biases.append(nn.Parameter(torch.zeros(dim_out, dtype=torch.float64)))
        self.weights = nn.ParameterList(weights)
        self.biases = nn.ParameterList(biases)


    def forward(self, X):
        # unaprijedni prolaz modela: izračunati vjerojatnosti
        #   koristiti: torch.mm, torch.softmax
        # ...
        return torch.softmax(self.forward_before_softmax(X), dim=1)
    
    def forward_before_softmax(self, X):
        for i, (w, b) in enumerate(zip(self.weights, self.biases)):
            X = torch.mm(X, torch.transpose(w, 0, 1)) + b
            if i<self.n_layers-1:
                X = self.nonlinearity(X)
        return X



    def get_loss(self, X, Yoh_):
        # formulacija gubitka
        #   koristiti: torch.log, torch.mean, torch.sum
        # ...
        log_probs = F.log_softmax(self.forward_before_softmax(X), dim=1) * Yoh_
        loss_tensor = torch.sum(log_probs, dim=1)
        return -torch.mean(loss_tensor)

    
    def count_params(self):
        params = []
        total_param_count = 0
        for name, param in ptlr.named_parameters():
            params.append((name, param.shape))
            total_param_count += param.numel()
        return {"params":params, "total_param_count":total_param_count}


def train(model : PTDeep, X, Yoh_, param_niter, param_delta, param_lambda=0.001):
    """Arguments:
       - X: model inputs [NxD], type: torch.Tensor
       - Yoh_: ground truth [NxC], type: torch.Tensor
       - param_niter: number of training iterations
       - param_delta: learning rate
    """

    # inicijalizacija optimizatora
    # ...
    sgd_params = [
        {"params": model.weights.parameters(), "weight_decay":param_lambda},
        {"params": model.biases.parameters()}
    ]
    optimizer = optim.SGD(sgd_params, lr=param_delta, weight_decay=0.0)

    # petlja učenja
    # ispisujte gubitak tijekom učenja
    # ...
    for i in range(param_niter):
        loss = model.get_loss(X, Yoh_)
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
    np.random.seed(43)
    torch.manual_seed(43)

    # instanciraj podatke X i labele Yoh_
    X,Y_ = data.sample_gmm_2d(ncomponents=6, nclasses=2, nsamples=10)
    Yoh_ = data.class_to_onehot(Y_)
    X_tensor = torch.tensor(X)
    Yoh_tensor = torch.tensor(Yoh_)
    
    

    # definiraj model:
    config = [2, 10, 10, 2]
    ptlr = PTDeep(config, torch.relu)

    # nauči parametre (X i Yoh_ moraju biti tipa torch.Tensor):
    train_params = {
        "param_niter":10000, 
        "param_delta":0.1, 
        "param_lambda":1e-4
    }
    train(ptlr, X_tensor, Yoh_tensor, **train_params)
    

    # dohvati vjerojatnosti na skupu za učenje
    probs = eval(ptlr, X)
    Y = np.argmax(probs, axis=1)

    # ispiši performansu (preciznost i odziv po razredima)

    # iscrtaj rezultate, decizijsku plohu
    # graph the decision surface
    rect=(np.min(X, axis=0), np.max(X, axis=0))
    classifier = lambda X: np.argmax(eval(ptlr, X), axis=1)
    #classifier = lambda X: eval(ptlr, X)[:,1]
    data.graph_surface(classifier, rect, offset=0.5)
    
    # graph the data points
    data.graph_data(X, Y_, Y, special=[])

    plt.show()
