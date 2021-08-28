from sklearn.datasets import make_blobs
from sklearn.cluster import KMeans

import matplotlib.pyplot as plt

def plot_points(x, y, dotsize):
    plt.figure(figsize=(6, 6))
    plt.scatter(x, y, s=dotsize)
    plt.show()



# Generating the sample data from make_blobs
# This particular setting has one distinct cluster and 3 clusters placed close
# together.
X, y = make_blobs(n_samples=500,
                  n_features=2,
                  centers=4,
                  cluster_std=1,
                  center_box=(-10.0, 10.0),
                  shuffle=True,
                  random_state=1)  # For reproducibility

#plot_points(X[:,0], X[:,1], 25)
Ks = [k for k in range(2,9)]
Js = []
for k in Ks:
    kmeans = KMeans(n_clusters=k)
    kmeans.fit(X)
    J = kmeans.inertia_
    Js.append(J)

plt.figure(figsize=(6, 6))
plt.scatter(Ks, Js, s=100)
plt.plot(Ks, Js)
plt.xlabel("Broj grupa")
plt.ylabel("Iznos kriterijske funkcije")
plt.show()