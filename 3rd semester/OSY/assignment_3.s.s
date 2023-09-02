#Author: Lydia Athanasiou
#Date: 23/12/2018
#Description: This program gets two 10-sized Arrays as inputs, converts them to Sparse Arrays and finds a third Sparse Array C = A + B

# The registers $a1-$a3 are also used as parameters in functions
# $a0 = to print messages, save inputs and save values in the Sparse Arrays
# $a1 = in readPin and createSparse the address of Arrays, in printSparse the address of Sparse Arrays, and in add the address of Sparse Array A
# $a2 = in createSparse the address of Sparse Arrays, in add the address of Sparse Array B, and in printSparse the size of the Sparse Array printed
# $a3 = the address of Sparse Array C (used in add function)

# $t0 = the size of the Arrays A and B (value = 10)
# $t1 = the choice of the user, and int a in function add
# $t2 = the options the user has except 0 (1,2,3,4,5,6,7,8),and int k in function createSparse, and int b in function add
# $t3 = int i for for loops, and int c in function add
# $t4 = the offset of the (Sparse)Arrays (used in readPin, createSparse and add)
# $t5 = value of current index (used in createSparse, add and printSparse)
# $t6 = in add the address of Sparse Array B, and in print the size of the Array printed
# $t7 = the size of Sparse Array A (used in add function)
# $t8 = the size of Sparse Array B (used in add function)
# $t9 = the next index of the current Array

# $s1 = the size of Sparse Array A
# $s2 = the size of Sparse Array B
# $s3 = the size of Sparse Array C

# $v0 = to temporary store system call codes(scc) that are loaded immediately,to store each scc's return values if they have one, to temporary store return values 
# $sp = the stack pointer (used in add function to pass the parameters mikosA and mikosB)
# $zero, to use value 0 right away

	.text
	.globl main

main :	 	lw $t0, size

			la $a0, message   		#print the user's options
			li $v0, 4
			syscall
			
			la $a0, question  		#make user tell his choice
			li $v0, 4
			syscall
			
			li $v0, 5		  	
			syscall
			add $t1, $v0, $zero		#save his choice in $t1
			
			beqz $t1, exit			#if choice = 0 exit
			
			li $t2, 1				#choice=1 ?
			beq $t1,$t2,readPinA	#if choice = 1 read array A
			
			li $t2, 2				#choice=2 ?
			beq $t1,$t2,readPinB	#if choice = 2 read array B
			
			li $t2, 3				#choice=3 ?
			beq $t1,$t2,createSpA	#if choice = 3 create Sparse Array A
			
			li $t2, 4				#choice=4 ?
			beq $t1,$t2,createSpB	#if choice = 4 create Sparse Array B
			
			li $t2, 5				#choice=5 ?
			beq $t1,$t2,createSpC	#if choice = 5 create Sparse Array C = A + B
			
			li $t2, 6				#choice=6 ?
			beq $t1,$t2,printSpA	#if choice = 6 dispay sparse array A
			
			li $t2, 7				#choice=7 ?
			beq $t1,$t2,printSpB	#if choice = 7 display sparse array B
			
			li $t2, 8				#choice=8 ?
			beq $t1,$t2,printSpC	#if choice = 8 display sparse array C
			
			
# ------------------------------ READ ARRAY  ----------------------------------

readPinA:	la $a0, readpinA		#print "Reading Array A"
			li $v0, 4 
			syscall
			
			# The parameter of the function (int[] pin)
			la $a1, A				#address of array A
			
			jal readPin

readPinB:	la $a0, readpinB		#print "Reading Array B"
			li $v0, 4
			syscall
			
			#The parameter of the function (int[] pin)
			la $a1, B				#address of array B
			
			jal readPin
		
# void readPin(int[] pin)
#	for(int i=0;i<pin.length;i++)
#		pin[i] = readInt("Position "+i+" :");
readPin:	li $t3, 0				#$t3 = i (initialized as i=0 for for loop)
			move $t8, $a1
	for1:	bge $t3, $t0, main		#if i>=pin.length go to main
			la $a0, position1   	#print "Position "
			li $v0, 4
			syscall
			
			li $v0, 1				#print i
			add $a0, $t3, $zero
			syscall
			
			la $a0, position2   	#print " :"
			li $v0, 4
			syscall
			
			li $v0, 5		  		#read pin[i]
			syscall
			
			add $a0, $v0, $zero		#save input to $a0
			mul $t4, $t3, 4			#move to the next index of pin
			add $t9, $t8, $t4		
			sw $a0, 0($t9)			#save pin[i] = $a0
			
			addi $t3, $t3, 1		#i++
			
			j for1

			
# ------------------------------ CREATE SPARSE --------------------------------

