#include "square.h"
#include "unary_function.h"
#include <stdlib.h>

static double value_at_impl_(Square *self, double x) {
	return x*x;
}

const Square_Vtable squareVtbl = {
	.dstr = unaryFunctionVtbl.dstr,
	.value_at = &value_at_impl_,
	.negative_value_at = unaryFunctionVtbl.negative_value_at
};

Square *Square_create(int lb, int ub) {
	Square *sq = malloc(sizeof(Square));
	Square_init(sq, lb, ub);
	return sq;
}



void Square_init(Square *self, int lb, int ub) {
	Unary_Function_init(self, lb, ub);
	self->vtbl = &squareVtbl;
}

void Square_destroy(Square *self) {
	Unary_Function_destruct(self);
	free(self);
};

double Square_value_at(Square *self, double x) {
	return self->vtbl->value_at(self, x);
};
