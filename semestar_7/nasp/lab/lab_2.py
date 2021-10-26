from math import ceil

class BTNode:
    def __init__(self, value:int=None, L:list=None, deg:int=5, P=None) -> None:
        if value is not None: self.L=[None, value, None]
        else:
            self.L=L
            for item in self.L:
                if type(item) is BTNode: item.P=self
        self.deg=deg
        self.P=P
    def searchValue(self, value:int):
        for index in range(len(self.L)):
            item=self.L[index]
            if type(item) is int:
                lc=self.L[index-1]
                if value==item: return (self, True, index)
                if value<item:
                    if lc is not None: return lc.searchValue(value)
                    else: return (self, False, index)
        rc=self.L[len(self.L)-1]
        if rc is not None: return rc.searchValue(value)
        else: return (self, False, len(self.L))
    def keys(self) -> int:
        return int((len(self.L)-1)/2)
    def minKeys(self) -> int:
        return ceil((self.deg-1)/2)


class BTreeList:
    def __init__(self, value:int, deg:int=5) -> None:
        self.root=BTNode(value=value, deg=deg)
        self.deg=deg
    def searchValue(self, value:int) -> bool:
        (n, f, i)=self.root.searchValue(value)
        return f

class BTreeList(BTreeList):
    def split(self, node):
        if node.keys() <= self.deg - 1:
            return
        # cvor je popunjen, treba razdjeljivati
        mididx = (len(node.L)-1) // 2
        leftsplit = node.L[:mididx]
        rightsplit = node.L[mididx+1:]
        midvalue = node.L[mididx]
        parent = node.P
        if parent is None:
            return
            #novi korijen...
        
        nodeidx = parent.L.index(node)
        parent.L.insert(nodeidx, None)
        parent.L.insert(nodeidx+1, midvalue)
        parent.L[nodeidx] = BTNode(value=None, L=leftsplit, deg=self.deg, P=parent)
        parent.L[nodeidx+2] = BTNode(value=None, L=rightsplit, deg=self.deg, P=parent)
        self.split(parent)
        

    def insertValue(self, value:int) -> None:
        leaf, found, idx = self.root.searchValue(value=value)
        if found:
            return

        leaf.L.insert(idx, value)
        leaf.L.insert(idx+1, None)
        self.split(leaf)


        