createSpA:  la $a0, createSPA		#print "Creating Sparse Array A"
			li $v0, 4
			syscall
			
			# The parameters of the function (int[] pin, int[] Sparse)
			la $a2, SpA				#$a2 = address of Sparse Array A
			la $a1, A				#$a1 = address of Array A
		
			jal createSparse
			
			add $s1, $v0, $zero		#save counterOfOnes (the length of Sparse Array A)
			
			div $a0, $s1, 2			#print how many values the Sparse Array A has
			li $v0, 1
			syscall
			
			la $a0, values			#print " values"
			li $v0, 4
			syscall
			
			jal main

createSpB:	la $a0, createSPB		#print "Creating Sparse Array B"
			li $v0, 4
			syscall
			
			# The parameters of the function (int[] pin, int[] Sparse)
			la $a2, SpB				#$a2 = address of Sparse Array B
			la $a1, B				#$a1 = address of Array B
			
			jal createSparse
			
			add $s2, $v0, $zero		#the length of Sparse Array B
			
			div $a0, $s2, 2			#print how many values the Sparse Array B has
			li $v0, 1
			syscall
			
			la $a0, values			#print " values"
			li $v0, 4
			syscall
			
			jal main

			
# int createSparse(int[] pin, int[] Sparse){
#	int i, k=0;
#	for (i=0; i<pin.length; i++)
#		if (pin [i] != 0){
#			Sparse [k++] = i;
#			Sparse [k++] = pin [i];
#		}
#	return k;
# }
createSparse:
			li $t3, 0				#$t3 = i (initialized as i=0 for for loop)
			li $t2, 0				#$t2 = k  (size of Sparse)
	for3:	beq $t3, $t0, end2
			lw $t5, 0($a1)			#$t5 = pin[i]
			bnez $t5, notZero		#if input is not zero go to notZero 
			j continue				#else go to continue
			
	notZero:mul $t4, $t2, 4			#move to the next index of Sparse
			add $t9, $a2, $t4		
			move $a0, $t3
			sw $a0, 0($t9)			#save Sparse[i] = i 
			addi $t2, $t2, 1		#k++
			
			add $t4, $t4, 4			#move to the next index of Sparse
			add $t9, $a2, $t4		
			move $a0, $t5 
			sw $a0, 0($t9)			#save Sparse[i+1] = pin[i]
			addi $t2, $t2, 1		#k++
			
	continue:
			addi $t3, $t3, 1		#i++
			addi $a1, $a1, 4		#move to the next index of pin
			j for3
			
	end2:	move $v0, $t2
			jr $ra					#return k ($v0, size of the Sparse Array)
			
			

createSpC:	la $a0, createSPC		#print "Creating Sparse Array C = A + B"
			li $v0, 4
			syscall
			
			# The parameters of the function (int [ ] SparseA, int mikosA, int [ ] SparseB, int mikosB, int [ ] SparseC)
			la $a1, SpA				#$a1 = address of Sparse Array A
			la $a2, SpB				#$a2 = address of Sparse Array B
			la $a3, SpC				#$a3 = address of Sparse Array C
			add $t7, $s1, $zero		#$t7 = size of Sparse Array A
			add $t8, $s2, $zero		#$t8 = size of Sparse Array B
			addi $sp, $sp, -4
			sw $t7, 0($sp)
			addi $sp, $sp, -8
			sw $t8, 0($sp)
			
			jal add__
			
			lw $t8, 0($sp)
			addi $sp, $sp, 8
			lw $t7, 0($sp)
			addi $sp, $sp, 4
			
			add $s3, $v0, $zero		#$s3 = the length of Sparse Array C
			
			div $a0, $s3, 2			#print how many values the Sparse Array C has
			li $v0, 1
			syscall
			
			la $a0, values			#print "values"
			li $v0, 4
			syscall
			
			jal main

