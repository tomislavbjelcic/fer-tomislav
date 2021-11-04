#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/msg.h>
#include <sys/ipc.h>
#include <unistd.h>

#define POSJETITELJA 8
#define MAX_MJESTA 4

uid_t UID;
int MSGQ_ID_POS;
int MSGQ_ID_VRT;


void queue_remove(void) {
    //pobrisi redove
    printf("Brisem redove poruka...\n");
    if (msgctl(MSGQ_ID_POS, IPC_RMID, NULL) != -1)
        printf("Izbrisan red poruka ID %d\n", MSGQ_ID_POS);
    if (msgctl(MSGQ_ID_VRT, IPC_RMID, NULL) != -1)
        printf("Izbrisan red poruka ID %d\n", MSGQ_ID_VRT);
}

void queue_create(void) {
    UID = getuid();
    if ((MSGQ_ID_VRT = msgget(UID, 0600 | IPC_CREAT)) == -1) {
        queue_remove();
        exit(1);
    }
    printf("Uspješno stvoren red poruka ID %d\n", MSGQ_ID_VRT);
    if ((MSGQ_ID_POS = msgget(UID+1, 0600 | IPC_CREAT)) == -1) {
        queue_remove();
        exit(1);
    }
    printf("Uspješno stvoren red poruka ID %d\n", MSGQ_ID_POS);
}

void interrupted(int signal) {
    printf("Prekid!!!\n");
    queue_remove();
    exit(1);
}

void posjetitelj(int i) {
    printf("Početak izvršavanja procesa posjetitelja broj %d\n", i);


    //POSJETITELJ i

    printf("Završetak izvršavanja procesa posjetitelja broj %d\n", i);
}

int main(void) {
    printf("Početak izvršavanja procesa vrtuljka\n");
    sigset(SIGINT, interrupted); 

    queue_create();

    int i;
	for (i = 0 ; i < POSJETITELJA; i++) {
		switch(fork()) {
			case 0:
				posjetitelj(i);
				exit(0);
			case -1:
				printf("Greska pri stvaranju novog procesa!\n");
                queue_remove();
				exit(1);
			
		}
	}

    //VRTULJAK
	
	for ( ; i > 0 ; i--)
		wait(NULL);

    queue_remove();
    printf("Završetak izvršavanja procesa vrtuljka.\n");
    return 0;
}