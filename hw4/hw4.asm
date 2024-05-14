############## Vincent Ke	 ##############
############## 113778667 	 #################
############## YUKE		 ################

############################ DO NOT CREATE A .data SECTION ############################
############################ DO NOT CREATE A .data SECTION ############################
############################ DO NOT CREATE A .data SECTION ############################
.text:
.globl create_person
create_person:
# Node * create_person(Network* ntwrk)
# Takes base address of Network as an argument and returns the address of a node in the network or -1 in register $v0.

	lw $t0, 0($a0) # Reads the word containing the max number of nodes.
	lw $t2, 16($a0) # Reads the word containing current number of nodes.
	lw $t3, 8($a0) # Reads the word containing the length of names.
	
	bgeu $t2, $t0, create_person_failed
						
	multu $t2, $t3 # current num of nodes * name length
	mflo $t1 # t1 is the number of increment from base address of Node[]
	
	addiu $v0, $t1, 36 # New address for node.
	addu $v0, $v0, $a0
	move $t1, $v0
	
	# Used to set everything to zero.
	create_person_loop:
		beqz $t3, create_person_loop_end
		sb $0, 0($t1)
		addi $t1, $t1, 1
		addi $t3, $t3, -1
		j create_person_loop
	create_person_loop_end:
		# Sets current number of nodes to ++.
		lw $t3, 16($a0)
		addi $t3, $t3, 1
		sw $t3, 16($a0)
		jr $ra
	
	create_person_failed:
		li $v0, -1
		jr $ra


.globl is_person_exists
is_person_exists:
# int is_person_exists(Network* ntwrk, Node* person)
# takes base address of Network as 1st arguemnt
# address of a person as second argument

	lw $t1, 16($a0) # Reads current number of nodes
	lw $t2, 8($a0) # Reads length of every name.
	
	beqz $t1, person_not_exist # If there is no node that has been added.
	
	multu $t1, $t2
	mflo $t0
	addiu $t0, $t0, 36
	addu $t0, $t0, $a0 # Computes the memory address of last node.
	
	addiu $t1, $a0, 36 # Computes the memory address of the first node.
	
	# Checks if memory address is even in range.
	blt $a1, $t1, person_not_exist
	bge $a1, $t0, person_not_exist
	
	# Checks if memory address is divisible by length.
	addiu $a1, $a1, -36
	sub $a1, $a1, $a0
	div $a1, $t2
	mfhi $t0
	beqz $t0, person_exist
	
	person_not_exist:
		li $v0, 0
		jr $ra
	person_exist:
		li $v0, 1
		jr $ra


.globl is_person_name_exists
is_person_name_exists:
# int is_person_name_exists(Network* ntwrk, char* name)
# $a0 is base address of network
# $a1 is base address of null terminating string

	lw $t1, 16($a0) # Current number of nodes
	lw $t2, 8($a0) # Length of every name.
		
	move $t3, $a1
	li $t4, 1
	string_length_loop:
		lb $t0, 0($t3)
		beqz $t0, string_length_loop_done
		
		addi $t3, $t3, 1 # Increment memory
		addi $t4, $t4, 1 # Increment counter
		
		j string_length_loop
	string_length_loop_done:
	# $t4 now is the length of string that is passed in.
	
	bgt $t4, $t2, person_name_not_exists # If the passed in string is bigger than largest
	
	multu $t1, $t2
	mflo $t7
	
	addiu $a0, $a0, 36 # Increment base memory to the start of the node[]
	addu $t7, $t7, $a0 # memory address of last node.
	
	name_exists_loop_start:
		move $t3, $a1 # Copies memory address of string.
		move $t4, $a0 # Copies memory address of node
	name_exists_loop:
		lb $t5, 0($t4) # Loads both string and node char.
		lb $t6, 0($t3)
		
		bne $t5, $t6, name_exists_loop_not_exist # If these two are different.
		
		beqz $t5, person_name_exists # Since both are them are equal, then they both must be null terminators.
		
		addiu $t4, $t4, 1 # Increment both address.
		addiu $t3, $t3, 1
	
		j name_exists_loop
	name_exists_loop_not_exist:
		addu $a0, $a0, $t2
		bgt $a0, $t7, person_name_not_exists
		j name_exists_loop_start
	
	person_name_not_exists:
		li $v0, 0
		jr $ra
	person_name_exists:
		move $v1, $a0
		li $v0, 1
		jr $ra


