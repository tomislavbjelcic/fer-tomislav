import csv
import os
import numpy as np
import matplotlib.pyplot as plt
from sklearn.cluster import AgglomerativeClustering

data = []

path = os.path.join(os.path.dirname(__file__), "hierdata.csv")

with open(str(path)) as csvfile:
    reader = csv.reader(csvfile, quoting=csv.QUOTE_NONNUMERIC) # change contents to floats
    for row in reader: # each row is a list
        data.append(row)
    
X = np.array(data)
plt.figure(figsize=(6, 6))

agg = AgglomerativeClustering(distance_threshold=3.5, n_clusters=None, linkage='average')
y = agg.fit_predict(X)

N = len(X[:, 0])
plt.scatter(X[:, 0], X[:, 1], c=y)
for i in range(N):
    plt.annotate(str(i), (X[i, 0], X[i, 1]))

plt.show()
