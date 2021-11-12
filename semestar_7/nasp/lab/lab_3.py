from statistics import median
from typing import Tuple, List
from collections import namedtuple

class Point(namedtuple('Point', ['x', 'y'])):
    __slots__ = ()
    def __str__(self) -> str:
        return f'({self.x},{self.y})'
    def __repr__(self) -> str:
        return self.__str__()

class Node:
    def __init__(self, point: Point):
        self.p = point
        self.med = 0.0
        self.left = None
        self.right = None

    def __str__(self):
        return str(self.p)

class PriorityTree:
    def __init__(self, points: List[Point]) -> None:
        if not points:
            self.root = None
        else:
            points = list(sorted(points, key=lambda p: -p[1]))
            self.root = self._create(points)

    def _create(self, points: List[Point]) -> Node:
        if not points:
            return None
        first = points[0]
        n = Node(first)
        n.med = first[0]
        del points[0]
        if points:
            med = median(map(lambda it: it[0], points))
            n.med = med
            Pl = list(filter(lambda it: it[0] <= med, points))
            if Pl:
                n.left = self._create(Pl)
            Pr = list(filter(lambda it: it[0] > med, points))
            if Pr:
                n.right = self._create(Pr)
        return n

    def findSplitNode(self, x: Tuple[float, float]) -> Tuple[Node, list]:
        x_left, x_right = x
        n, path = self.root, []
        while n is not None and (x_left > n.med or x_right < n.med):
            p = n.p
            path.append(p)
            if x_right < n.med:
                n = n.left
            else:
                n = n.right
        return n, path

    def queryPrioritySubtree(self, n: Node, y: float) -> list:
        res = []
        if n is not None and n.p.y >= y:
            res.append(n.p)
            res = res + self.queryPrioritySubtree(n.left, y)
            res = res + self.queryPrioritySubtree(n.right, y)
        return res

def query(tree: PriorityTree, x: Tuple[float, float], y: float) -> List[Point]:
    rv = []
    is_in_interval_1d = lambda n, x: (n.p.x <= x[1] and n.p.x >= x[0])
    is_in_interval = lambda n, x, y : (n.p.x <= x[1] and n.p.x >= x[0] and n.p.y >= y)
    n_split, nr = tree.findSplitNode(x)
    for n in nr:
        if n.x <= x[1] and n.x >= x[0] and n.y >= y:
            rv.append(n)
    if n_split is None:
        return rv
    if (n_split.left is None) and (n_split.right is None):
        qps = tree.queryPrioritySubtree(n_split, y)
        for n_qps in qps:
            rv.append(n_qps)
    else:
        if is_in_interval(n_split, x, y):
            rv.append(n_split.p)
        n = n_split.left
        while (n is not None and n.p.y >= y):
            if is_in_interval(n, x, y):
                rv.append(n.p)
            if is_in_interval_1d(n, x):
                qps = tree.queryPrioritySubtree(n.right, y)
                for n_qps in qps:
                    rv.append(n_qps)
                n = n.left
            else:
                n = n.right
        n = n_split.right
        while (n is not None and n.p.y >= y):
            if is_in_interval(n, x, y):
                rv.append(n.p)
            if is_in_interval_1d(n, x):
                qps = tree.queryPrioritySubtree(n.left, y)
                for n_qps in qps:
                    rv.append(n_qps)
                n = n.right
            else:
                n = n.left
    return rv
