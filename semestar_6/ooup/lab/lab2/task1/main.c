#include <stdio.h>
#include <string.h>

typedef int (*CMPFUN)(const void *, const void *);

const void* mymax
	(const void *base, size_t nmemb, size_t size, 
	CMPFUN compar) {
	
	if (nmemb <= 0) return 0;
	
	const void *max = base;
	const char *it = (const char *) base;
	for (size_t i=0; i<nmemb; i++) {
		const void *elem = (const void *) (it + i*size);
		int cmp = compar(elem, max);
		if (cmp)
			max = elem;
	}	
	
	return max;
}

int gt_int(const int *e1, const int *e2) {
	return *e1 > *e2;
}


int gt_char(const char *e1, const char *e2) {
	return *e1 > *e2;
}

int gt_str(const char **e1, const char **e2) {
	int strcp = strcmp(*e1, *e2);
	return strcp > 0;
}

int main(void) {
	
	int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
	size_t nint = 9;
	const int *imaxptr = (const int *) mymax((const void *) arr_int, nint, sizeof(int), (CMPFUN) &gt_int);
	printf("Max int: %d\n", *imaxptr);
	
	char arr_char[] = "Suncana strana ulice";
	size_t nchar = strlen(arr_char);
	const char *charmaxptr = (const char *) mymax((const void *) arr_char, nchar, sizeof(char), (CMPFUN) &gt_char);
	printf("Max char: %c\n", *charmaxptr);
	
	const char* arr_str[] = {
   "Gle", "malu", "vocku", "poslije", "kise",
   "Puna", "je", "kapi", "pa", "ih", "njise"};
   size_t nstr = 11;
   const char **strmaxptr = (const char **) mymax((const void *) arr_str, nstr, sizeof(const char *), (CMPFUN) &gt_str);
   printf("Max string: %s\n", *strmaxptr);
	
	
	return 0;
}
