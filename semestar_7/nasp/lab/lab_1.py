class Node:
    
    def __init__(self, v):
        self.V = v
        self.P = self.L = self.R = None
    
    def __str__(self) -> str:
        return "{} parent:{}".format(self.V, self.P.V if self.P is not None else None)
    
    def __repr__(self) -> str:
        return self.__str__()
    
    def setLeftChild(self, node):
        self.L = node
        if node:
            node.P = self
            
    def setRightChild(self, node):
        self.R = node
        if node:
            node.P = self
            
    def rightMost(self):
        if self.R:
            return self.R.rightMost()
        return self
    
    def getPredecessor(self):
        if self.L:
            return self.L.rightMost()
        return None

class BinaryTree:
    
    def __init__(self, root: Node = None):
        self.root = root


def nstr(node: Node, level=0, indentstr="    "):
    lr = ""
    if node.P is not None:
        lr = "L" if node==node.P.L else "R"
    indent = indentstr*level
    return "{}{}:{}".format(indent, lr, node)

def printTree(node: Node) -> None:
    stack = []
    stack.append((node, 0))
    while len(stack) > 0:
        top = stack.pop()
        topnode = top[0]
        l = topnode.L
        r = topnode.R
        level = top[1]
        print(nstr(topnode, level))
        if r is not None:
            stack.append((r, level+1))
        if l is not None:
            stack.append((l, level+1))



def bst_add(root:Node, val):
    scan = root
    while True:
        if scan.V == val:
            return
        goleft = (val < scan.V)
        if goleft:
            if scan.L is None:
                scan.setLeftChild(Node(val))
                return
            scan = scan.L
        else:
            if scan.R is None:
                scan.setRightChild(Node(val))
                return
            scan = scan.R

def brisanjeKopiranjem(stablo: BinaryTree, vrijednost) -> bool:
    # potrebno prvo pronaci cvor sa tom vrijednoscu ako postoji
    if vrijednost is None:
        return False
    scan = stablo.root
    while True:
        if scan is None:
            return False
        if vrijednost == scan.V:
            break

        if vrijednost < scan.V:
            scan = scan.L
        else:
            scan = scan.R
    # u ovom trenutku ako je funkcija dosla do ovdje, scan pokazuje na cvor sa vrijednosti
    has_right = scan.R is not None
    has_left = scan.L is not None
    is_root = scan.P is None
    
    if ((has_left==False) and (has_right==False)):
        if is_root:
            stablo.root = None
        elif scan == scan.P.L:
            scan.P.setLeftChild(None)
        else:
            scan.P.setRightChild(None)
    elif (has_left and has_right):
        pred = scan.getPredecessor()
        scan.V = pred.V
        if pred == pred.P.L:
            pred.P.setLeftChild(None)
        else:
            pred.P.setRightChild(None)
        return True
    elif (has_left):
        if is_root:
            stablo.root = scan.L
            stablo.root.P = None
            return True
        
        if scan == scan.P.L:
            scan.P.setLeftChild(scan.L)
        else:
            scan.P.setRightChild(scan.L)
    else:
        if is_root:
            stablo.root = scan.R
            stablo.root.P = None
            return True
        
        if scan == scan.P.L:
            scan.P.setLeftChild(scan.R)
        else:
            scan.P.setRightChild(scan.R)

    return True


root = Node(1)
bst_add(root, 2)
bst_add(root, 3)
stablo = BinaryTree(root)
printTree(stablo.root)
print("\n\n\n")

brisanjeKopiranjem(stablo, 3)

printTree(stablo.root)
        