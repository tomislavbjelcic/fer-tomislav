#include <iostream>

using namespace std;

class B
{
public:
    virtual int prva() = 0;
    virtual int druga(int) = 0;
};

class D : public B
{
public:
    virtual int prva() { return 42; }
    virtual int druga(int x) { return prva() + x; }
};

void solution(B *pb, int x) {
    /*
    int p = pb->prva();
    int d = pb->druga(x);
    cout << p << endl << d << endl;
    */

    typedef int (*funPrvaType)(void *);
    typedef int (*funDrugaType)(void *, int);
    /**
     * Cjelobrojni tip bez predznaka koji može spremiti iznos adrese.
     * Za izvršavanje na trenutnom računalu promijeniti u cjelobrojni tip bez predznaka 
     * koji memorijski jednako zauzima kao tip pokazivača.
    */
    typedef unsigned long long addr_t;

    addr_t vtableAddress = *((addr_t *) pb);
    addr_t *vtable = (addr_t *) vtableAddress;
    
    funPrvaType funPrva = (funPrvaType) vtable[0];
    funDrugaType funDruga = (funDrugaType) vtable[1];

    int p = funPrva(pb);
    int d = funDruga(pb, x);
    cout << p << endl << d << endl;
    
    return;
}

int main(void) {
    D d;
    int x = 7;
    solution(&d, x); // za x=7 ispisuje 42 49
    return 0;
}