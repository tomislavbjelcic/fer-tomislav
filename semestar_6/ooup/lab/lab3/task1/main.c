#include "myfactory.h"

#include <stdio.h>
#include <stdlib.h>
#include "animal.h"

int main(int argc, char *argv[]){
  for (int i=1; i<argc; ++i){
  	char *arg = argv[i];
  	
  	size_t size = libsize(arg);
  	if (size == 0) {
  		printf("Creation of plug-in object %s failed.\n", arg);
      	continue;
  	}
  	
  	char cmem[size];
  	void *mem = cmem;
  	
    Animal *p = (Animal *) myfac(arg, mem, "Modrobradi");
    if (p == NULL){
      printf("Creation of plug-in object %s failed.\n", arg);
      continue;
    }
    
    printf("%s\n", animalName(p));
    animalPrintGreeting(p);
    animalPrintMenu(p);
    // free(p); samo ako se radi o alokaciji na gomili
  }
  
  return 0;
}
