#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <stdlib.h>
#include <time.h>
#include "header.h"

int status;

//Bank Account
pthread_mutex_t account_m;
int bank_account = 0;

//Seats Plan
pthread_mutex_t zoneA_m;
int *zoneA;
pthread_mutex_t zoneB_m;
int *zoneB;
pthread_mutex_t zoneC_m;
int *zoneC;

//Seats
pthread_mutex_t availZoneA_m;
unsigned int availZoneA;
pthread_mutex_t availZoneB_m;
unsigned int availZoneB;
pthread_mutex_t availZoneC_m;
unsigned int availZoneC;

//Screen lock
pthread_mutex_t print_m;

//Phoners
pthread_mutex_t phoners_m;
unsigned int availPhones;
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;

//Cashiers
unsigned int availCashiers;
pthread_mutex_t cashiers_m;
pthread_cond_t cond1 = PTHREAD_COND_INITIALIZER;

//Average Waiting Time
pthread_mutex_t average_waiting_m;
float average_waiting_time = 0;
int client_counter = 0;

//Average Service Time
pthread_mutex_t average_service_m;
float average_service_time = 0;


pthread_mutex_t full_theater_m;
float full_theater = 0;
pthread_mutex_t seats_not_found_m;
float seats_not_found = 0;
pthread_mutex_t fail_m;
float fail = 0;
pthread_mutex_t perfect_m;
float perfect = 0;


//Terminal Arguments
unsigned int Ncust;
unsigned int seedp;


//Pseudo_random Generator
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
		destroy(&zoneA_m);
		destroy(&zoneB_m);
		destroy(&zoneC_m);
		destroy(&availZoneA_m);
		destroy(&availZoneB_m);
		destroy(&availZoneC_m);
		destroy(&print_m);
		destroy(&phoners_m);
		destroy(&cashiers_m);
		destroy(&average_waiting_m);
		destroy(&average_service_m);
		//free
		free(zoneA);
		free(zoneB);
		free(zoneC);
}

void availabilityZone(int zone_id ,int random_tickets, int thread_id, int* seats_ids){
	
	int rows;
	unsigned int avail;
	pthread_mutex_t avail_m;
	int* seats;
	pthread_mutex_t seats_m;

	if(zone_id == 1){
		rows = NzoneA;
		avail = availZoneA;
		avail_m = availZoneA_m;
		seats = zoneA;
		seats_m = zoneA_m;

	}else if(zone_id == 2){
		rows = NzoneB;
		avail = availZoneB;
		avail_m = availZoneB_m;
		seats = zoneB;
		seats_m = zoneB_m;

	}else if(zone_id == 3){
		rows = NzoneC;
		avail = availZoneC;
		avail_m = availZoneC_m;
		seats = zoneC;
		seats_m = zoneC_m;

	}

	//Checking for available seats
	pthread_mutex_lock(&avail_m);
	int full = (avail == 0);
	int noSeats = avail < random_tickets;
	pthread_mutex_unlock(&avail_m);

	if (!noSeats){
		pthread_mutex_lock(&seats_m);
		

		//Finding and storing available seats
		int empty_seats = 0;
		int first_seat = -1;
		noSeats = 1;

			
		for(int r = 0; r<rows;r++){
			for(int i=0;i<Nseat;i++){
				if(seats[r*Nseat + i] == 0){
					empty_seats++;
					if(empty_seats == 1) first_seat = r*Nseat + i;
				}else{
					empty_seats = 0;
					noSeats = 1;
				}
				if(empty_seats == random_tickets){
					noSeats = 0;
					for(int s = first_seat; s<first_seat+random_tickets; s++){
						seats_ids[s-first_seat] = s;
						seats[s] = thread_id;
					}
					//Update available seats
					pthread_mutex_lock(&avail_m);
					avail -= random_tickets;
					pthread_mutex_unlock(&avail_m);
					break;
				}
			}
			if(empty_seats == random_tickets){
				break;
			}else{
				empty_seats = 0;
				noSeats = 1;
			}		
		}

		pthread_mutex_unlock(&seats_m);
	}
	
	if(noSeats){
		//release phoner
		status = pthread_mutex_lock(&phoners_m);
		++availPhones;
		status = pthread_cond_signal(&cond);
		status = pthread_mutex_unlock(&phoners_m);
		
		//Error messages
		pthread_mutex_lock(&print_m);
		if(full) {
			printf("\n Client %d: The reservation was cancelled because the theater is full!\n", thread_id);
			pthread_mutex_lock(&full_theater_m);
			full_theater++;
			pthread_mutex_unlock(&full_theater_m);
		}
		else{
			printf("\n Client %d: The reservation was cancelled due to lack of available seats!\n", thread_id);
			pthread_mutex_lock(&seats_not_found_m);
			seats_not_found++;
			pthread_mutex_unlock(&seats_not_found_m);
		}
		pthread_mutex_unlock(&print_m);
		free(seats_ids);
		pthread_exit(NULL);
	}
	
}

