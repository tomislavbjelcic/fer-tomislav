#include <iostream>
#include <assert.h>
#include <stdlib.h>

struct Point
{
    int x;
    int y;

    Point() : Point(0, 0) {}
    Point(int x, int y): x(x), y(y) {}
    friend std::ostream &operator<<(std::ostream &os, const Point &p) {
        os << "(" << p.x << ", " << p.y << ")";
        return os;
    }
};
struct Shape
{
    enum EType
    {
        circle,
        square,
        rhomb
    };
    EType type_;
};
struct Circle
{
    Shape::EType type_;
    double radius_;
    Point center_;

    Circle(): Circle(0, 0, 0.0) {}
    Circle(int cx, int cy, double r): radius_(r), center_(cx, cy) {}
    friend std::ostream &operator<<(std::ostream &os, const Circle &c) {
        os << "Circle with center " << c.center_ << " and radius " << c.radius_;
        return os;
    }
};
struct Square
{
    Shape::EType type_;
    double side_;
    Point center_;

    Square(): Square(0, 0, 0.0) {}
    Square(int cx, int cy, double side_): side_(side_), center_(cx, cy) {}
    friend std::ostream &operator<<(std::ostream &os, const Square &sq) {
        os << "Square with center " << sq.center_ << " and side " << sq.side_;
        return os;
    }
};
struct Rhomb {
    Shape::EType type_;
    double side_;
    double angle_;
    Point center_;

    Rhomb(): Rhomb(0, 0, 0.0, 0.0) {}
    Rhomb(int cx, int cy, double side_, double angle_): side_(side_), angle_(angle_), center_(cx, cy) {}
    friend std::ostream &operator<<(std::ostream &os, const Rhomb &sq) {
        os << "Rhomb with center " << sq.center_ << ", side " << sq.side_ << " and angle " << sq.angle_;
        return os;
    }
};

void drawSquare(struct Square *)
{
    std::cerr << "in drawSquare\n";
}
void drawCircle(struct Circle *)
{
    std::cerr << "in drawCircle\n";
}
void drawRhomb(struct Rhomb *)
{
    std::cerr << "in drawRhomb\n";
}
void drawShapes(Shape **shapes, int n)
{
    for (int i = 0; i < n; ++i)
    {
        struct Shape *s = shapes[i];
        switch (s->type_)
        {
        case Shape::square:
            drawSquare((struct Square *)s);
            break;
        case Shape::circle:
            drawCircle((struct Circle *)s);
            break;
        case Shape::rhomb:
            drawRhomb((struct Rhomb *)s);
            break;
        default:
            assert(0);
            exit(0);
        }
    }
}

void moveShapes(Shape **shapes, int n, int dx, int dy) {
    for (int i=0; i<n; i++) {
        Shape *s = shapes[i];
        switch(s->type_) {
            case Shape::EType::circle: {
                Circle *circle = (Circle *) s;
                circle->center_.x += dx;
                circle->center_.y += dy;
                break;
            }
            case Shape::EType::square: {
                Square *sq = (Square *) s;
                sq->center_.x += dx;
                sq->center_.y += dy;
                break;
            }
            // zaboravljen Shape::EType::rhomb
            default:
                assert(0);
                exit(0);
        }
    }
}
int main(void)
{
    
    int n = 5;
    Shape *shapes[n];

    Circle *c0 = new Circle(3, 0, 1);
    std::cout << *c0 << "\n";
    shapes[0] = (Shape *) c0;
    shapes[0]->type_ = Shape::circle;

    Square *sq1 = new Square(1, 2, 3);
    std::cout << *sq1 << "\n";
    shapes[1] = (Shape *)sq1;
    shapes[1]->type_ = Shape::square;

    Square *sq2 = new Square(-2, -1, 5);
    std::cout << *sq2 << "\n";
    shapes[2] = (Shape *)sq2;
    shapes[2]->type_ = Shape::square;

    Circle *c3 = new Circle(3, 2, 4);
    std::cout << *c3 << "\n";
    shapes[3] = (Shape *)c3;
    shapes[3]->type_ = Shape::circle;

    Rhomb *r4 = new Rhomb(7, 3, 2, 1);
    std::cout << *r4 << "\n";
    shapes[4] = (Shape *) r4;
    shapes[4]->type_ = Shape::rhomb;

    drawShapes(shapes, n);
    //moveShapes(shapes, n, 5, -3); raspad

    std::cout << *c0 << "\n";
    std::cout << *sq1 << "\n";
    std::cout << *sq2 << "\n";
    std::cout << *c3 << "\n";
    std::cout << *r4 << "\n";

    
    return 0;
}