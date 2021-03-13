#include "square.h"
#include "unary_function.h"
#include <stdlib.h>

struct Square_Vtable {
	void (*dstr)(Unary_Function *);
	double (*value_at)(Square *, double);
	double (*negative_value_at)(Unary_Function *, double); 	
};

static Square_Vtable vtbl = {
	.dstr = &Unary_Function_destruct_impl,
	.value_at = &Square_value_at_impl,
	.negative_value_at = &Unary_Function_negative_value_at
};

Square *Square_create(int lb, int ub) {
	Square *sq = malloc(sizeof(Square));
	Square_init(sq, lb, ub);
	return sq;
}

void Square_init(Square *self, int lb, int ub) {
	Unary_Function_init((Unary_Function *)self, lb, ub);
	self->vtbl = &vtbl;
}

void Square_destroy(Square *self) {
	Unary_Function_destruct((Unary_Function *)self);
	free(self);
};

double Square_value_at_impl(Square *self, double x) {
	return x*x;
};
