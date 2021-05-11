#include "myfactory.h"
#include <stdlib.h>
#include <dlfcn.h>
#include <string.h>

typedef void* (*CREATE_FUN)(const char *);
typedef size_t (*SIZEOF_FUN)(void);
typedef void (*CONSTRUCT_FUN)(void *, const char *);

static const char *cwd = "./";
static const char *extension = ".so";
static const char *sym_create = "create";
static const char *sym_construct = "construct";
static const char *sym_sizeof = "sizeOf";

static void *getHandle(const char *libname) {
	int cwdLen = strlen(cwd);
	int extLen = strlen(extension);
	int libnameLen = strlen(libname);
	int len = cwdLen + extLen + libnameLen + 1;
	char libfilestr[len];
	
	strcpy(libfilestr, cwd);
	strcat(libfilestr, libname);
	strcat(libfilestr, extension);
	
	void *handle = dlopen(libfilestr, RTLD_LAZY);
	return handle;
}

size_t libsize(const char *libname) {
	void *handle = getHandle(libname);
	if (handle == NULL)
		return 0;
	
	void *siz = dlsym(handle, sym_sizeof);
	if (siz == NULL)
		return 0;
	
	SIZEOF_FUN sizfun = (SIZEOF_FUN) siz;
	size_t s = sizfun();
	return s;
}

void *myfactory(char const* libname, char const* ctorarg) {
	
	void *handle = getHandle(libname);
	if (handle == NULL)
		return NULL;
	void *fac = dlsym(handle, sym_create);
	if (fac == NULL)
		return NULL;
	
	CREATE_FUN cf = (CREATE_FUN) fac;
	void *obj = cf(ctorarg);
	// dlclose(handle);
	
	return obj;
}

void *myfac(char const* libname, void *mem, char const* ctorarg) {
	void *handle = getHandle(libname);
	if (handle == NULL)
		return NULL;
	
	void *cons = dlsym(handle, sym_construct);
	if (cons == NULL)
		return NULL;
	
	CONSTRUCT_FUN cf = (CONSTRUCT_FUN) cons;
	cf(mem, ctorarg);
	return mem;
}
