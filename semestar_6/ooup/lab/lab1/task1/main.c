#include "animal.h"
#include "dog.h"
#include "cat.h"
#include <stdlib.h>
#include <stdio.h>

void testAnimals(void) {
    struct Animal *p1 = createDog("Hamlet");
    struct Animal *p2 = createCat("Ofelija");
    struct Animal *p3 = createDog("Polonije");

    animalPrintGreeting(p1);
    animalPrintGreeting(p2);
    animalPrintGreeting(p3);

    animalPrintMenu(p1);
    animalPrintMenu(p2);
    animalPrintMenu(p3);

    free(p1);
    free(p2);
    free(p3);
}


void testAnimalsUsingStack(void) {
    /*
    Stvaranje nove konkretne životinje smo mogli izdvojiti i u zasebne funkcije
    createDogOnStack i createCatOnStack.
    Potpis i implementacija takvih funkcija bi bila (na primjeru pasa):
    
    Animal createDogOnStack(const char *name) {
        Animal dog;
        constructDog(&dog, name);
        return dog;
    }

    Povratni tip ne smije biti pokazivač jer funkcija stvara objekte na stogu, što znači
    da nakon završetka izvršavanja takve funkcije, ako taj objekt na stogu ne povratimo 
    pozivatelju u cjelosti, on će se uništiti (dealocirati) jer se radi o lokalnoj varijabli.
    A kada bismo pozivatelju vratili adresu (pokazivač) na takav objekt koji bi se uništio, 
    nastao bi problem visećeg pokazivača (referenciranje na dealocirani komad memorije), 
    što za posljedicu ima nedefinirano ponašanje.
    */
    Animal a1;  Animal *p1 = &a1;   constructDog(p1, "Hamlet");
    Animal a2;  Animal *p2 = &a2;   constructCat(p2, "Ofelija");
    Animal a3;  Animal *p3 = &a3;   constructDog(p3, "Polonije");

    animalPrintGreeting(p1);
    animalPrintGreeting(p2);
    animalPrintGreeting(p3);

    animalPrintMenu(p1);
    animalPrintMenu(p2);
    animalPrintMenu(p3);

    /*
    Nije potrebno jer će se objekti sami dealocirati sa stoga nakon završetka
    izvršavanja funkcije testAnimalsUsingStack. 

    free(p1);
    free(p2);
    free(p3);
    */
}

void testCreateManyDogs(void) {
    int n = 5;
    const char *names[] = {"Rex", "Djuro", "Mirko", "Vlado", "Nils"};
    Animal *dogs = createManyDogs(n, names);
    for (int i=0; i<n; i++) {
        printf("Pas broj %d: %s\n", i+1, dogs[i].name);
    }

    free(dogs);
}

int main(void)
{
    testAnimals();
    printf("\n");
    testCreateManyDogs();
    printf("\n");
    testAnimalsUsingStack();
    return 0;
}