void cardSuccess(int zone_id, int random_tickets, int thread_id, int* seats_ids){
	int cost;
	int* seats ;
	pthread_mutex_t seats_m, availSeats_m;
	unsigned int availSeats;
	if(zone_id == 1 ){
		cost = CzoneA;
		seats = zoneA;
		seats_m = zoneA_m;
		availSeats = availZoneA;
		availSeats_m = availZoneA_m;
	}else if(zone_id == 2 ){
		cost = CzoneB;
		seats = zoneB;
		seats_m = zoneB_m;
		availSeats = availZoneB;
		availSeats_m = availZoneB_m;
	}else if(zone_id == 3 ){
		cost = CzoneC;
		seats = zoneC;
		seats_m = zoneC_m;
		availSeats = availZoneC;
		availSeats_m = availZoneC_m;
	}
	//Card success
	int pay_success = s_random() % 101;
	if(pay_success < Pcardsuccess) { //Card succeded
		pthread_mutex_lock(&account_m);
		bank_account += random_tickets * cost;
		pthread_mutex_unlock(&account_m);
		
		pthread_mutex_lock(&print_m);
		printf("\n Client %d: The reservation was completed successfully! The id of the transaction is %d, your seats in row %d are ",thread_id, thread_id, (seats_ids[0]/Nseat)+1);
                for (int i=0; i<random_tickets; i++){
			printf("%d",seats_ids[i]);
			if (i!=random_tickets-1){
				printf(",");
			}
		}
 		printf(" in zone %d and the cost of the transaction is %d euros!\n", zone_id, random_tickets*cost);
		pthread_mutex_unlock(&print_m);
		pthread_mutex_lock(&perfect_m);
		perfect++;
		pthread_mutex_unlock(&perfect_m);
	}else{ //Card failed
		//Release seats
		pthread_mutex_lock(&seats_m);
		for(int j = 0;j < random_tickets;j++){
			seats[seats_ids[j]] = 0;	
		}
		pthread_mutex_unlock(&seats_m);

		pthread_mutex_lock(&availSeats_m);
		availSeats += random_tickets;
		pthread_mutex_unlock(&availSeats_m);
		
		pthread_mutex_lock(&print_m);
		printf("\n Client %d: The reservation was cancelled because the transaction with the credit card was not accepted\n", thread_id);
		pthread_mutex_unlock(&print_m);
		pthread_mutex_lock(&fail_m);
		fail++;
		pthread_mutex_unlock(&fail_m);
	}
}

//Thread method for Clients
void *client(void * tid){

	//Waiting time
	struct timespec start, stop_phoner, start_cashier, stop_cashier, stop_service;
	double time;
	
	//thread id
	int thread_id = *(int*)tid;
	
	//Waiting for available phoner
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

	if(clock_gettime(CLOCK_REALTIME, &stop_phoner) == -1){
		perror("clock gettime");
		destruction();
		exit(EXIT_FAILURE);
	}
	time = (stop_phoner.tv_sec - start.tv_sec) + (stop_phoner.tv_nsec - start.tv_nsec) / 1000000000L;

	
	
	//random phoner service time
	int random_time = s_random();
	random_time = (random_time % (Tseathigh - Tseatlow + 1) ) + Tseatlow;

	//random zone
	int random_zone = s_random();
	random_zone = random_zone % 101;
	
	//random number of tickets
	int random_tickets = s_random();
	random_tickets = (random_tickets % (Nseathigh - Nseatlow + 1) ) + Nseatlow;

	//Chosen seats
	int* seats_ids = (int*)malloc(sizeof(int) * random_tickets);
	if (seats_ids==NULL){
		printf("\nERROR: Malloc failed not enough memory!\n");
		pthread_exit(NULL);
	}
	
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
	
	int zone_id;
	if(random_zone < PzoneA){
		zone_id = 1;
	}else if(random_zone < PzoneA + PzoneB){
		zone_id = 2;
	}else if(random_zone < PzoneA + PzoneB + PzoneC){
		zone_id = 3;
	}
	availabilityZone(zone_id, random_tickets, thread_id, seats_ids);
	
	//Release phoner
	status = pthread_mutex_lock(&phoners_m);
	++availPhones;
	status = pthread_cond_signal(&cond);
	status = pthread_mutex_unlock(&phoners_m);

	//Waiting for available cashier
	status = pthread_mutex_lock(&cashiers_m);
	if(clock_gettime(CLOCK_REALTIME, &start_cashier) == -1){
		perror("clock gettime");
		destruction();
		exit(EXIT_FAILURE);
	}
	while(availCashiers == 0){
		status = pthread_cond_wait(&cond1, &cashiers_m);
	}

	availCashiers--;
	status = pthread_mutex_unlock(&cashiers_m);

	if(clock_gettime(CLOCK_REALTIME, &stop_cashier) == -1){
		perror("clock gettime");
		destruction();
		exit(EXIT_FAILURE);
	}
	time += (stop_cashier.tv_sec - start_cashier.tv_sec) + (stop_cashier.tv_nsec - start_cashier.tv_nsec) / 1000000000L;
	status = pthread_mutex_lock(&average_waiting_m);
	average_waiting_time = (client_counter * average_waiting_time + time) / (client_counter+1);
	status = pthread_mutex_unlock(&average_waiting_m);
	status = pthread_mutex_lock(&cashiers_m);
	availCashiers--;
	status = pthread_mutex_unlock(&cashiers_m);

	//random cashier service time
	random_time = s_random();
	random_time = (random_time % Tcashhigh) + Tcashlow;

	if(sleep(random_time) != 0){
		pthread_mutex_lock(&print_m);
		printf("\n Client %d: Thread failed to sleep\n", thread_id);
		pthread_mutex_unlock(&print_m);
		status = pthread_mutex_lock(&cashiers_m);
		++availCashiers;
		status = pthread_cond_signal(&cond1);
		status = pthread_mutex_unlock(&cashiers_m);
		
		pthread_exit(NULL);
	}


	cardSuccess(zone_id, random_tickets, thread_id, seats_ids);


	free(seats_ids);


	//Service time
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


	//Release cashier
	status = pthread_mutex_lock(&cashiers_m);
	++availCashiers;
	status = pthread_cond_signal(&cond1);
	status = pthread_mutex_unlock(&cashiers_m);


	//Thread exit
	pthread_exit(NULL);
}

