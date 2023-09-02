# Author: Lydia Athanasiou
# Date: 5/11/2018
# Description: This program calculates the Greatest Common Divisor of two numbers given as an input from the user
	# $t0, to temporary store the integer a that the user gives
	# $t1, to temporary store the integer b that the user gives
	# $t2, to temporary store the integer y
	# $t3, to temporary store the integer s that the user gives as an answer 
	# $v0, to temporary store system call codes(scc) that are loaded immediately, and to store each scc's return values if they have one 
	# $a0, to store arguments 
	# $zero, to use value 0 right away
	
	.text
	.globl main
main: 
	# int a,b,y,s;
	lw $t0, A
	lw $t1, B
	lw $t2, Y
	lw $t3, S
	
	#print("Dwse enan akeraio: ");
	#a = Diabase_akeraio();		   
	li $v0, 4			
	la $a0, GIVE_INT	
	syscall
	li $v0, 5			
	syscall
	add $t0,$v0,$zero  # to store a in $t0
	
	
	#print("Dwse enan akeraio: "); 
	#b = Diabase_akeraio();		   
	li $v0, 4			
	la $a0, GIVE_INT
	syscall
	li $v0, 5			
	syscall
	add $t1,$v0,$zero   # to store b in $t1


	#print("Poios einai o MKD twn "+a+" kai "+b+";");
	li $v0, 4 			#print "Poios einai o MKD twn "
	la $a0, WHO_GCD1 	
	syscall
	li $v0, 1			#print a
	add $a0,$t0,$zero   
	syscall
	li $v0, 4 			#print " kai "
	la $a0, WHO_GCD2 
	syscall
	li $v0, 1			#print b
	add $a0,$t1,$zero    
	syscall
	li $v0, 4 			#print ";"
	la $a0, WHO_GCD3 	
	syscall

	
	#y = a%b;
	#while(y!=0){
	#	a=b;
	#	b=y;
	#	y=a%b;
	#}
	rem $t2,$t0,$t1
	jal WHILE_Y
	WHILE_Y:		
		beq $t2,$zero,EXIT_Y
		add $t0,$t1,$zero
		add $t1,$t2,$zero
		rem $t2,$t0,$t1
		jal WHILE_Y
	EXIT_Y:
	#s=Diabase_Akeraio();
		li $v0, 5			
		syscall
		add $t3,$v0,$zero    
		jal WHILE_S
		
		
	#while(s!=b){
	#	print("Lathos!Dokimaste 3ana");
	#	print("Poios einai o MKD;")
	#	s = Diabase_Akeraio();
	#}
	WHILE_S:
		beq $t3,$t1,EXIT_S
		li $v0, 4			
		la $a0, ERROR	
		syscall
		li $v0, 4			
		la $a0, GIVE_AGAIN	
		syscall
		li $v0, 5
		syscall		
		add $t3,$v0,$zero
		jal WHILE_S
	EXIT_S :
	#System.out.println("Sugxarhthria!");
		li $v0, 4			
		la $a0, CONGRATS	
		syscall
				
	#exit
		li $v0,10
		syscall
			
			
	.data
	GIVE_INT:	.asciiz "Give an integer: "
	WHO_GCD1:	.asciiz "Who is the GCD of "
	WHO_GCD2:	.asciiz " and "
	WHO_GCD3:	.asciiz "? "
	ERROR: 		.asciiz "Wrong! Try again.\n"
	GIVE_AGAIN:	.asciiz "Who is the GCD? "
	CONGRATS:	.asciiz "Congratulations!\n\n"
	# Used to declare variables a,b,y,s
	Y:			.word 0
	S:			.word 0
	A:			.word 0
	B:			.word 0



	
			