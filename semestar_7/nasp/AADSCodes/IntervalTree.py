from statistics import median
from TwoDRangeTree import BinaryTreeWithLeafValues
from GeomGeneral import isInRange

class Node:
    value,leftChild,rightChild=None,None,None
    taul,taur=None,None
    intervals=[]
    def __init__(self,v):
        self.value=v
        self.intervals=[]

class IntervalTree:
    rootNode=None
    def __init__(self,I):
        self.rootNode=self._create(I)
    def _create(self,I):
        if len(I)==0: return None
        else:
            epoints=set(map(lambda si:si[0][0],I)).union(set(map(lambda si:si[0][1],I)))
            xmed=median(epoints)
            n=Node(xmed)
            Ileft=list(filter(lambda si:si[0][0]<xmed and si[0][1]<xmed,I))
            Iright=list(filter(lambda si:si[0][0]>xmed and si[0][1]>xmed,I))
            Imed=list(filter(lambda si:si not in Ileft+Iright,I))
            n.intervals=Imed
            nl,nr=self._create(Ileft),self._create(Iright)
            n.leftChild,n.rightChild=nl,nr
            if len(Imed)>0:
                Pleft=list(set(map(lambda si:(min(si[0][0],si[0][1]),si[1]),Imed)))
                Pright=list(set(map(lambda si:(max(si[0][0],si[0][1]),si[1]),Imed)))
                n.taul=BinaryTreeWithLeafValues(Pp=Pleft)
                n.taur=BinaryTreeWithLeafValues(Pp=Pright)
            return n
    def IntervalQuery(self,qx1,qx2,qy1,qy2):
        if self.rootNode is not None: return self._IntervalQuery(self.rootNode,qx1,qx2,qy1,qy2)
        else: return []
    def _IntervalQuery(self,n,qx1,qx2,qy1,qy2):
        iv,move=set(),None
        if isInRange(qx1,n.value,qx2):
            for i in n.intervals:
                if qy1<=i[1]<=qy2: iv.add(i)
            move='both'
        elif qx1 is not None and n.value<qx1:
            if n.intervals is not None and len(n.intervals)>0:
                p=n.taur.TwoDRangeQuery(qx1,None,qy1,qy2)
                if len(p)>0:
                    _p=[]
                    for _n in p: _p=_p+_n.value
                    for i in n.intervals:
                        p1,p2=(i[0][0],i[1]),(i[0][1],i[1])
                        if (p1 in _p or p2 in _p): iv.add(i)
            move='right'
        elif qx2 is not None and n.value>qx2:
            if n.intervals is not None and len(n.intervals)>0:
                p=n.taul.TwoDRangeQuery(None,qx2,qy1,qy2)
                if len(p)>0:
                    _p=[]
                    for _n in p: _p=_p+_n.value
                    for i in n.intervals:
                        p1,p2=(i[0][0],i[1]),(i[0][1],i[1])
                        if (p1 in _p or p2 in _p): iv.add(i)
            move='left'
        if move in ['both','left'] and n.leftChild is not None:
            iv=iv.union(self._IntervalQuery(n.leftChild,qx1,qx2,qy1,qy2))
        if move in ['both','right'] and n.rightChild is not None:
            iv=iv.union(self._IntervalQuery(n.rightChild,qx1,qx2,qy1,qy2))
        return iv

