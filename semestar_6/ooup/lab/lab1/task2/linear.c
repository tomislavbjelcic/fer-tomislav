#include "linear.h"
#include "unary_function.h"
#include <stdlib.h>

struct Linear_Vtable {
    void (*dstr)(Unary_Function *);
	double (*value_at)(Linear *, double);
	double (*negative_value_at)(Unary_Function *, double);
};

Linear_Vtable vtbl = {
    .dstr = &Unary_Function_destruct_impl,
    .value_at = &Linear_value_at_impl,
    .negative_value_at = &Unary_Function_negative_value_at_impl
};

Linear *Linear_create(int lb, int ub, double a_coef, double b_coef) {
    Linear *ln = malloc(sizeof(Linear));
    Linear_init(ln, lb, ub, a_coef, b_coef);
    return ln;
}

void Linear_init(Linear *self, int lb, int ub, double a_coef, double b_coef) {
    Unary_Function_init((Unary_Function *)self, lb, ub);
    self->vtbl = &vtbl;
    self->a = a_coef;
    self->b = b_coef;
}

void Linear_destroy(Linear *self) {
    Unary_Function_destruct((Unary_Function *)self);
    free(self);
}

double Linear_value_at_impl(Linear *self, double x) {
    double val = self->a * x + self->b;
    return val;
}