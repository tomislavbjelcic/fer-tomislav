#include <iostream>

class Point {
    public:
    int x;
    int y;

    Point() : Point(0, 0) {}
    Point(int x, int y): x(x), y(y) {}
};

class Shape {
    public:
    virtual void draw() = 0;
    virtual void move(int cx, int cy) = 0;
};

class CenteredShape : public Shape {
    protected:
    Point center;

    private:
    virtual const char *name() const {
        return "Centered shape";
    }
    virtual void printMyself(std::ostream &os) const {
        
    }


    public:
    CenteredShape(int cx, int cy) : center(cx, cy) {}
    virtual void move(int dx, int dy) override {
        center.x += dx;
        center.y += dy;
    }
    
};

class Circle : public CenteredShape {
    private:
    double r;

    Circle() : Circle(0, 0, 0.0) {}
    Circle(int x, int y, double r) : CenteredShape(x, y), r(r) {}
    virtual void draw() override {

    }
};

int main(void) {
    
    return 0;
}

