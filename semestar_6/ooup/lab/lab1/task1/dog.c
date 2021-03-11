#include "dog.h"
#include <stdlib.h>

static const char *dogGreet(void) {
    return "vau!";
}

static const char *dogMenu(void) {
    return "kuhanu govedinu";
}

static Animal_vtable dog_vtable = {
    .greet = &dogGreet,
    .menu = &dogMenu
};

void constructDog(Animal *dog, const char *name) {
    dog->name = name;
    dog->vtable = &dog_vtable;
}

Animal *createDog(const char *name) {
    Animal *dog = malloc(sizeof(Animal));
    constructDog(dog, name);
    return dog;
}

Animal *createManyDogs(const int n, const char **names) {
    if (n<1) return NULL;
    Animal *dogs = malloc(n * sizeof(Animal));
    for (int i=0; i<n; i++) {
        constructDog(dogs + i, names[i]);
    }
    return dogs;
}
