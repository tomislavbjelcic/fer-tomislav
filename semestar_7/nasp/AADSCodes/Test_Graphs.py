import unittest
from TwoMSTTravelingSalesman import TwoMSTTSP
from BellmanFord import BellmanFord,BellmanFordFast
from General import RevPath,FindCycles
from BFS_Iterative import BFS
from BlockSearch import BlockSearch
from DFS import DFS,DFSIterative
from Dijkstra import Dijkstra,DijkstraMSTUndirected
from EdmondsKarp import EdmondsKarp
from EulerianCycles import Fleury,Hierholzer,Eulerization,IsEulerianCycle
import copy
from Kruskal import KruskalUndirected
from Prim import Prim
from SCCSearch import SCCSearch
from WFI import WFI

class SimpleBinaryTreeCases(unittest.TestCase):
    def test_2MSTTS(self):
        G= {'a': {'a':0,'b':5,'c':1,'d':2,'e':2},
            'b': {'a':5,'b':0,'c':4,'d':6,'e':1},
            'c': {'a':1,'b':4,'c':0,'d':1,'e':3},
            'd': {'a':2,'b':6,'c':1,'d':0,'e':4},
            'e': {'a':2,'b':1,'c':3,'d':4,'e':0}}
        #Ch=TwoMSTTSP(G,'a')
        #print(Ch)
        #G= {'a': {'a':0,'b':2,'c':3},
        #    'b': {'a':2,'b':0,'c':1},
        #    'c': {'a':3,'b':1,'c':0}}
        Ch=TwoMSTTSP(G,'a')
        self.assertTrue((('a','e'),2) in Ch,len(Ch)==5)

    def test_BellmanFord(self):
        G= {'a': {'a':0,'b':1,'c':0,'d':0,'e':0,'f':0,'g':0,'h':0,'i':0},
            'b': {'a':0,'b':0,'c':0,'d':0,'e':-5,'f':0,'g':0,'h':0,'i':0},
            'c': {'a':0,'b':0,'c':0,'d':1,'e':0,'f':0,'g':1,'h':1,'i':0},
            'd': {'a':2,'b':0,'c':0,'d':0,'e':4,'f':0,'g':0,'h':0,'i':1},
            'e': {'a':0,'b':0,'c':0,'d':0,'e':0,'f':4,'g':0,'h':0,'i':0},
            'f': {'a':0,'b':0,'c':0,'d':0,'e':0,'f':0,'g':0,'h':0,'i':0},
            'g': {'a':0,'b':0,'c':0,'d':-1,'e':0,'f':0,'g':0,'h':0,'i':0},
            'h': {'a':0,'b':0,'c':0,'d':0,'e':0,'f':0,'g':-1,'h':0,'i':0},
            'i': {'a':0,'b':0,'c':0,'d':0,'e':0,'f':1,'g':0,'h':0,'i':0}
            }
        D1=BellmanFord(G,'c')
        D2=BellmanFordFast(G,'c')
        self.assertTrue(RevPath(D1,'f')==['f', 'i', 'd', 'g', 'h', 'c'],RevPath(D2,'f')==['f', 'i', 'd', 'g', 'h', 'c'])

    def test_BFSIterative(self):
        G = {}
        G['a'] = {'adj':['b','c','d']}
        G['b'] = {'adj':['a','e','f']}
        G['c'] = {'adj':['a','f']}
        G['d'] = {'adj':['a','h']}
        G['e'] = {'adj':['b','i']}
        G['f'] = {'adj':['b','c','g']}
        G['g'] = {'adj':['f','i']}
        G['h'] = {'adj':['d','i']}
        G['i'] = {'adj':['e','g','h']}
        self.assertTrue(BFS(G)==['a', 'b', 'c', 'd', 'e', 'f', 'h', 'i', 'g'])

    def test_BlockSearch(self):
        G = {}
        #G['a'] = {'adj':['c']}
        #G['b'] = {'adj':[]}
        #G['c'] = {'adj':['b','d','e','f']}
        #G['d'] = {'adj':['c','e','f']}
        #G['e'] = {'adj':['c','d','f']}
        #G['f'] = {'adj':['c','d','e']}
        G['a'] = {'adj':['c','d','f']}
        G['c'] = {'adj':['a','f']}
        G['f'] = {'adj':['a','c','d']}
        G['d'] = {'adj':['a','b','e']}
        G['b'] = {'adj':['d','e','g','h']}
        G['e'] = {'adj':['b','d']}
        G['g'] = {'adj':['b','h','k']}
        G['h'] = {'adj':['b','g','i','k']}
        G['i'] = {'adj':['h','j']}
        G['j'] = {'adj':['i']}
        G['k'] = {'adj':['g','h']}
        G['l'] = {'adj':['m']}
        G['m'] = {'adj':['l']}
        blocks=BlockSearch(G)
        #print(blocks)

    def test_DFS(self):
        G = {}
        G['a'] = {'adj':['b','c','d']}
        G['b'] = {'adj':['a','e','f']}
        G['c'] = {'adj':['a','f']}
        G['d'] = {'adj':['a','h']}
        G['e'] = {'adj':['b','i']}
        G['f'] = {'adj':['b','c','g']}
        G['g'] = {'adj':['f','i']}
        G['h'] = {'adj':['d','i']}
        G['i'] = {'adj':['e','g','h']}
        r1=DFSIterative(G)
        #print(r1)
        r2=DFS(G)
        #print(r2)

    def test_Dijkstra(self):
        G= {'a': {'a':0,'b':0,'c':0,'d':0,'e':1,'f':0,'g':0,'h':10,'i':0,'j':0},
            'b': {'a':0,'b':0,'c':2,'d':0,'e':0,'f':0,'g':0,'h':0,'i':0,'j':0},
            'c': {'a':0,'b':0,'c':0,'d':0,'e':0,'f':0,'g':0,'h':0,'i':0,'j':0},
            'd': {'a':4,'b':0,'c':0,'d':0,'e':0,'f':0,'g':0,'h':1,'i':0,'j':0},
            'e': {'a':0,'b':0,'c':0,'d':0,'e':0,'f':3,'g':0,'h':0,'i':0,'j':0},
            'f': {'a':0,'b':1,'c':3,'d':0,'e':0,'f':0,'g':7,'h':0,'i':1,'j':0},
            'g': {'a':0,'b':0,'c':0,'d':0,'e':0,'f':0,'g':0,'h':0,'i':0,'j':0},
            'h': {'a':0,'b':0,'c':0,'d':0,'e':5,'f':0,'g':0,'h':0,'i':9,'j':0},
            'i': {'a':0,'b':0,'c':0,'d':0,'e':0,'f':0,'g':0,'h':0,'i':0,'j':2},
            'j': {'a':0,'b':0,'c':0,'d':0,'e':0,'f':0,'g':1,'h':0,'i':0,'j':0}
            }
        D=Dijkstra(G,'d','g')
        DRP=RevPath(D,'g')
        #print(DRP)
        G2={'a': {'a':0,'b':6,'c':5,'d':0,'e':0,'f':0,'g':0},
            'b': {'a':6,'b':0,'c':9,'d':0,'e':13,'f':0,'g':0},
            'c': {'a':5,'b':9,'c':0,'d':16,'e':0,'f':12,'g':0},
            'd': {'a':0,'b':0,'c':16,'d':0,'e':15,'f':7,'g':0},
            'e': {'a':0,'b':13,'c':0,'d':15,'e':0,'f':0,'g':8},
            'f': {'a':0,'b':0,'c':12,'d':7,'e':0,'f':0,'g':3},
            'g': {'a':0,'b':0,'c':0,'d':0,'e':8,'f':3,'g':0}}
        MST=DijkstraMSTUndirected(G2)
        #print(MST)

    def test_EdmondsKarp(self):
        G1={'s1': {'s1':0,'v1':10,'v2':5,'v3':8,'v4':0,'d1':0},
            'v1': {'s1':0,'v1':0,'v2':0,'v3':0,'v4':3,'d1':5},
            'v2': {'s1':0,'v1':0,'v2':0,'v3':0,'v4':3,'d1':10},
            'v3': {'s1':0,'v1':3,'v2':3,'v3':0,'v4':10,'d1':0},
            'v4': {'s1':0,'v1':0,'v2':0,'v3':0,'v4':0,'d1':8},
            'd1': {'s1':0,'v1':0,'v2':0,'v3':0,'v4':0,'d1':0}}
        G2={'a':{'a':0,'b':21,'c':10,'d':0,'e':0,'f':16,'g':0,'h':0,'i':0,'j':0},
            'b':{'a':0,'b':0,'c':0,'d':14,'e':7,'f':0,'g':0,'h':0,'i':0,'j':0},
            'c':{'a':0,'b':0,'c':0,'d':0,'e':0,'f':9,'g':0,'h':0,'i':0,'j':0},
            'd':{'a':0,'b':0,'c':0,'d':0,'e':0,'f':8,'g':25,'h':0,'i':0,'j':0},
            'e':{'a':0,'b':0,'c':0,'d':0,'e':0,'f':0,'g':0,'h':0,'i':11,'j':0},
            'f':{'a':0,'b':0,'c':0,'d':0,'e':0,'f':0,'g':0,'h':13,'i':0,'j':0},
            'g':{'a':0,'b':0,'c':0,'d':0,'e':0,'f':0,'g':0,'h':0,'i':0,'j':14},
            'h':{'a':0,'b':0,'c':0,'d':0,'e':0,'f':0,'g':6,'h':0,'i':0,'j':17},
            'i':{'a':0,'b':0,'c':0,'d':0,'e':0,'f':0,'g':9,'h':0,'i':0,'j':19},
            'j':{'a':0,'b':0,'c':0,'d':0,'e':0,'f':0,'g':0,'h':0,'i':0,'j':0}}
        r1=EdmondsKarp(G1,'s1','d1')[0]
        #print(r1)
        r2=EdmondsKarp(G2,'a','j')[0]
        #print(r2)

    def test_EulerianCycles(self):
        G = {}
        G['a'] = {'adj':['b','f']}
        G['b'] = {'adj':['a','c']}
        G['c'] = {'adj':['b','d']}
        G['d'] = {'adj':['c','e']}
        G['e'] = {'adj':['d','f','m','j']}
        G['f'] = {'adj':['a','e','g','k']}
        G['g'] = {'adj':['f','h']}
        G['h'] = {'adj':['g','i']}
        G['i'] = {'adj':['h','j']}
        G['j'] = {'adj':['e','i']}
        G['k'] = {'adj':['f','l']}
        G['l'] = {'adj':['k','m']}
        G['m'] = {'adj':['l','e']}
        G1=copy.deepcopy(G)
        G2=copy.deepcopy(G)
        G3=copy.deepcopy(G)
        isc=IsEulerianCycle(G1,'a')
        #print(isc)
        cycle=Fleury(G2,'a')
        #print(cycle)
        cycle=Fleury(G3,'f')
        #print(cycle)
        G4 = {}
        G4['a'] = {'adj':['b','f']}
        G4['b'] = {'adj':['a','c']}
        G4['c'] = {'adj':['b','d']}
        G4['d'] = {'adj':['c','e']}
        G4['e'] = {'adj':['d','f','k','m']}
        G4['f'] = {'adj':['a','e','k','m']}
        G4['g'] = {'adj':['h','l']}
        G4['h'] = {'adj':['g','i']}
        G4['i'] = {'adj':['h','j']}
        G4['j'] = {'adj':['i','k']}
        G4['k'] = {'adj':['e','f','l','j']}
        G4['l'] = {'adj':['g','k']}
        G4['m'] = {'adj':['e','f']}
        G5=copy.deepcopy(G4)
        cycle=Fleury(G4,'m')
        #print(cycle)
        cycle=Hierholzer(G5,'m')
        #print(cycle)
        G= {'a': {'a':0,'b':0,'c':3,'d':2,'e':0,'f':0,'g':0,'h':0,'i':0},
            'b': {'a':0,'b':0,'c':12,'d':2,'e':1,'f':1,'g':5,'h':0,'i':0},
            'c': {'a':3,'b':12,'c':0,'d':0,'e':0,'f':6,'g':0,'h':0,'i':0},
            'd': {'a':2,'b':2,'c':0,'d':0,'e':0,'f':0,'g':0,'h':0,'i':0},
            'e': {'a':0,'b':1,'c':0,'d':0,'e':0,'f':0,'g':0,'h':1,'i':0},
            'f': {'a':0,'b':1,'c':6,'d':0,'e':0,'f':0,'g':0,'h':2,'i':1},
            'g': {'a':0,'b':5,'c':0,'d':0,'e':0,'f':0,'g':0,'h':9,'i':2},
            'h': {'a':0,'b':0,'c':0,'d':0,'e':1,'f':2,'g':9,'h':0,'i':0},
            'i': {'a':0,'b':0,'c':0,'d':0,'e':0,'f':1,'g':2,'h':0,'i':0}}
        G6=Eulerization(G)
        cycle=Hierholzer(G6,'b')
        #print(cycle)

    def test_FindCycle(self):
        G = {}
        G['a'] = {'adj':['b']}
        G['b'] = {'adj':['c']}
        G['c'] = {'adj':['d','e','f']}
        G['d'] = {'adj':[]}
        G['e'] = {'adj':['b']}
        G['f'] = {'adj':['b']}
        cycs=FindCycles(G)
        #print(cycs)

    def test_Kruskal(self):
        G= {'a': {'a':0,'b':6,'c':5,'d':0,'e':0,'f':0,'g':0},
            'b': {'a':6,'b':0,'c':9,'d':0,'e':13,'f':0,'g':0},
            'c': {'a':5,'b':9,'c':0,'d':16,'e':0,'f':12,'g':0},
            'd': {'a':0,'b':0,'c':16,'d':0,'e':15,'f':7,'g':0},
            'e': {'a':0,'b':13,'c':0,'d':15,'e':0,'f':0,'g':8},
            'f': {'a':0,'b':0,'c':12,'d':7,'e':0,'f':0,'g':3},
            'g': {'a':0,'b':0,'c':0,'d':0,'e':8,'f':3,'g':0}}
        MST=KruskalUndirected(G)
        #print(MST)

    def test_Prim(self):
        G= {'a': {'a':0,'b':6,'c':5,'d':0,'e':0,'f':0,'g':0},
            'b': {'a':6,'b':0,'c':9,'d':0,'e':13,'f':0,'g':0},
            'c': {'a':5,'b':9,'c':0,'d':16,'e':0,'f':12,'g':0},
            'd': {'a':0,'b':0,'c':16,'d':0,'e':15,'f':7,'g':0},
            'e': {'a':0,'b':13,'c':0,'d':15,'e':0,'f':0,'g':8},
            'f': {'a':0,'b':0,'c':12,'d':7,'e':0,'f':0,'g':3},
            'g': {'a':0,'b':0,'c':0,'d':0,'e':8,'f':3,'g':0}}
        MST=Prim(G,'a')
        #print(MST)

    def test_SCCSearch(self):
        G = {}
        G['a'] = {'adj':['c']}
        G['c'] = {'adj':['f']}
        G['f'] = {'adj':['a']}
        G['d'] = {'adj':['a','f','b']}
        G['b'] = {'adj':['e','h']}
        G['e'] = {'adj':['d']}
        G['h'] = {'adj':['g','i','k']}
        G['g'] = {'adj':['b','k']}
        G['k'] = {'adj':[]}
        G['i'] = {'adj':['j']}
        G['j'] = {'adj':[]}
        res=SCCSearch(G)
        #print(res)

    def test_WFI(self):
        G= {'a': {'a':0,'b':0,'c':3,'d':2,'e':0,'f':0,'g':0,'h':0,'i':0},
            'b': {'a':0,'b':0,'c':12,'d':2,'e':1,'f':1,'g':5,'h':0,'i':0},
            'c': {'a':3,'b':12,'c':0,'d':0,'e':0,'f':6,'g':0,'h':0,'i':0},
            'd': {'a':2,'b':2,'c':0,'d':0,'e':0,'f':0,'g':0,'h':0,'i':0},
            'e': {'a':0,'b':1,'c':0,'d':0,'e':0,'f':0,'g':0,'h':1,'i':0},
            'f': {'a':0,'b':1,'c':6,'d':0,'e':0,'f':0,'g':0,'h':2,'i':1},
            'g': {'a':0,'b':5,'c':0,'d':0,'e':0,'f':0,'g':0,'h':9,'i':2},
            'h': {'a':0,'b':0,'c':0,'d':0,'e':1,'f':2,'g':9,'h':0,'i':0},
            'i': {'a':0,'b':0,'c':0,'d':0,'e':0,'f':1,'g':2,'h':0,'i':0}}
        (D,P)=WFI(G)
        #print(D)
        #print(D['b']['c'])
        #print(D['b']['g'])
        #print(D['b']['h'])
        #print(D['c']['g'])
        #print(D['c']['h'])
        #print(D['g']['h'])
        #print('***')
        #print(RevWFI(P,'b','c'))
        #print(RevWFI(P,'b','g'))
        #print(RevWFI(P,'b','h'))
        #print(RevWFI(P,'c','g'))
        #print(RevWFI(P,'c','h'))
        #print(RevWFI(P,'g','h'))

if __name__ == '__main__':
    unittest.main()
