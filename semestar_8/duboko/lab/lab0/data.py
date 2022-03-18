import numpy as np
import matplotlib.pyplot as plt

class Random2DGaussian:

    def __init__(self) -> None:
        self.minx = 0
        self.maxx = 10
        self.mu = (self.maxx - self.minx) * np.random.random_sample(2) + self.minx
        eigvals = (np.random.random_sample(2)*(self.maxx - self.minx)/5)**2
        D = np.diag(eigvals)
        phi = (2*np.pi) * np.random.random_sample()
        cosphi = np.cos(phi)
        sinphi = np.sin(phi)
        R = np.array([[cosphi, -sinphi], [sinphi, cosphi]])
        self.sigma = np.linalg.multi_dot([np.transpose(R), D, R])

    
    def get_sample(self, n):
        return np.random.multivariate_normal(mean=self.mu, cov=self.sigma, size=n)


if __name__=="__main__":
    np.random.seed(100)
    G=Random2DGaussian()
    X=G.get_sample(100)
    plt.scatter(X[:,0], X[:,1])
    plt.show()

