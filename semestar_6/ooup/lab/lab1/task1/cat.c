#include "cat.h"
#include <stdlib.h>

static const char *catGreet(void) {
    return "mijau!";
}

static const char *catMenu(void) {
    return "konzerviranu tunjevinu";
}

static Animal_vtable cat_vtable = {
    .greet = &catGreet,
    .menu = &catMenu
};

void constructCat(Animal *cat, const char *name) {
    cat->name = name;
    cat->vtable = &cat_vtable;
}

Animal *createCat(const char *name) {
    Animal *cat = malloc(sizeof(Animal));
    constructCat(cat, name);
    return cat;
}