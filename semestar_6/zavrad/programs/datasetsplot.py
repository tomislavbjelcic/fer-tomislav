import numpy as np
import matplotlib.pyplot as plt

from sklearn import datasets
from sklearn.preprocessing import StandardScaler
from itertools import cycle, islice

np.random.seed(0)
custom_colors = ["#2796f5", "#f54b4b", "#1c9c78", "#ffd92e"]

# ============
# Generate datasets. We choose the size big enough to see the scalability
# of the algorithms, but not too big to avoid too long running times
# ============
n_samples = 1500
noisy_circles = datasets.make_circles(n_samples=n_samples, factor=.5,
                                      noise=.05)
noisy_moons = datasets.make_moons(n_samples=n_samples, noise=.05)
blobs = datasets.make_blobs(n_samples=n_samples, random_state=8)
no_structure = np.random.rand(n_samples, 2), [0]*n_samples

# Anisotropicly distributed data
random_state = 170
X, y = datasets.make_blobs(n_samples=n_samples, random_state=random_state)
transformation = [[0.6, -0.6], [-0.4, 0.8]]
X_aniso = np.dot(X, transformation)
aniso = (X_aniso, y)

# blobs with varied variances
varied = datasets.make_blobs(n_samples=n_samples,
                             cluster_std=[1.0, 2.5, 0.5],
                             random_state=random_state)


datasets = [
    (noisy_circles, "Koncentrične kružnice"),
    (noisy_moons, "Polumjeseci"),
    (varied, "Neukošene grupe različitih veličina"),
    (aniso, "Ukošene grupe"),
    (blobs, "Dobro odvojene, kompaktne i sferične grupe"),
    (no_structure, "Jednoliko raspoređeni podaci")
]
num = len(datasets)
rows = num//2 if (num%2 == 0) else num//2 + 1
cols = 2

plt.subplots_adjust(hspace=0.35)

for i, (dataset, name) in enumerate(datasets):
    data, labels = dataset
    data = StandardScaler().fit_transform(data)

    colors = np.array(list(islice(cycle(custom_colors),
                                      int(max(labels) + 1))))

    plt.subplot(rows, cols, i+1)
    plt.title("{} ({})".format(name, i+1))
    plt.scatter(data[:, 0], data[:, 1], s=3, c=colors[labels])


plt.show()