#ifndef LINEAR_H
#define LINEAR_H

typedef struct Linear_Vtable Linear_Vtable;
typedef struct Linear Linear;

struct Linear {
	Linear_Vtable *vtbl;
	int lower_bound;
	int upper_bound;
    double a;
    double b;
};


Linear *Linear_create(int lb, int ub, double a_coef, double b_coef);	// poziv operatora new
void Linear_init(Linear *self, int lb, int ub, double a_coef, double b_coef);	// konstruktor
void Linear_destroy(Linear *self);	// poziv operatora delete

double Linear_value_at_impl(Linear *self, double x);

#endif