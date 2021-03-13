#include "unary_function.h"
#include <stdlib.h>
#include <stdio.h>

struct Unary_Function_Vtable {
	void (*dstr)(Unary_Function *);
	double (*value_at)(Unary_Function *, double);
	double (*negative_value_at)(Unary_Function *, double); 	
};

static Unary_Function_Vtable vtbl = {
	.dstr = &Unary_Function_destruct_impl,
	.value_at = NULL,
	.negative_value_at = &Unary_Function_negative_value_at_impl
};

void Unary_Function_init(Unary_Function *self, int lb, int ub) {
	self->vtbl = &vtbl;
	self->lower_bound = lb;
	self->upper_bound = ub;
}

void Unary_Function_destruct(Unary_Function *self) {
	self->vtbl->dstr(self);
}

void Unary_Function_destruct_impl(Unary_Function *self) {

}

double Unary_Function_value_at(Unary_Function *self, double x) {
	return self->vtbl->value_at(self, x);
}

double Unary_Function_negative_value_at(Unary_Function *self, double x) {
	return self->vtbl->negative_value_at(self, x);
}

double Unary_Function_negative_value_at_impl(Unary_Function *self, double x) {
	return -Unary_Function_value_at(self, x);
}

void Unary_Function_tabulate(Unary_Function *self) {
	for (int x = self->lower_bound; x <= self->upper_bound; x++) {
		printf("f(%d)=%lf\n", x, Unary_Function_value_at(self, x));
	}
}

bool Unary_Function_same_functions_for_ints(Unary_Function *f1, Unary_Function *f2, double tolerance) {
	if (f1->lower_bound != f2->lower_bound)
        return false;
    if (f1->upper_bound != f2->upper_bound)
        return false;
    for (int x = f1->lower_bound; x <= f1->upper_bound; x++) {
        double delta = Unary_Function_value_at(f1, x) - Unary_Function_value_at(f2, x);
        if (delta < 0)
            delta = -delta;
        if (delta > tolerance)
            return false;
   	}
    return true;
};
