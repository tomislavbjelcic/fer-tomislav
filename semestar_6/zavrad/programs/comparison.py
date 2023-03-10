"""
=========================================================
Comparing different clustering algorithms on toy datasets
=========================================================

This example shows characteristics of different
clustering algorithms on datasets that are "interesting"
but still in 2D. With the exception of the last dataset,
the parameters of each of these dataset-algorithm pairs
has been tuned to produce good clustering results. Some
algorithms are more sensitive to parameter values than
others.

The last dataset is an example of a 'null' situation for
clustering: the data is homogeneous, and there is no good
clustering. For this example, the null dataset uses the
same parameters as the dataset in the row above it, which
represents a mismatch in the parameter values and the
data structure.

While these examples give some intuition about the
algorithms, this intuition might not apply to very high
dimensional data.
"""
print(__doc__)

import time
import warnings

import numpy as np
import matplotlib.pyplot as plt

from sklearn import cluster, datasets, mixture
from sklearn import metrics
from sklearn.preprocessing import StandardScaler
from itertools import cycle, islice

np.random.seed(0)
custom_colors = ["red", "blue", "green", "#f3aa16", "#ed1aed"]

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

# ============
# Set up cluster parameters
# ============
plt.figure(figsize=(9 * 2 + 3, 13))
plt.subplots_adjust(left=.02, right=.98, bottom=.001, top=.95, wspace=.05,
                    hspace=.01)

plot_num = 1

default_base = {'quantile': .3,
                'eps': .3,
                'damping': .9,
                'preference': -200,
                'n_neighbors': 10,
                'n_clusters': 3,
                'min_samples': 20,
                'xi': 0.05,
                'min_cluster_size': 0.1}

datasets = [
    (noisy_circles, {'damping': .77, 'preference': -240,
                     'quantile': .2, 'n_clusters': 2,
                     'min_samples': 20, 'xi': 0.25}, "Noisy Circles"),
    (noisy_moons, {'damping': .75, 'preference': -220, 'n_clusters': 2}, "Noisy Moons"),
    (varied, {'eps': .18, 'n_neighbors': 2,
              'min_samples': 5, 'xi': 0.035, 'min_cluster_size': .2}, "Varied Blobs"),
    (aniso, {'eps': .15, 'n_neighbors': 2,
             'min_samples': 20, 'xi': 0.1, 'min_cluster_size': .2}, "Anisotropic Blobs"),
    (blobs, {}, "Blobs"),
    (no_structure, {}, "No Structure")]

internal_eval_metrics = [
    (metrics.davies_bouldin_score, "DBI"),
    (metrics.silhouette_score, "AvgSilhouette")
]

external_eval_metrics = [
    (metrics.rand_score, "Rand"),
    (metrics.adjusted_rand_score, "ARI"),
    (metrics.mutual_info_score, "MI"),
    (metrics.normalized_mutual_info_score, "NMI"),
    (metrics.adjusted_mutual_info_score, "AMI")
]

for i_dataset, (dataset, algo_params, dataset_name) in enumerate(datasets):
    # update parameters with dataset-specific values
    params = default_base.copy()
    params.update(algo_params)

    X, y = dataset

    # normalize dataset for easier parameter selection
    X = StandardScaler().fit_transform(X)

    # ============
    # Create cluster objects
    # ============
    kmeans = cluster.KMeans(n_clusters=params['n_clusters'])
    ward = cluster.AgglomerativeClustering(
        n_clusters=params['n_clusters'], linkage='ward')
    dbscan = cluster.DBSCAN(eps=params['eps'])
    average_linkage = cluster.AgglomerativeClustering(
        linkage="average",
        n_clusters=params['n_clusters'])
    gmm = mixture.GaussianMixture(
        n_components=params['n_clusters'], covariance_type='full')
    single_linkage = cluster.AgglomerativeClustering(
        n_clusters=params['n_clusters'], linkage='single'
    )
    complete_linkage = cluster.AgglomerativeClustering(
        n_clusters=params['n_clusters'], linkage='complete'
    )

    clustering_algorithms = (
        ('K-Means', kmeans),
        ('HAC - Ward', ward),
        ('HAC - Average Linkage', average_linkage),
        ('HAC - Single Linkage', single_linkage),
        ('HAC - Complete Linkage', complete_linkage),
        ('DBSCAN', dbscan),
        ('Gaussian Mixture', gmm)
    )

    for name, algorithm in clustering_algorithms:
        t0 = time.time()

        # catch warnings related to kneighbors_graph
        with warnings.catch_warnings():
            warnings.filterwarnings(
                "ignore",
                message="the number of connected components of the " +
                "connectivity matrix is [0-9]{1,2}" +
                " > 1. Completing it to avoid stopping the tree early.",
                category=UserWarning)
            warnings.filterwarnings(
                "ignore",
                message="Graph is not fully connected, spectral embedding" +
                " may not work as expected.",
                category=UserWarning)
            y_pred = algorithm.fit_predict(X)

        t1 = time.time()
        timems = round((t1-t0) * 1000)
        
        print("Dataset: {}; {} executed.".format(dataset_name,name))

        if dataset_name != "No Structure":
            for metric, metric_name in internal_eval_metrics:
                score = metric(X, y_pred)
                print("{}: {}".format(metric_name, score), end="\t")
            print()
            for metric, metric_name in external_eval_metrics:
                score = metric(y, y_pred)
                print("{}: {}".format(metric_name, score), end="\t")
            print("\n")

        plt.subplot(len(datasets), len(clustering_algorithms), plot_num)
        if i_dataset == 0:
            plt.title(name, size=13)

        colors = np.array(list(islice(cycle(custom_colors),
                                      int(max(y_pred) + 1))))
        
        # add black color for outliers (if any)
        colors = np.append(colors, ["#000000"])
        plt.scatter(X[:, 0], X[:, 1], s=3, c=colors[y_pred])
        '''
        L = 2.5
        plt.xlim(-L, L)
        plt.ylim(-L, L)
        '''
        plt.xticks(())
        plt.yticks(())
        plt.text(.99, .88, "{}ms".format(timems),
                 transform=plt.gca().transAxes, size=15,
                 horizontalalignment='right')
        
        plot_num += 1

plt.show()
