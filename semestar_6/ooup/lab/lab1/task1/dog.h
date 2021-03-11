#ifndef DOG_H
#define DOG_H

#include "animal.h"

void constructDog(Animal *dog, const char *name);
Animal *createDog(const char *name);
Animal *createManyDogs(int n, const char **names);

#endif