int initialisation(pthread_mutex_t* lock){
	if(pthread_mutex_init(lock, NULL) != 0){
		printf("\n mutex init has failed\n");
		destruction();
		exit(-1);
	}
	return 0;
}

int main(int argc, char ** argv){
	availZoneA = NzoneA;
	availZoneB = NzoneB;
	availZoneC = NzoneC;
	availPhones = Ntel;
	availCashiers = Ncash;
	//mutexes initialisation
	initialisation(&account_m);
	initialisation(&zoneA_m);
	initialisation(&zoneB_m);
	initialisation(&zoneC_m);
	initialisation(&availZoneA_m);
	initialisation(&availZoneB_m);
	initialisation(&availZoneC_m);
	initialisation(&print_m);
	initialisation(&phoners_m);
	initialisation(&cashiers_m);
	initialisation(&average_waiting_m);
	initialisation(&average_service_m);
	printf("\n");

	//seats_plan = (int*)malloc(sizeof(int) * Nseat);
	zoneA = (int*)malloc(sizeof(int) * Nseat * NzoneA);
	zoneB = (int*)malloc(sizeof(int) * Nseat * NzoneB);
	zoneC = (int*)malloc(sizeof(int) * Nseat * NzoneC);
	
	if(zoneA == NULL || zoneB == NULL || zoneC == NULL){
		printf("\nERROR: Malloc failed not enough memory!\n");
		return -1;
	}
	
	Ncust = atoi(argv[1]);
	seedp = atoi(argv[2]);

	int i;
	for(i=0;i<Nseat*NzoneA;i++){
		zoneA[i] = 0;
	}
	for(i=0;i<Nseat*NzoneB;i++){
		zoneB[i] = 0;
	}
	for(i=0;i<Nseat*NzoneC;i++){
		zoneC[i] = 0;
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

	printf("Computing results");
	
	pthread_mutex_lock(&print_m);
	printf("\n");
	for(i=0;i<Nseat*NzoneA;i++){
		if(zoneA[i] != 0)
			printf(" Seat %d / Client %d / Zone A\n", i, zoneA[i]);
	}
	for(i=0;i<Nseat*NzoneB;i++){
		if(zoneB[i] != 0)
			printf(" Seat %d / Client %d / Zone B\n", i, zoneB[i]);
	}
	for(i=0;i<Nseat*NzoneC;i++){
		if(zoneC[i] != 0)
			printf(" Seat %d / Client %d / Zone C\n", i, zoneC[i]);
	}

	printf("\n\n Total earnings: %d\n", bank_account);
	printf("\n Average Waiting Time: %f seconds\n", average_waiting_time);
	printf("\n Average Service Time: %f seconds\n", average_service_time);
	printf("\n Completed Transactions: %.2f %%", perfect*100/Ncust);
	printf("\n Card Failure Transactions: %.2f %%", fail*100/Ncust);
	printf("\n Full Theater Transactions: %.2f %%", full_theater*100/Ncust);
	printf("\n Not Enough Seats Transactions: %.2f %%", seats_not_found*100/Ncust);
	pthread_mutex_unlock(&print_m);

	//pthread_mutex_destroy(&lock)
	
	

	
	printf("\n");
	destruction();
	exit(EXIT_SUCCESS);
	
}
