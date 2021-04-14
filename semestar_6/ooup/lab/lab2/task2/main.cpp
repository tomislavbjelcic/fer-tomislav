#include <iostream>
#include <vector>

template <typename Iterator, typename Predicate>
Iterator mymax(Iterator first, Iterator last, Predicate pred){
	Iterator maxiter = first;
	while (first != last) {
		if (pred(*first, *maxiter))
			maxiter = first;
		first++;
	}
	return maxiter;
}

int gt_int(const int &i1, const int &i2) {
	return i1 > i2;
}

int gt_string(const char &c1, const char &c2) {
	return c1 > c2;
}

int main(void) {
	int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
	const int *maxint = mymax(&arr_int[0], &arr_int[sizeof(arr_int)/sizeof(*arr_int)], gt_int);
	std::cout << "Max int: " << *maxint << "\n";
	
	std::string str("Suncana strana ulice");
	auto result = mymax(str.begin(), str.end(), gt_string);
	std::cout << "Max char: " << *result << "\n";
	
	std::vector<int> vec;

	vec.push_back(-5);
	vec.push_back(10);
	vec.push_back(-9);
	vec.push_back(55);
	vec.push_back(21);
	
	auto res = mymax(vec.begin(), vec.end(), gt_int);
	std::cout << "Max int in vector: " << *res << "\n";


	return 0;
}