.globl add_person_property
add_person_property:
# int add_person_property(Network* ntwrk, Node* person, char* prop_name, char* prop_val)
# $a0 = base address of network
# $a1 = address of person node.
# $a2 = name of property to set
# $a3 = value to set

# Conditions
# 1. $a2 is the same as asciiz string "NAME"
# 2. person exists
# 3. property value length is less than network size of node
# 4. property value is unique.

	addi $sp, $sp, -12 # Saves arguments.
	sw $a0, 4($sp)
	sw $a1, 8($sp)
	sw $ra, 0($sp)

	# Condition 1:
	move $t0, $a0
	move $t1, $a2 # Start of prop_name
	#addiu $t0, $t0, 24 # Start of "NAME" in network
	# Assuming both these are 5 bytes.
	lb $t2, 0($t1)
	lb $t3, 24($t0)
	bne $t2, $t3, add_person_property_failed_one
	lb $t2, 1($t1)
	lb $t3, 25($t0)
	bne $t2, $t3, add_person_property_failed_one
	lb $t2, 2($t1)
	lb $t3, 26($t0)
	bne $t2, $t3, add_person_property_failed_one
	lb $t2, 3($t1)
	lb $t3, 27($t0)
	bne $t2, $t3, add_person_property_failed_one
	lb $t2, 4($t1)
	lb $t3, 28($t0)
	bne $t2, $t3, add_person_property_failed_one
	j condition_two

	add_person_property_failed_one:
		li $v0, 0
		lw $ra, 0($sp)
		addi $sp, $sp, 12
		jr $ra

	# Condition 2:
	condition_two:
	lw $a0, 4($sp)
	lw $a1, 8($sp)
	jal is_person_exists
	beqz $v0, add_person_property_failed_two
	
	# Condition 4:
	lw $a0, 4($sp) # Loads base addres
	move $a1, $a3  # Loads string address.
	jal is_person_name_exists
	beqz $v0, pass_condition_four
	j add_person_property_failed_four
	
	pass_condition_four:
	
	# Condition 3:
	move $t0, $a3
	li $t2, 1
	add_person_property_string_length_loop:
		lb $t1, 0($t0)
		beqz $t1, add_person_property_string_length_loop_done
		addi $t0, $t0, 1
		addi $t2, $t2, 1
		j add_person_property_string_length_loop
	add_person_property_string_length_loop_done:
	# $t2 should be the length of string, including null terminator.
	lw $t0, 4($sp) # Loads base memory of network.
	lw $t1, 8($t0) # Loads the size of node
	
	bgt $t2, $t1, add_person_property_failed_three
	
	# Passed all conditions.
	lw $t0, 8($sp) # Loads the persons address
	add_person_property_loop:
		lb $t4, 0($a3)
		
		beqz $t4, add_person_property_done
		
		sb $t4, 0($t0)
		addi $t0, $t0, 1
		addi $a3, $a3, 1
		j add_person_property_loop
	
	add_person_property_done:
		li $v0, 1
		lw $ra, 0($sp)
		addi $sp, $sp, 12
		jr $ra
		
	add_person_property_failed_two:
	li $v0, -1
	j add_person_property_failed
	add_person_property_failed_three:
	li $v0, -2
	j add_person_property_failed
	add_person_property_failed_four:
	li $v0, -3
	j add_person_property_failed
	add_person_property_failed:
		lw $ra, 0($sp)
		addi $sp, $sp, 12
		jr $ra


.globl get_person
get_person:
# Node* get_person(Network* network, char* name)
# $a0 = base address of the network
# $a1 = address of asciiz name.

	addi $sp, $sp, -4
	sw $ra, 0($sp)
	jal is_person_name_exists
	beqz $v0, get_person_end
	move $v0, $v1
	get_person_end:
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		jr $ra


.globl is_relation_exists
is_relation_exists:
# int is_relation_exists(Network* ntwrk, Node* person1, Node* person2)
# size_of_edge is always 12 according to brief description number 4.
# first word and second word is address of node, third word is relationship.

