#ifndef SQUARE_H
#define SQUARE_H

typedef struct Square_Vtable Square_Vtable;
typedef struct Square Square;

struct Square {
	Square_Vtable *vtbl;
	int lower_bound;
	int upper_bound;
};


Square *Square_create(int lb, int ub);	// poziv operatora new
void Square_init(Square *self, int lb, int ub);	// konstruktor
void Square_destroy(Square *self);	// poziv operatora delete

double Square_value_at_impl(Square *self, double x);


#endif
