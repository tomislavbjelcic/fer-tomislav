import torch
import torch.nn as nn
import torch.nn.functional as F


class _BNReluConv(nn.Sequential):
    def __init__(self, num_maps_in, num_maps_out, k=3, bias=True):
        super(_BNReluConv, self).__init__()
        # YOUR CODE HERE
        self.append(nn.BatchNorm2d(num_features=num_maps_in))
        self.append(nn.ReLU())
        self.append(nn.Conv2d(in_channels=num_maps_in, out_channels=num_maps_out, kernel_size=k, padding="same"))
        #torch.nn.Conv2d(in_channels, out_channels, kernel_size, stride=1, padding=0, dilation=1, groups=1, bias=True, padding_mode='zeros', device=None, dtype=None)
        

class SimpleMetricEmbedding(nn.Module):
    def __init__(self, input_channels, emb_size=32):
        super().__init__()
        self.emb_size = emb_size
        # YOUR CODE HERE
        self.bnrc1 = _BNReluConv(num_maps_in=input_channels, num_maps_out=emb_size, k=3)
        self.mp1 = nn.MaxPool2d(kernel_size=3, stride=2)
        self.bnrc2 = _BNReluConv(num_maps_in=emb_size, num_maps_out=emb_size, k=3)
        self.mp2 = nn.MaxPool2d(kernel_size=3, stride=2)
        self.bnrc3 = _BNReluConv(num_maps_in=emb_size, num_maps_out=emb_size, k=3)
        self.gmp = nn.AdaptiveAvgPool2d(output_size=1)

    def get_features(self, img):
        # Returns tensor with dimensions BATCH_SIZE, EMB_SIZE
        # YOUR CODE HERE
        x = self.bnrc1(img)
        x = self.mp1(x)
        x = self.bnrc2(x)
        x = self.mp2(x)
        x = self.bnrc3(x)
        x = self.gmp(x)
        x = x.squeeze()
        return x

    def loss(self, anchor, positive, negative):
        a_x = self.get_features(anchor)
        p_x = self.get_features(positive)
        n_x = self.get_features(negative)
        # YOUR CODE HERE
        # loss = torch.max(torch.tensor(0), torch.sum(torch.square(a_x - p_x), axis=1) - torch.sum(torch.square(a_x - n_x), axis=1) + 1.0)
        loss = torch.mean(
            torch.max(
                torch.tensor(0), 
                torch.linalg.norm(a_x - p_x, axis=1) - torch.linalg.norm(a_x - n_x, axis=1) + 1.0
            )
        )
        return loss
