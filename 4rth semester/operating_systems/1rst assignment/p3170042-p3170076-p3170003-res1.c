#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <stdlib.h>
#include <time.h>
#include "p3170042-p3170076-p3170003-res1.h"

int status;

pthread_mutex_t account_m;
int bank_account = 0;

pthread_mutex_t seats_m;

int *seats_plan;

pthread_mutex_t availSeats_m;

unsigned int availSeats;

pthread_mutex_t print_m;

pthread_mutex_t phoners_m;

unsigned int availPhones;
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;

pthread_mutex_t average_waiting_m;
float average_waiting_time = 0;
int client_counter = 0;

pthread_mutex_t average_service_m;
float average_service_time = 0;

unsigned int Ncust;
unsigned int seedp;

int s_random(){
	seedp = rand_r(&seedp);
	return seedp;
}

int destroy(pthread_mutex_t* lock){
	if(pthread_mutex_destroy(lock) != 0){
		printf("\n mutex destroy has failed!\n");
		exit(-1);
	}
	return 0;
}

void destruction(){
		destroy(&account_m);
		destroy(&seats_m);
		destroy(&availSeats_m);
		destroy(&print_m);
		destroy(&phoners_m);
		destroy(&average_waiting_m);
		destroy(&average_service_m);
		//free
		free(seats_plan);
}

/*pelatis->sinalagi me tilefoniti*/
void *client(void * tid){

	//waiting time
	struct timespec start, stop_wait, stop_service;
	double time;
	
	int thread_id = *(int*)tid;
	
	status = pthread_mutex_lock(&phoners_m);
	if(clock_gettime(CLOCK_REALTIME, &start) == -1){
		perror("clock gettime");
		destruction();
		exit(EXIT_FAILURE);
		
	}
	while(availPhones == 0){
		status = pthread_cond_wait(&cond, &phoners_m);
	}


	availPhones--;
	status = pthread_mutex_unlock(&phoners_m);

	if(clock_gettime(CLOCK_REALTIME, &stop_wait) == -1){
		perror("clock gettime");
		destruction();
		exit(EXIT_FAILURE);
	}
	time = (stop_wait.tv_sec - start.tv_sec) + (stop_wait.tv_nsec - start.tv_nsec) / 1000000000L;

	status = pthread_mutex_lock(&average_waiting_m);
	average_waiting_time = (client_counter * average_waiting_time + time) / (client_counter+1);
	status = pthread_mutex_unlock(&average_waiting_m);
	
	int random_time = s_random();

	random_time = (random_time % Tseathigh) + Tseatlow;
	
	int random_tickets = s_random();

	random_tickets = (random_tickets % Nseathigh) + Nseatlow;
	
	if(sleep(random_time) != 0){
		pthread_mutex_lock(&print_m);
		printf("\n Client %d: Thread failed to sleep\n", thread_id);
		pthread_mutex_unlock(&print_m);
		status = pthread_mutex_lock(&phoners_m);
		++availPhones;
		status = pthread_cond_signal(&cond);
		status = pthread_mutex_unlock(&phoners_m);
		pthread_exit(NULL);
	}
	
	pthread_mutex_lock(&availSeats_m);
	int full = (availSeats == 0);
	int noSeats = availSeats < random_tickets;
	pthread_mutex_unlock(&availSeats_m);

	unsigned int tickets_rem = random_tickets;
	int* seats_ids;

	if(noSeats){
		status = pthread_mutex_lock(&phoners_m);
		++availPhones;
		status = pthread_cond_signal(&cond);
		status = pthread_mutex_unlock(&phoners_m);
		
		pthread_mutex_lock(&print_m);
		if(full)  printf("\n Client %d: The reservation was cancelled because the theater is full!\n", thread_id);
		else printf("\n Client %d: The reservation was cancelled due to lack of available seats!\n", thread_id);
		pthread_mutex_unlock(&print_m);
		pthread_exit(NULL);
	}else{
		pthread_mutex_lock(&seats_m);

		seats_ids = (int*)malloc(sizeof(int) * random_tickets);
		if (seats_ids==NULL){
			printf("\nERROR: Malloc failed not enough memory!\n");
			pthread_exit(NULL);
		}

		unsigned int j;
		unsigned int a = 0;
		for(j = 0; j < Nseat && tickets_rem != 0; j++){
			if(seats_plan[j] == 0){
				seats_plan[j] = thread_id;
				seats_ids[a++] = j;
				tickets_rem--;
			}
		}
		pthread_mutex_unlock(&seats_m);
		
		pthread_mutex_lock(&availSeats_m);
		availSeats -= random_tickets;
		pthread_mutex_unlock(&availSeats_m);
	}
	
	int pay_success = s_random() % 100;
	if(pay_success < Pcardsuccess) {
		pthread_mutex_lock(&account_m);
		bank_account += random_tickets * Cseat;
		pthread_mutex_unlock(&account_m);
		
		pthread_mutex_lock(&print_m);

		printf("\n Client %d: The reservation was completed successfully! The id of the transaction is %d, your seats are ",thread_id, thread_id);
                for (int i=0; i<random_tickets; i++){
			printf("%d",seats_ids[i]);
			if (i!=random_tickets-1){
				printf(",");
			}
		}
 		printf(" and the cost of the transaction is %d euros!\n", random_tickets*Cseat);

		pthread_mutex_unlock(&print_m);
	}else{
		pthread_mutex_lock(&seats_m);

		for(int j = 0;j < random_tickets;j++){
			seats_plan[seats_ids[j]] = 0;	
		}
		pthread_mutex_unlock(&seats_m);
		pthread_mutex_lock(&availSeats_m);
		availSeats += random_tickets;
		pthread_mutex_unlock(&availSeats_m);
		
		pthread_mutex_lock(&print_m);
		printf("\n Client %d: The reservation was cancelled because the transaction with the credit card was not accepted\n", thread_id);
		pthread_mutex_unlock(&print_m);
	}

	free(seats_ids);

	//service time
	if(clock_gettime(CLOCK_REALTIME, &stop_service) == -1){
		perror("clock gettime");
		destruction();
		exit(EXIT_FAILURE);
	}
	time = (stop_service.tv_sec - start.tv_sec) + (stop_service.tv_nsec - start.tv_nsec) / 1000000000L;
	status = pthread_mutex_lock(&average_service_m);
	average_service_time = (client_counter * average_service_time + time) / (client_counter+1);
	client_counter += 1;
	status = pthread_mutex_unlock(&average_service_m);
	
	status = pthread_mutex_lock(&phoners_m);
	++availPhones;
	status = pthread_cond_signal(&cond);
	status = pthread_mutex_unlock(&phoners_m);
	
	pthread_exit(NULL);
	
	
}