#int add (int [ ] SparseA, int mikosA, int [ ] SparseB, int mikosB, int [ ] SparseC) { 
#	int a,b,c;
#	for (a=0, b=0, c=0; a<mikosA && b<mikosB; ) 
#		if (SparseA[a] < SparseB [b]) { SparseC [c++] = SparseA[a++]; SparseC [c++] = SparseA[a++]; } 
#		else if (SparseA[a] > SparseB [b]) { SparseC [c++] = SparseB[b++]; SparseC [c++] = SparseB[b++]; } 
#		else { SparseC [c++] = SparseA[a++]; b++; SparseC [c++] = SparseA[a++] + SparseB[b++]; }
#	for (;a<mikosA;) { SparseC [c++] = SparseA[a++]; SparseC [c++] = SparseA[a++]; } 
#	for (;b<mikosB;) { SparseC [c++] = SparseB[b++]; SparseC [c++] = SparseB[b++]; }
#	for return c; 
#}
add__:		li $t1, 0				# $t1 = a
			li $t2, 0				# $t2 = b 
			li $t3, 0				# $t3 = c
	for4:	bge	$t1, $t7, for5		# if a>=mikosA go to for5
			bge $t2, $t8, for5		# if b>=mikosB go to for5
			
			#both conditions are true (1st for loop)
			lw $t5, 0($a1)			#$t5 = SpA[i]
			lw $t6, 0($a2)			#$t6 = SpB[i]
			
			#if block
			bge $t5, $t6, elif		#if SpA[a]>=SpB[b] go to elif
			mul $t4, $t3, 4			#move to the next index of SpC
			add $t9, $a3, $t4		
			move $a0, $t5
			sw $a0, 0($t9)			#save SpC[c++] = SpA[a++]
			
			addi $t3, $t3, 1		#c++
			addi $t1, $t1, 1		#a++
			
			addi $a1, $a1, 4		#move to the next index of Sparse Array A
			lw $t5, 0($a1)			#$t5 = SpA[i]
			
			add $t4, $t4, 4			#move to the next index of Sparse Array C
			add $t9, $a3, $t4		
			move $a0, $t5 
			sw $a0, 0($t9)			#save #SpC[c++] = SpA[a++]
			
			addi $t3, $t3, 1		#c++
			addi $t1, $t1, 1		#a++
			addi $a1, $a1, 4		#move to the next index of Sparse Array A
			
			j for4
			
		elif:
			#else if block
			ble $t5, $t6, else		#if SpA[a]<=SpB[b] go to else
			mul $t4, $t3, 4			#move to the next index of SpC
			add $t9, $a3, $t4		
			move $a0, $t6
			sw $a0, 0($t9)			#save SpC[c++] = SpB[b++]
			
			addi $t3, $t3, 1		#c++
			addi $t2, $t2, 1		#b++
			
			addi $a2, $a2, 4		#move to the next index of Sparse Array B
			lw $t6, 0($a2) 			#$t6 = SpB[i]
			
			add $t4, $t4, 4			#move to the next index of Sparse Array C
			add $t9, $a3, $t4		
			move $a0, $t6 
			sw $a0, 0($t9)			#save SpC[c++] = SpB[b++]
			
			addi $t3, $t3, 1		#c++
			addi $t2, $t2, 1		#a++
			addi $a2, $a2, 4		#move to the next index of Sparse Array B
			
			j for4
			
		else:
			#else block
			mul $t4, $t3, 4			#move to the next index of SpC
			add $t9, $a3, $t4		
			move $a0, $t5
			sw $a0, 0($t9)			#save SpC[c++] = SpA[a++]
			
			addi $t3, $t3, 1		#c++
			addi $t1, $t1, 1		#a++
			
			addi $t2, $t2, 1		#b++
			
			addi $a1, $a1, 4		#move to the next index of Sparse Array A
			addi $a2, $a2, 4		#move to the next index of Sparse Array B	
			
			lw $t5, 0($a1)			#$t5 = SpA[a]
			lw $t6, 0($a2)			#$t6 = SpB[b]
			
			add $t4, $t5, $t6		#$t4 = SpA[a++] + SpB[b++]
			
			addi $t1, $t1, 1		#a++
			addi $t2, $t2, 1		#b++
			
			move $a0, $t4						
			mul $t4, $t3, 4			#move to the next index of SpC
			add $t9, $a3, $t4
			sw $a0, 0($t9)			#save SpC[c++] = SpA[a] + SpB[b]
			
			addi $t3, $t3, 1		#c++
			addi $a1, $a1, 4		#move to the next index of Sparse Array A	
			addi $a2, $a2, 4		#move to the next index of Sparse Array B
			
			j for4
			
	#Getting out of the 1st for loop (2nd for loop)
	for5:	bge $t1, $t7, for6		#if a>=mikosA go to for6
	
			mul $t4, $t3, 4			#move to the next index of pin
			add $t9, $a3, $t4		
			move $a0, $t5
			sw $a0, 0($t9)			#save SpC[c++] = SpA[a++]
			
			addi $t3, $t3, 1		#c++
			addi $t1, $t1, 1		#a++
			
			addi $a1, $a1, 4		#move to the next index of SparseA
			lw $t5, 0($a1)			#$t5 = SpA[i]
			
			add $t4, $t4, 4			#move to the next index of Sparse Array C
			add $t9, $a3, $t4		
			move $a0, $t5 
			sw $a0, 0($t9)			#save SpC[c++] = SpA[a++]
			
			addi $t3, $t3, 1		#c++
			addi $t1, $t1, 1		#a++
			
			j for5
			
	#Getting out of the 2nd for loop (3rd for loop)
	for6:	bge $t2, $t8, return 	#if b>=mikosB go to return
	
			mul $t4, $t3, 4			#move to the next index of pin
			add $t9, $a3, $t4		
			move $a0, $t6
			sw $a0, 0($t9)			#save SpC[c++] = SpB[b++]
			
			addi $t3, $t3, 1		#c++
			addi $t2, $t2, 1		#b++
			
			addi $a2, $a2, 4		#move to the next index of SparseB
			lw $t6, 0($a2)			#$t6 = SpB[i]
			
			add $t4, $t4, 4			#move to the next index of Sparse Array C
			add $t9, $a3, $t4		
			move $a0, $t6 
			sw $a0, 0($t9)			#save SpC[c++] = SpB[b++]
			
			addi $t3, $t3, 1		#c++
			addi $t2, $t2, 1		#b++
			
			j for6
	
	return: move $v0, $t3
			jr $ra					#return c ($v0, size of Sparse Array C)
			
			
