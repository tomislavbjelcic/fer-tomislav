import numpy as np

def sigm(x):
    return np.where(x >= 0, 
                    1 / (1 + np.exp(-x)), 
                    np.exp(x) / (1 + np.exp(x)))

def sigm_extended(y, s):
    return np.where(y==1,
                    sigm(s),
                    1-sigm(s))

def celoss(y, s):
    return np.where(y==1,
                    -s + np.log(1+np.exp(s)),
                    np.log(1+np.exp(s)))

def binlogreg_train(X, Y_):
    N = len(Y_)
    w = np.random.randn(N)
    b = 0
    # gradijentni spust (param_niter iteracija)
    param_niter = 1000
    param_delta = 0.01
    for i in range(param_niter):
        # klasifikacijske mjere
        scores = np.dot(X, w) + b
        
        # vjerojatnosti razreda c_1
        probs = sigm_extended(Y_, scores)

        # gubitak
        loss  = celoss(Y_, scores)
        
        # dijagnostički ispis
        if i % 10 == 0:
            print("iteration {}: loss {}".format(i, loss))

        # derivacije gubitka po klasifikacijskim mjerama
        dL_dscores = sigm(scores) - np.where(y==1, 1, 0)     # N x 1
        
        # gradijenti parametara
        grad_w = np.dot(dL_dscores, X) / N     # D x 1
        grad_b = np.sum(dL_dscores) / N     # 1 x 1

        # poboljšani parametri
        w += -param_delta * grad_w
        b += -param_delta * grad_b
    
    return w, b


if __name__ == "__main__":
    y=1
    s=-1000
    
