import numpy as np
import matplotlib.pyplot as plt

from sklearn.datasets import make_blobs

plt.figure(figsize=(6, 6))

n_samples = 21
random_state = 32
centers = np.array([[28, 57], [40, 73], [15, 40]]) #dalmatian, greyhound & chihuahuas
stds = np.array([[1.6, 2], [2, 2], [1.2, 1.6]])

X, y = make_blobs(n_samples=n_samples, centers=centers, cluster_std=stds, random_state=random_state)

plt.scatter(X[:, 0], X[:, 1])
plt.xlabel("Masa / kg")
plt.ylabel("Visina / cm")
#plt.title("Podaci o psima u skloništu za životinje")

plt.show()