# ------------------------------ PRINT SPARSE ----------------------------------

printSpA:	la $a0, displaySpA		#print "Displaying Sparse Array A"
			li $v0, 4
			syscall
			
			# The parameters of the function (int [] Sparse, int mikos)
			la $a1, SpA
			move $a2, $s1
			
			jal printSparse

printSpB:	la $a0, displaySpB		#print "Displaying Sparse Array B"
			li $v0, 4
			syscall
			
			# The parameters of the function (int [] Sparse, int mikos)
			la $a1, SpB
			move $a2, $s2
			
			jal printSparse
	
printSpC:	la $a0, displaySpC		#print "Displaying Sparse Array C"
			li $v0, 4
			syscall
			
			# The parameters of the function (int [] Sparse, int mikos)
			la $a1, SpC
			move $a2, $s3
			
			jal printSparse

			
# void printSparse(int[] Sparse, int mikos){
#	for (int i=0, i<mikos;)
#		println("Position: "+Sparse[i++]+" Value: "+Sparse[i++]);
printSparse:li $t3, 0				#$t3 = i (initialized as i=0 for for loop)
			move $t6, $a2
	for2:	beq $t3, $t6, main
			lw $t5, 0($a1)			#$t5 = Sparse[i]
			
			la $a0, positionsp1 	#print "Position: "
			li $v0, 4
			syscall
			
			li $v0, 1				#print Sparse[i++]
			add $a0, $t5, $zero
			syscall
			
			addi $t3, $t3, 1		#i++
			
			addi $a1, $a1, 4		#move to next index of the Sparse Array
			lw $t5, 0($a1)			#$t5 = Sparse[i]
			
			la $a0, positionsp2		#print " Value "
			li $v0, 4
			syscall
			
			li $v0, 1				#print Sparse[i++]
			add $a0, $t5, $zero
			syscall
			
			addi $t3, $t3, 1		#i++
			
			addi $a1, $a1, 4		#move to next index of the Sparse Array
			j for2
		

exit:		li $v0, 10
			syscall



	
	.data
#Array A 10(integers) x 4 (sizeof(integer))
A :			.space 40

#Array B 
B : 		.space 40

#Sparse Array A
SpA :		.space 80

#Sparse Array B
SpB :		.space 80

#Sparse Array COMPLETED
SpC :		.space 80

#size of the arrays
size:		.word 10

#Choices for the user to choose
message:	.asciiz "\n\n\n------------------------------ \n1. Read Array A\n2. Read Array B\n------------------------------ \n3. Create Sparse Array A\n4. Create Sparse Array B\n5. Create Sparse Array C = A + B\n------------------------------ \n6. Display Sparse Array A\n7. Display Sparse Array B\n8. Display Sparse Array C\n------------------------------ \n0. Exit\n------------------------------\n"

#What the user chooses?
question:	.asciiz "Choice? "

# for creating and printing the sparse arrays
position1:	.asciiz "Position "
position2:	.asciiz " :"
positionsp1:.asciiz "\nPosition: "
positionsp2:.asciiz " Value: "
values:		.asciiz " values"

# for each procedure
readpinA:	.asciiz "\nReading Array A\n"
readpinB:	.asciiz "\nReading Array B\n"
createSPA:	.asciiz "\nCreating Sparse Array A\n"
createSPB:	.asciiz "\nCreating Sparse Array B\n"
createSPC:	.asciiz "\nCreating Sparse Array C = A + B\n"
displaySpA:	.asciiz "\nDisplaying Sparse Array A"
displaySpB:	.asciiz "\nDisplaying Sparse Array B"
displaySpC:	.asciiz "\nDisplaying Sparse Array C"
