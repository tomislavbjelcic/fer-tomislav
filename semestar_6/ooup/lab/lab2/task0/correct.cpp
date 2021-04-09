#include <iostream>

class Point {
    public:
    int x;
    int y;

    Point() : Point(0, 0) {}
    Point(int x, int y): x(x), y(y) {}
};
std::ostream &operator<<(std::ostream &os, const Point &p) {
    os << "(" << p.x << ", " << p.y << ")";
   	return os;
}

class Shape {
	

	protected:
	virtual void printMyself(std::ostream &os) const = 0;

    public:
    virtual void draw() const = 0;
    virtual void move(int cx, int cy) = 0;
    virtual const char *shapeName() const = 0;
    
    friend std::ostream &operator<<(std::ostream &os, const Shape &s);
};

std::ostream &operator<<(std::ostream &os, const Shape &s) {
	s.printMyself(os);
	return os;
}

class CenteredShape : public Shape {
	
    protected:
    Point center;

    virtual void printMyself(std::ostream &os) const override {
        os << shapeName() << " with center " << center;
    }


    public:
    CenteredShape(int cx, int cy) : center(cx, cy) {}
    virtual void move(int dx, int dy) override {
        center.x += dx;
        center.y += dy;
    }
    
};

class Circle : public CenteredShape {
    
    double r;
    
    protected:
    virtual void printMyself(std::ostream &os) const override {
        CenteredShape::printMyself(os);
        os << " and radius " << r;
    }
	
	public:
    Circle() : Circle(0, 0, 0.0) {}
    Circle(int cx, int cy, double r) : CenteredShape(cx, cy), r(r) {}
    virtual void draw() const override {
		std::cout << "drawCircle" << std::endl;
    }
    virtual const char *shapeName() const override {
    	return "Circle";
    }
    
};

class Square : public CenteredShape {
	
	double side;
	
	protected:	
	virtual void printMyself(std::ostream &os) const override {
        CenteredShape::printMyself(os);
        os << " and side " << side;
    }
    
    public:
    Square() : Square(0, 0, 0.0) {}
    Square(int cx, int cy, double side) : CenteredShape(cx, cy), side(side) {}
    virtual void draw() const override {
		std::cout << "drawSquare" << std::endl;
    }
    virtual const char *shapeName() const override {
    	return "Square";
    }
	
};

class Rhomb : public CenteredShape {
	
	double side;
	double angle;
	
	protected:	
	virtual void printMyself(std::ostream &os) const override {
        CenteredShape::printMyself(os);
        os << ", side " << side << " and angle " << angle;
    }
    
    public:
    Rhomb() : Rhomb(0, 0, 0.0, 0.0) {}
    Rhomb(int cx, int cy, double side, double angle) : CenteredShape(cx, cy), side(side), angle(angle) {}
    virtual void draw() const override {
		std::cout << "drawRhomb" << std::endl;
    }
    virtual const char *shapeName() const override {
    	return "Rhomb";
    }
	
};

void drawShapes(Shape **shapes, int n) {
	for (int i=0; i<n; i++) {
		Shape *s = shapes[i];
		s->draw();
	}
}

void moveShapes(Shape **shapes, int n, int dx, int dy) {
	for (int i=0; i<n; i++) {
		Shape *s = shapes[i];
		s->move(dx, dy);
	}
}

void printShapes(Shape **shapes, int n) {
	const char nl = '\n';
	for (int i=0; i<n; i++)
		std::cout << *shapes[i] << nl;
}

int main(void) {
	const int n = 3;
	Shape *shapes[n];
	
	shapes[0] = new Circle(3, 0, 1);
	shapes[1] = new Square(1, 2, 3);
	shapes[2] = new Rhomb(7, 3, 2, 1);
	printShapes(shapes, n);
	
    drawShapes(shapes, n);
    moveShapes(shapes, n, 5, -3);
    
    printShapes(shapes, n);
    
    return 0;
}

