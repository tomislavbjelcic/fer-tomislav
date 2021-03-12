#ifndef SQUARE_H
#define SQUARE_H

#include "unary_function.h"

typedef struct Square_Vtable Square_Vtable;
typedef struct Square Square;

struct Square_Vtable {
	void (*dstr)(Unary_Function *);
	double (*value_at)(Square *, double);
	double (*negative_value_at)(Unary_Function *, double); 	
};

struct Square {
	Square_Vtable *vtbl;
	int lower_bound;
	int upper_bound;
};


Square *Square_create(int lb, int ub);
void Square_init(Square *self, int lb, int ub);
void Square_destroy(Square *self);
double Square_value_at(Square *self, double x);


#endif
