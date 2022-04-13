import numpy as np
import sklearn
import matplotlib.pyplot as plt
from sklearn.preprocessing import PolynomialFeatures
from sklearn.linear_model import LinearRegression

def f(x):
    return x**2 - 3*x + 2

def least_squares(X, y):
    q, r = np.linalg.qr(X, mode="reduced")
    y_ = np.dot(q.T, y)
    return np.linalg.solve(r, y_) #bacit ce gresku ako r nije regularna


if __name__ == "__main__":
    #np.random.seed(100)


    N = 50
    low = -3
    high = 3
    epsilon = 0
    x = (high - low) * np.random.random_sample(size=N) + low
    x_continuous = np.linspace(start=low, stop=high, num=1000) # glumi kontinuirani skup [-3, 3]
    y = f(x) + epsilon * np.random.randn(N)
    plt.scatter(x, y)

    X = x.reshape((N, 1))
    X_continuous = x_continuous.reshape((len(x_continuous), 1))

    pf = PolynomialFeatures(degree=2)
    X_ = pf.fit_transform(X) # ovo ćemo predati funkciji least_squares i ima jedinice u prvom stupcu
    X_continuous_ = pf.fit_transform(X_continuous)

    pf_nobias = PolynomialFeatures(degree=2, include_bias=False)
    X_nobias = pf_nobias.fit_transform(X) # za potrebe algoritma LinearRegression
    X_continuous_nobias = pf_nobias.fit_transform(X_continuous)


    # napravi predikcije metodom least_squares
    coefs = least_squares(X_, y)
    print(f"Moji koeficijenti: {coefs}\n")
    y_my_pred = np.dot(X_continuous_, coefs)
    plt.plot(x_continuous, y_my_pred, c="g", label="moja")

    # napravi predikcije koristeci LinearRegression
    model = LinearRegression()
    model.fit(X_nobias, y)
    print(f"sklearn.linear_model.LinearRegression koeficijenti:\nTežine: {model.coef_}\nPomak: {model.intercept_}")
    y_sklearn_pred = model.predict(X_continuous_nobias)
    plt.plot(x_continuous, y_sklearn_pred, c="y", label="sklearn")

    # nacrtaj teorijsku parabolu
    plt.plot(x_continuous, f(x_continuous), c="r", label="teorijska")


    plt.xlabel("x")
    plt.ylabel("y")
    plt.legend(loc="best")
    plt.show()
    
    
    