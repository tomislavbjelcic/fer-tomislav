#include <iostream>

class CoolClass
{
public:
    virtual void set(int x) { x_ = x; };
    virtual int get() { return x_; };

private:
    int x_;
};


class PlainOldClass
{
public:
    void set(int x) { x_ = x; };
    int get() { return x_; };

private:
    int x_;
};

int main(void) {
    std::cout << "Size of PlainOldClass: " << sizeof(PlainOldClass) << std::endl;
    std::cout << "Size of CoolClass: " << sizeof(CoolClass) << std::endl;
    std::cout << "Pointer size: " << sizeof(void*) << std::endl;
    std::cout << "Int size: " << sizeof(int) << std::endl;
    return 0;
}