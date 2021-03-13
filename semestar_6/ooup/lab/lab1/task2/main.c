#include <stdio.h>
#include "unary_function.h"
#include "square.h"
#include "linear.h"

int main(void) {
	Square *sq1 = Square_create(-2, 2);
	Unary_Function *f1 = (Unary_Function *) sq1;
	Unary_Function_tabulate(f1);

	Linear *ln1 = Linear_create(-2, 2, 5, -2);
	Unary_Function *f2 = (Unary_Function *) ln1;
	Unary_Function_tabulate(f2);

	printf("f1==f2: %s\n", Unary_Function_same_functions_for_ints(f1, f2, 1E-6) ? "DA" : "NE");
	printf("neg_val f1(1) = %lf\n", Unary_Function_negative_value_at(f1, 1.0));
    printf("neg_val f2(1) = %lf\n", Unary_Function_negative_value_at(f2, 1.0));

	Square_destroy(sq1);
	Linear_destroy(ln1);
	return 0;
}
