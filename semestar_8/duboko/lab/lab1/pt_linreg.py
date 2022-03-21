import torch
import torch.optim as optim


## Definicija računskog grafa
# podaci i parametri, inicijalizacija parametara
a = torch.randn(1, requires_grad=True)
b = torch.randn(1, requires_grad=True)

X = torch.tensor([1, 1, 2, 2])
Y = torch.tensor([2, 3, 1, 3])

# optimizacijski postupak: gradijentni spust
optimizer = optim.SGD([a, b], lr=0.1)
n_iter = 100


for i in range(1, n_iter+1):
    # afin regresijski model
    Y_ = a*X + b

    diff = (Y-Y_)



    # kvadratni gubitak
    #loss = torch.sum(diff**2)
    loss = torch.mean(diff ** 2)

    # računanje gradijenata
    loss.backward()

    # računanje gradijenta pješke
    manual_grad_a = torch.mean(-2*diff*X)
    manual_grad_b = torch.mean(-2*diff)


    # korak optimizacije
    optimizer.step()
    print(f'step: {i}, loss:{loss}, Y_:{Y_}, grad_a:{a.grad}, grad_b:{b.grad}, manual_grad_a:{manual_grad_a}, manual_grad_b:{manual_grad_b}, a:{a}, b:{b}')

    # Postavljanje gradijenata na nulu
    optimizer.zero_grad()