# $a0 = base address of network
# $a1 = address of node1
# $a2 = address of node2

	lw $t0, 20($a0) # Number of edges currently the network has
	lw $t7, 0($a0)
	lw $t6, 8($a0)
	mult $t6, $t7
	mflo $t1
	addi $t1, $t1, 36 # base address of edges[]
	add $t1, $t1, $a0
	
	move $t2, $t0 # Duplicate num of edges for counter.
	is_relation_loop:
		beqz $t2, is_relation_nothing_found # If all edges are gone through.
	
		lw $t3, 0($t1) # Loads the word
		beq $t3, $a1, is_relation_first_equal # Is first equal
		beq $t3, $a2, is_relation_second_equal # Is second equal
		j is_relation_loop_tail
		
		# If the other is equal, then found, else loop.
		is_relation_first_equal:
			lw $t3, 4($t1)
			beq $t3, $a2, is_relation_found
			j is_relation_loop_tail
		is_relation_second_equal:
			lw $t3, 4($t1)
			beq $t3, $a1, is_relation_found
			j is_relation_loop_tail
			
		is_relation_loop_tail:
		addi $t1, $t1, 12
		addi $t2, $t2, -1
		j is_relation_loop
	
	is_relation_found:
		li $v0, 1
		jr $ra
			
	is_relation_nothing_found:
		li $v0, 0
		jr $ra


.globl add_relation
add_relation:
# int add_relation(Network* ntwrk, Node* person1, Node* person2)
# Constraints:
# 1. Both person1 and person2 need to exists
# 2. Edges is not maxed out
# 3. Relation doesn't exists yet.
# 4. This new relation isn't connecting itself.

	# Saves stuff.
	addi $sp, $sp, -16
	sw $ra, 0($sp)
	sw $a0, 4($sp)
	sw $a1, 8($sp)
	sw $a2, 12($sp)
	
	# Constraint 1
	jal is_person_exists
	beqz $v0, constraint_one_fail
	lw $a0, 4($sp)
	lw $a1, 12($sp)
	jal is_person_exists
	beqz $v0, constraint_one_fail
	j constraint_two
	
	constraint_one_fail:
		li $v0, 0
		j add_relation_end
	
	# Constraint 2
	constraint_two:
	lw $a0, 4($sp)
	lw $t0, 4($a0) # Number of total edges.
	lw $t1, 20($a0) # Current number of edges.
	bgeu $t1, $t0, constraint_two_fail
	j constraint_three
	
	constraint_two_fail:
		li $v0, -1
		j add_relation_end
		
	# Constraint 3
	constraint_three:
	# $a0 is already loaded
	lw $a1, 8($sp)
	lw $a2, 12($sp)
	jal is_relation_exists
	beqz $v0, constraint_four
	
	li $v0, -2
	j add_relation_end
	
	# Constraint 4
	constraint_four:
	lw $a1, 8($sp)
	lw $a2, 12($sp)
	beq $a1, $a2, constraint_four_fail
	j add_relation_for_real
	
	constraint_four_fail:
		li $v0, -3
		j add_relation_end
	
	add_relation_for_real:
		lw $a0, 4($sp)
		lw $t0, 0($a0) # total nodes
		lw $t1, 8($a0) # node size
		mult $t0, $t1
		mflo $t0 # node * node size
		addi $t0, $t0, 36 # offset by 36 from base address.
		add $t0, $t0, $a0 # start of edges memory address.
		
		lw $t1, 20($a0) # Current number of edges
		li $t2, 12 # size of edges is assumed to be 12
		mult $t1, $t2
		mflo $t1 # edges * edges size
		
		add $t0, $t0, $t1 # memory address of new edge.
		
		lw $t1, 8($sp) # person1
		sw $t1, 0($t0) # saves person1
		lw $t1, 12($sp) # person2
		sw $t1, 4($t0) # saves person2
		
		lw $t0, 20($a0) # Current number of edges
		addiu $t0, $t0, 1
		sw $t0, 20($a0) # Updates current number of edges.
		
		li $v0, 1 # Success code.
	
	add_relation_end:
		lw $ra, 0($sp)
		addi $sp, $sp, 16
		jr $ra
		

