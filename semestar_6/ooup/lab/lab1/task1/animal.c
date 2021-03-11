#include "animal.h"
#include <stdio.h>

void animalPrintGreeting(const Animal *animal) {
    const char *greetMsg = animal->vtable->greet();
    printf("%s pozdravlja: %s\n", animal->name, greetMsg);
    return;
};

void animalPrintMenu(const Animal *animal) {
    const char *menuMsg = animal->vtable->menu();
    printf("%s voli %s\n", animal->name, menuMsg);
    return;
};