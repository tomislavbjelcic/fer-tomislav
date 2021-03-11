#ifndef ANIMAL_H
#define ANIMAL_H

typedef const char* (*PTRFUN)();

typedef struct Animal_vtable {
    PTRFUN greet;
    PTRFUN menu;
} Animal_vtable;

typedef struct Animal {
    const char *name;
    const Animal_vtable *vtable;
} Animal;

void animalPrintGreeting(const Animal *animal);
void animalPrintMenu(const Animal *animal);

#endif