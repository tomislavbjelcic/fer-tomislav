import numpy as np

def qr_givens(A):
    m = A.shape[0]
    n = A.shape[1]
    Q = np.eye(m, m)
    R = A
    for l in range(n):
        for i in range(m-1, l, -1):
            if (R[i][l] == 0):
                continue
            r = np.linalg.norm([R[l][l], R[i][l]])
            G = np.eye(m, m)
            c = R[l][l] / r
            s = -R[i][l] / r
            G[l][l] = c
            G[i][i] = c
            G[i][l] = s
            G[l][i] = -s
            R = np.dot(G, R)

            # ako ne želimo jako male brojeve ispod glavne dijagonale
            R[i][l] = 0

            Q = np.dot(Q, G.T)
            
    return Q, R

def check_q(Q):
    # provjeri je li Q ortogonalna
    n = Q.shape[0]
    return np.allclose(np.dot(Q.T, Q), np.eye(n, n))

def check_r(R):
    # provjeri je li R gornja trokutasta
    return np.allclose(R, np.triu(R))

def check_qr(A, Q, R):
    # provjeri je li A=QR
    return np.allclose(A, np.dot(Q, R))

if __name__ == "__main__":
    A = np.random.randn(20, 10)
    Q, R = qr_givens(A)

    print(f"A = {A}\n")
    print(f"Q = {Q}\n")
    print(f"R = {R}\n")



    print("Provjera...")
    print(f"A=QR? {check_qr(A,Q,R)}")
    print(f"Q ortogonalna? {check_q(Q)}")
    print(f"R gornja trokutasta? {check_r(R)}")
    
