opts="-Wall -Wextra -ansi -pedantic -std=c11"

gcc $opts -shared -fPIC tiger.c -o tiger.so
gcc $opts -shared -fPIC parrot.c -o parrot.so
gcc -g $opts main.c myfactory.c animal.c -ldl
