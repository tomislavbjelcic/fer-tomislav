#ifndef UNARY_FUNCTION_H
#define UNARY_FUNCTION_H

#include <stdbool.h>

typedef struct Unary_Function_Vtable Unary_Function_Vtable;
typedef struct Unary_Function Unary_Function;

struct Unary_Function {
	Unary_Function_Vtable *vtbl;
	int lower_bound;
	int upper_bound;
};

/*
Koja god funkcija uz nju ima i funkciju sa dodatkom _impl, to mi označava da se radi o virtualnoj funkciji
čija ponuđena implementacija se nalazi u funkciji sa dodatkom _impl.
*/

void Unary_Function_init(Unary_Function *self, int lb, int ub);	// konstruktor
void Unary_Function_destruct(Unary_Function *self);	// destruktor, kojeg sam dodatno učinio virtualnim
void Unary_Function_destruct_impl(Unary_Function *self);

double Unary_Function_value_at(Unary_Function *self, double x);
//double Unary_Function_value_at_impl(Unary_Function *self, double x);
double Unary_Function_negative_value_at(Unary_Function *self, double x);
double Unary_Function_negative_value_at_impl(Unary_Function *self, double x);
void Unary_Function_tabulate(Unary_Function *self);
bool Unary_Function_same_functions_for_ints(Unary_Function *f1, Unary_Function *f2, double tolerance);

#endif
