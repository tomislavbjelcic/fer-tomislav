import torch
import torch.nn as nn

class IdentityModel(nn.Module):
    def __init__(self):
        super(IdentityModel, self).__init__()

    def get_features(self, img):
        # YOUR CODE HERE
        feats = torch.flatten(img, start_dim=1)
        return feats

    