int initialisation(pthread_mutex_t* lock){
	if(pthread_mutex_init(lock, NULL) != 0){
		printf("\n mutex init has failed\n");
		exit(-1);
	}
	return 0;
}


int main(int argc, char ** argv){
	availSeats = Nseat;
	availPhones = Ntel;

	//mutexes initialisation
	initialisation(&account_m);
	initialisation(&seats_m);
	initialisation(&availSeats_m);
	initialisation(&print_m);
	initialisation(&phoners_m);
	initialisation(&average_waiting_m);
	initialisation(&average_service_m);
	printf("\n");
	seats_plan = (int*)malloc(sizeof(int) * Nseat);
	
	if(seats_plan == NULL){
		printf("\nERROR: Malloc failed not enough memory!\n");
		return -1;
	}
	
	Ncust = atoi(argv[1]);
	seedp = atoi(argv[2]);
	int i;

	for(i=0;i<Nseat;i++){
		seats_plan[i] = 0;
	}


	pthread_t threads[Ncust];
	//malloc
	int thread_id[Ncust];
	
	//create threads
	for(i=0; i<Ncust; i++){
		thread_id[i]=i+1;
		status = pthread_create(&threads[i], NULL, client, (void*) &thread_id[i]);
		if(status!=0){
			printf("Creation of the thread failed");
			destruction();
			exit(-1);
		}
	}

	//wait for threads to finish
	for(i=0;i<Ncust;++i){
		status = pthread_join(threads[i], NULL);
		if(status!=0){
			printf("Join of the thread failed");
			destruction();
			exit(-1);
		}
	}
	
	pthread_mutex_lock(&print_m);
	printf("\n");
	for(i=0;i<Nseat;i++){
		if(seats_plan[i] != 0)
			printf(" Seat %d / Client %d \n", i, seats_plan[i]);
	}
	printf("\n\n Total earnings: %d\n", bank_account);
	printf("\n Average Waiting Time: %f seconds\n", average_waiting_time);
	printf("\n Average Service Time: %f seconds\n", average_service_time);
	pthread_mutex_unlock(&print_m);

	//pthread_mutex_destroy(&lock)
	destruction();
	
	printf("\n");

	exit(EXIT_SUCCESS);
	
}