.globl add_relation_property
add_relation_property:
# int add_relation_property(Network* ntwrk, Node* person1, Node* person2, char* prop_name)
	# Condition 2.
	lb $t2, 0($a3)
	lb $t3, 29($a0)
	bne $t2, $t3, add_relation_prop_name_check_failed
	lb $t2, 1($a3)
	lb $t3, 30($a0)
	bne $t2, $t3, add_relation_prop_name_check_failed
	lb $t2, 2($a3)
	lb $t3, 31($a0)
	bne $t2, $t3, add_relation_prop_name_check_failed
	lb $t2, 3($a3)
	lb $t3, 32($a0)
	bne $t2, $t3, add_relation_prop_name_check_failed
	lb $t2, 4($a3)
	lb $t3, 33($a0)
	bne $t2, $t3, add_relation_prop_name_check_failed
	
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	# Checks condition two at the same time as it returns the edge address.
	jal add_relation_helper
	beqz $v0, add_relation_condition_one_failed
	li $t0, 1
	sw $t0, 8($v1) # Set that edge to 1.
	li $v0, 1
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	jr $ra
	
	add_relation_condition_one_failed:
		li $v0, 0
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		jr $ra	
	
	add_relation_prop_name_check_failed:
		li $v0, -1
		jr $ra
	
add_relation_helper:
# Copy of is_relation_exists, but it now also returns an address
# int is_relation_exists(Network* ntwrk, Node* person1, Node* person2)
# size_of_edge is always 12 according to brief description number 4.
# first word and second word is address of node, third word is relationship.

# $a0 = base address of network
# $a1 = address of node1
# $a2 = address of node2

	lw $t0, 20($a0) # Number of edges currently the network has
	lw $t7, 0($a0)
	lw $t6, 8($a0)
	mult $t6, $t7
	mflo $t1
	addi $t1, $t1, 36 # base address of edges[]
	add $t1, $t1, $a0
	
	move $t2, $t0 # Duplicate num of edges for counter.
	add_relation_helper_loop:
		beqz $t2, add_relation_helper_nothing_found # If all edges are gone through.
	
		lw $t3, 0($t1) # Loads the word
		beq $t3, $a1, add_relation_helper_first_equal # Is first equal
		beq $t3, $a2, add_relation_helper_second_equal # Is second equal
		j add_relation_helper_loop_tail
		
		# If the other is equal, then found, else loop.
		add_relation_helper_first_equal:
			lw $t3, 4($t1)
			beq $t3, $a2, add_relation_helper_found
			j add_relation_helper_loop_tail
		add_relation_helper_second_equal:
			lw $t3, 4($t1)
			beq $t3, $a1, add_relation_helper_found
			j add_relation_helper_loop_tail
			
		add_relation_helper_loop_tail:
		addi $t1, $t1, 12
		addi $t2, $t2, -1
		j add_relation_helper_loop
	
	add_relation_helper_found:
		move $v1, $t1
		li $v0, 1
		jr $ra
			
	add_relation_helper_nothing_found:
		li $v0, 0
		jr $ra


.globl is_friend_of_friend
is_friend_of_friend:
# int is_friend_of_friend(Network* ntwrk, char* name1, char* name2)

	# Saves a bunch of things.
	addi $sp, $sp, -24
	sw $ra, 0($sp)
	sw $a0, 4($sp)
	sw $a1, 8($sp)
	sw $a2, 12($sp)
	
	# Checks if name one exists.
	jal is_person_name_exists
	beqz $v0, this_dude_dont_exist
	sw $v1, 16($sp) # Address of first name.
	
	# Checks if name two exists.
	lw $a0, 4($sp)
	lw $a1, 12($sp)
	jal is_person_name_exists
	beqz $v0, this_dude_dont_exist
	sw $v1, 20($sp) # Address of second name.
	
	lw $t0, 4($sp) # Loads base address.
	lw $t1, 0($t0) # nodes
	lw $t2, 8($t0) # size of nodes
	multu $t1, $t2
	mflo $t1 # Memory usage of nodes[]
	addiu $t0, $t0, 36 # Moves address to start of nodes
	addu $t0, $t0, $t1 # Moves address to start of edges.
	lw $t2, 4($sp) # Load base address again.
	lw $t1, 20($t2) # Number of edges currently in the network.
	
	# Useful stuff: $t0 memory address at the start of edges[]
	# 		$t1 number of edges in the network.
	
	# Implementation idea:
	# For every single edge, check if it contains person1
	# If it does, get the other person (person3) and use is_relation_exists on them
	
	
	friend_loop:
		beqz $t1, they_aint_connected
		
		lw $t2, 16($sp) # First name address.
		lw $t3, 0($t0) # First name in edge
		beq $t2, $t3, friend_first_equal		
		lw $t3, 4($t0) # Second name in edge
		beq $t2, $t3, friend_second_equal
		j friend_loop_tail
		
		friend_first_equal:
			# Checks if they were friends.
			lw $t2, 8($t0)
			li $t3, 1
			bne $t2, $t3, friend_loop_tail
		
			lw $a1, 4($t0) # Second name in edge
			lw $a0, 4($sp) # Base address
			lw $a2, 20($sp) # person2 name
			
			# Saves stuff
			addi $sp, $sp, -8
			sw $t0, 0($sp)
			sw $t1, 4($sp)
			jal friend_helper_exists
			# Load stuff
			lw $t0, 0($sp)
			lw $t1, 4($sp)
			addi $sp, $sp, 8
			
			beqz $v0, friend_loop_tail
			j friends_of_friends
			
		friend_second_equal:
			# Checks if they were friends.
			lw $t2, 8($t0)
			li $t3, 1
			bne $t2, $t3, friend_loop_tail
		
			lw $a1, 0($t0) # first name in edge
			lw $a0, 4($sp) # Base address
			lw $a2, 20($sp) # person2 name
			
			# Saves stuff
			addi $sp, $sp, -8
			sw $t0, 0($sp)
			sw $t1, 4($sp)
			jal friend_helper_exists
			# Load stuff
			lw $t0, 0($sp)
			lw $t1, 4($sp)
			addi $sp, $sp, 8
			beqz $v0, friend_loop_tail
			j friends_of_friends
			
		friend_loop_tail:
			addi $t0, $t0, 12
			addi $t1, $t1, -1
			j friend_loop
	
	they_aint_connected:
		lw $ra, 0($sp)
		addi $sp, $sp, 24
		li $v0, 0
		jr $ra
		
	friends_of_friends:
		# Checks if these two are friends.
		lw $a0, 4($sp)
		lw $a1, 16($sp)
		lw $a2, 20($sp)
	
		# Saves stuff
		addi $sp, $sp, -8
		sw $t0, 0($sp)
		sw $t1, 4($sp)
		jal friend_helper_exists
		# Load stuff
		lw $t0, 0($sp)
		lw $t1, 4($sp)
		addi $sp, $sp, 8
		li $t3, 1
		beq $v0, $t3, friend_loop_tail # If they are friends, go loop again.
	
		lw $ra, 0($sp)
		addi $sp, $sp, 24
		li $v0, 1
		jr $ra
	
	this_dude_dont_exist:
		lw $ra, 0($sp)
		addi $sp, $sp, 24
		li $v0, -1
		jr $ra


friend_helper_exists:
# int friend_helper_exists(Network* ntwrk, Node* person1, Node* person2)
# size_of_edge is always 12 according to brief description number 4.
# first word and second word is address of node, third word is relationship.

# $a0 = base address of network
# $a1 = address of node1
# $a2 = address of node2

	lw $t0, 20($a0) # Number of edges currently the network has
	lw $t7, 0($a0)
	lw $t6, 8($a0)
	mult $t6, $t7
	mflo $t1
	addi $t1, $t1, 36 # base address of edges[]
	add $t1, $t1, $a0
	
	move $t2, $t0 # Duplicate num of edges for counter.
	friend_helper_loop:
		beqz $t2, friend_helper_nothing_found # If all edges are gone through.
	
		lw $t3, 0($t1) # Loads the word
		beq $t3, $a1, friend_helper_first_equal # Is first equal
		beq $t3, $a2, friend_helper_second_equal # Is second equal
		j friend_helper_loop_tail
		
		# If the other is equal, then found, else loop.
		friend_helper_first_equal:
			lw $t3, 4($t1)
			beq $t3, $a2, friend_helper_found
			j friend_helper_loop_tail
		friend_helper_second_equal:
			lw $t3, 4($t1)
			beq $t3, $a1, friend_helper_found
			j friend_helper_loop_tail
			
		friend_helper_loop_tail:
		addi $t1, $t1, 12
		addi $t2, $t2, -1
		j friend_helper_loop
	
	friend_helper_found:
		li $t0, 1
		lw $t1, 8($t1)
		beq $t0, $t1, friend_helper_yay
		li $v0, 0
		jr $ra
		
	friend_helper_yay:
		li $v0, 1
		jr $ra
			
	friend_helper_nothing_found:
		li $v0, 0
		jr $ra