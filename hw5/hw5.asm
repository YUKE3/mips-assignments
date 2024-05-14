############## Vincent Ke	 ##############
############## 113778667 	 #################
############## YUKE		 ################

############################ DO NOT CREATE A .data SECTION ############################
############################ DO NOT CREATE A .data SECTION ############################
############################ DO NOT CREATE A .data SECTION ############################
.text:
.globl create_term
create_term:
# Term* create_term(int coeff, int exp)

# Changes $a0, $a1, $t0, $t1

# 12 Bytes
# first 4 bytes holds the coefficient
# second 4 bytes holds the exponent
# third 4 bytes holds address of another term. (Zero for this method)

# $a0 = coefficient
# $a1 = exponenet
	move $t0, $a0
	move $t1, $a1 # Copies parameters.
	
	# Allocate 12 bytes of memory.
	li $a0, 12
	li $v0, 9
	syscall
	# $v0 is the base address of new data structure
	
	sw $t0, 0($v0) # First 4 bytes
	sw $t1, 4($v0) # Second 4 bytes
	sw $0, 8($v0) # Third 4 bytes
	
	# Return to caller.
	jr $ra

.globl create_polynomial
create_polynomial:
# Polynomial * create_polynomial(int[] terms, N)

# $a0 = an array of pairs (coefficient, exponenet)
#	Always terminated by the pair (0, -1)
# $a1 = an integer N.
#	If N is 0 or negative or greater than the size of the array.
#		Create a linked list of terms with all pairs in array.
# 		Sorted by exponenets in descending order.
#		Ignore duplicates.
# 		If same exponenet, coefficient together.
#	If N is positive and less than the array size
#		linked list of terms with N highest pairs
#		Ignore duplicates
#		N highest pairs
#		If same exponenet, coefficient together.

	# Get array size:
	li $t3, 0 # Counter
	move $t0, $a0 # Copies memory address
	create_polynomial_array_size_loop:
		lw $t1, 0($t0) # Loads the two values.
		lw $t2, 4($t0)
		
		bnez $t1, create_polynomial_array_size_loop_cont
		li $t1, -1
		bne $t1, $t2, create_polynomial_array_size_loop_cont
		
		# Here should be reached if the pair is (0,-1)
		j create_polynomial_array_size_loop_done
	create_polynomial_array_size_loop_cont:
		addi $t0, $t0, 8 # Increment address
		addi $t3, $t3, 1 # Increment counter.
		j create_polynomial_array_size_loop

	# Get array size has been verified to work.
	# $a0-1 remain unchanged, $t3 is size of array.

	create_polynomial_array_size_loop_done:
		
		beqz $t3, create_polynomial_failed
		# jump to second part N is bigger than 0
		bgt $a1, $0, create_polynomial_two
		li $t4, -1
	create_polynomial_one:
		
		lw $t1, 0($a0)
		lw $t2, 4($a0)
		# Loads first pair
		
		# Saves the $a0 and $ra
		addi $sp, $sp, -4
		sw $ra, 0($sp)
		addi $sp, $sp, -4
		sw $a0, 0($sp)
		
		move $a0, $t1
		move $a1, $t2
		jal create_term
		move $t7, $v0
		# Loads address again
		lw $a0, 0($sp)
		
		addi $t4, $t4, -1
		
		# Up until here has been tested and is working
		# $a0 is address of pair
		# $sp is offset by 8, and contains return address, $a0
		# $v0 contains head of polynomial.
		
		create_polynomial_one_loop:
			addi $a0, $a0, 8 # Increment address of pair
			lw $t1, 0($a0)
			lw $t2, 4($a0) # Reads the two integers.
			
			# $t4 keeps track of how much has been processed. it is only positive if this code follows part2.
			beqz $t4, create_polynomial_one_loop_done
			
			bnez $t1, create_polynomial_one_loop_cont		
			li $t0, -1
			bne $t2, $t0, create_polynomial_one_loop_cont
			# Here should only be reached if there is no pairs left.
			j create_polynomial_one_loop_done
		create_polynomial_one_loop_cont:
			move $t0, $t7 # Copies address of head.
			li $t6, 0
			
			create_polynomial_one_inner_loop:
				beq $t0, $0, create_polynomial_one_tail
				lw $t3, 4($t0) # Loads exponent
				bgt $t2, $t3, create_polynomial_one_inner_loop_esc
				beq $t2, $t3, create_polynomial_one_check_dup
				
				move $t6, $t0 # Previous address.
				lw $t0, 8($t0) # The next address.
				j create_polynomial_one_inner_loop
				
				
			create_polynomial_one_inner_loop_esc:
				# This should happen when exponent is greater than exponent at current address.
				# This needs previous address.
				addi $sp, $sp, -4
				sw $a0, 0($sp) # Save $a0
				move $a0, $t1
				move $a1, $t2
				jal create_term
				lw $a0, 0($sp)
				addi $sp, $sp, 4
				# Creates a new term at $v0.
				# $t6 current has the previous term.
				beqz $t6, create_polynomial_one_inner_loop_esc_new
				lw $t0, 8($t6) # current term
				sw $t0, 8($v0) # saves that to new term
				sw $v0, 8($t6) # saves new term to next of previous
				addi $t4, $t4, -1 # Decrement counter.
				j create_polynomial_one_loop_tail
			create_polynomial_one_inner_loop_esc_new:
				# This should run if and only there is no previous, this is the biggest exponent.
				sw $t7, 8($v0) # Saves head to next of new.
				move $t7, $v0 # Set head to new.
				addi $t4, $t4, -1 # Decrement counter.
				j create_polynomial_one_loop_tail
				
			create_polynomial_one_check_dup:
				# This should happen when exponent is equal
				# This doesn't need previous address.
				
				# $a0, $t0, $t1, $t2, $t7 shouldn't be changed.
				lw $t5, 0($sp) # should be the original $a0.
				create_polynomial_one_check_dup_loop:
					beq $t5, $a0, create_polynomial_one_check_dup_no_dup # If we reached current.
					lw $t3, 4($t5)
					beq $t3, $t2, create_polynomial_one_check_dup_check
					j create_polynomial_one_check_dup_loop_tail
				create_polynomial_one_check_dup_check:
					lw $t3, 0($t5)
					beq $t3, $t1, create_polynomial_one_loop_tail # This is a dupe
					j create_polynomial_one_check_dup_loop_tail
				create_polynomial_one_check_dup_loop_tail:
					addi $t5, $t5, 8
					j create_polynomial_one_check_dup_loop
					
				create_polynomial_one_check_dup_no_dup:
					lw $t3, 0($t0)
					add $t3, $t3, $t1 # Adds coefficient
					sw $t3, 0($t0)
					addi $t4, $t4, -1 # Decrement counter
					
				j create_polynomial_one_loop_tail
			create_polynomial_one_tail:
				# This should happen when exponenet is the smallest.
				# This need previous address.
				addi $sp, $sp, -4
				sw $a0, 0($sp) # Save $a0
				move $a0, $t1
				move $a1, $t2
				jal create_term
				lw $a0, 0($sp)
				addi $sp, $sp, 4
				# Creates a new term at $v0.
				sw $v0, 8($t6)
				addi $t4, $t4, -1 # Decrement counter
				j create_polynomial_one_loop_tail
				
		create_polynomial_one_loop_tail:
			j create_polynomial_one_loop
			
		create_polynomial_one_loop_done:
			move $a0, $t7
			jal remove_zero_coefficients
			move $v0, $a0
		
			addi $sp, $sp, 4
			lw $ra, 0($sp)
			addi $sp, $sp, 4
			jr $ra
		
	create_polynomial_two:
		# jumps back if N wasn't actually in range.
		bge $a1, $t3, create_polynomial_one
		
		# Implementation idea:
		# Literally just sort those terms and then trim.
		# Then call create_polynomial_one
		
		# What shouldn't be changed, $a0, $a1
		
		# Selection sort based on coefficients.
		
		# $a0 is the array of pairs.
		
		move $t0, $a0 # copy the array.
		move $t1, $t0 # memory of first.
		addi $t0, $t0, 8
		move $t2, $t0 # memory of second.
		
		li $t7, 0 # Counter for amount sorted.
		
		create_polynomial_selection_sort:
			# exit condition, when this loop is done.
			lw $t3, 0($t2)
			bnez $t3, create_polynomial_selection_sort_cont
			li $t4, -1
			lw $t3, 4($t2)
			bne $t3, $t4, create_polynomial_selection_sort_cont
			# This should be reached when current iteration reaches (1,0)
			addi $t0, $t0, 8
			addi $t1, $t1, 8
			move $t2, $t0
			# Checks if this is end
			lw $t3, 0($t2)
			bnez $t3, create_polynomial_selection_sort
			li $t4, -1
			lw $t3, 4($t2)
			bne $t3, $t4, create_polynomial_selection_sort
			j create_polynomial_selection_sort_done
			
			create_polynomial_selection_sort_cont:
			lw $t3, 4($t2) # Coefficient2
			lw $t4, 4($t1) # Coefficient1
			
			bgt $t3, $t4, create_polynomial_swap
			
			create_polynomial_selection_sort_tail:
			addi $t2, $t2, 8
			j create_polynomial_selection_sort
			
			create_polynomial_swap:
				# Swaps components at $t1, $t2
				lw $t3, 0($t2)
				lw $t4, 0($t1)
				sw $t4, 0($t2)
				sw $t3, 0($t1)
				lw $t3, 4($t2)
				lw $t4, 4($t1)
				sw $t4, 4($t2)
				sw $t3, 4($t1)
				j create_polynomial_selection_sort_tail
		
		create_polynomial_selection_sort_done:
			# Sets after sorted part to 0,-1, since they aren't used anymore.
			move $t4, $a1
			j create_polynomial_one
  
	create_polynomial_failed:
		# This should only happen if there is no pairs.
		# Assuming $0 is NULL. Honestly don't know what else I can do.
		move $v0, $0
		jr $ra


remove_zero_coefficients:
# $a0 = start of polynomial.
# IN-PLACE operation. changes $a0
	addi $sp, $sp, -8
	sw $ra, 0($sp)
	sw $a0, 4($sp)
	
	move $t0, $a0 # Previous node
	lw $t1, 8($a0) # Current node
	remove_zero_coefficient_loop:
		beqz $t1, remove_zero_coefficient_done
	
		lw $t5, 0($t1) # Current node coefficient.
		beqz $t5, remove_zero_coefficient_loop_remove
		
		# If current node doesn't have a zero.
		move $t0, $t1 # Prev = Curr
		lw $t1, 8($t1) # Curr = Next
		j remove_zero_coefficient_loop
		
	remove_zero_coefficient_loop_remove:
		lw $t5, 8($t1)
		sw $t5, 8($t0) # PreviousNode.next = CurrentNode.next
		
		lw $t1, 8($t0)
		j remove_zero_coefficient_loop
	
	remove_zero_coefficient_first_zero:
		lw $a0, 8($a0)
		lw $ra, 0($sp)
		addi $sp, $sp, 8
		jr $ra
		
	remove_zero_coefficient_done:
		lw $a0, 4($sp)
		lw $t0, 0($a0)
		beqz $t0, remove_zero_coefficient_first_zero
	
		lw $ra, 0($sp)
		addi $sp, $sp, 8
		jr $ra
		
	remove_zero_coefficient_no_polynomial_left:
		move $a0, $0
		jr $ra

.globl add_polynomial
add_polynomial:
# $a0, $a1 are both sorted polynomials.

# Implementations idea:
# Keep two pointers to head of both polynomial
# Find if they are equal, if they are, add one coefficient to another, then add it to a new polynomial
# If they are not equal, take the higher one, and add to the new polynomial.
# The new polynomial head can simply be the bigger one at the start.

# Base edge cases:
# Both are nothing
# One is nothing

beqz $a0, add_polynomial_edge_0
beqz $a1, add_polynomial_edge_1

j add_polynomial_start

add_polynomial_edge_0:
	# Being nothing should be accounted for, since it will just return the other one which is $0.
	move $v0, $a1
	jr $ra
add_polynomial_edge_1:
	# Since $a0 has to be something for this to be reached.
	move $v0, $a0
	jr $ra
	
add_polynomial_start:
	move $t1, $a0 # Head for first polynomial
	move $t2, $a1 # Head for second polynomial
	
	lw $t3, 4($t1) # exponent of first polynomial first term
	lw $t4, 4($t2) # exponent of second polynomial first term
	
	bgt $t3, $t4, add_polynomial_start_big1
	bgt $t4, $t3, add_polynomial_start_big2
	
	# Here is reached if both exponents are the same
	lw $t3, 0($t1)
	lw $t4, 0($t2)
	add $t3, $t3, $t4 # Adds the two coefficients together.
	sw $t3, 0($t1) # Saves coefficient in first term of first polynomial
	move $t0, $t1 # Make first term of new polynomial head.
	
	lw $t1, 8($t1) # Go to next term in the polynomials
	lw $t2, 8($t2)
	
	move $t7, $t0 # Saves the head of new polynomial
	
	j add_polynomial_loop
	
	add_polynomial_start_big1:
		# This is if the first polynomial has a bigger first term
		move $t0, $t1 # Make first term of the new polynomial firt polynomial head
		lw $t1, 8($t1) # Goes to the next term in the first polynomial
		move $t7, $t0 # Saves the head of new polynomial
		j add_polynomial_loop
	add_polynomial_start_big2:
		# This is if the first polynomial has a smaller first term
		move $t0, $t2 # Make first term of new polynomial head of second polynomial
		lw $t2, 8($t2) # Goes to the next term in the second polynomial
		move $t7, $t0 # Saves the head of new polynomial
		j add_polynomial_loop
		
	# $t0 = last polynomial in new polynomial
	# $t1 = head of rest of polynomials in first polynomial
	# $t2 = head of rest of polynomials in second polynomial
	# $t7 = head of new polynomial
	add_polynomial_loop:
		# Checks if either of the polynomials are empty.
		beqz $t1, add_polynomial_loop_first_empty
		beqz $t2, add_polynomial_loop_second_empty
		
		lw $t3, 4($t1) # Loads exponenets
		lw $t4, 4($t2)
		
		bgt $t3, $t4, add_polynomial_loop_first_greater
		bgt $t4, $t3, add_polynomial_loop_second_greater
		# This is reached if $t3 = $t4
		j add_polynomial_loop_equal
		
		add_polynomial_loop_first_empty:
			# Both polynomials have nothing left.
			beqz $t2, add_polynomial_loop_done
			
			# We only need to add the rest of $t2
			sw $t2, 8($t0)
			j add_polynomial_loop_done
		add_polynomial_loop_second_empty:
			# Since we already verified $t1 is not empty
			sw $t1, 8($t0)
			j add_polynomial_loop_done
		
		add_polynomial_loop_first_greater:
			sw $t1, 8($t0) # Greater exponent go in new.
			move $t0, $t1 # new tail of new polynomial
			lw $t1, 8($t1) # $t1 goes to next term.
			j add_polynomial_loop
		add_polynomial_loop_second_greater:
			sw $t2, 8($t0) # Greater exponent go in new.
			move $t0, $t2 # new tail of new polynomial
			lw $t2, 8($t2) # $t2 goes to next term.
			j add_polynomial_loop
			
		add_polynomial_loop_equal:
			lw $t5, 0($t1) # coefficient of first
			lw $t6, 0($t2) # coefficient of second
			add $t5, $t5, $t6 # Adds together
			sw $t5, 0($t1) # Saves coefficient
			sw $t1, 8($t0) # Adds to new.
			move $t0, $t1 # new tail of new polynomial
			
			lw $t1, 8($t1)
			lw $t2, 8($t2) # both polynomial goes to next.
			j add_polynomial_loop
			
		add_polynomial_loop_done:
			move $a0, $t7
			addi $sp, $sp, -4
			sw $ra, 0($sp)
			jal remove_zero_coefficients # well self explanatory.
			move $v0, $a0
			lw $ra, 0($sp)
			addi $sp, $sp, 4
			jr $ra

.globl mult_polynomial
mult_polynomial:
# Polynomial* mult_polynomial(polynomial* p, Polynomial* q)
# Implementation idea:
# Create an array which contains m*n+2 elements, where m is length of poly1 and n is length ploy2
# Fill the elements up using two for loops
# Last element = 0 -1
# call create polynomial

# base case
beqz $a0, explode
beqz $a1, explode

mult_polynomial_get_m:
	li $t1, 0 # Counter for m
	move $t0, $a0
	mult_polynomial_get_m_loop:
		beqz $t0, mult_polynomial_get_m_loop_done
		lw $t0, 8($t0)
		addi $t1, $t1, 1
		j mult_polynomial_get_m_loop
	mult_polynomial_get_m_loop_done:
	
	li $t2, 0 # Counter for n
	move $t0, $a1
	mult_polynomial_get_n_loop:
		beqz $t0, mult_polynomial_get_n_loop_done
		lw $t0, 8($t0)
		addi $t2, $t2, 1
		j mult_polynomial_get_n_loop
	mult_polynomial_get_n_loop_done:
	
	# $t1 = m, $t2 = n
	move $t0, $a0 # store for now
	mult $t1, $t2
	mflo $a0
	addi $a0, $a0, 1
	li $t3, 8
	mult $a0, $t3
	mflo $a0
	li $v0, 9
	syscall
	move $t7, $v0 # address to array
	move $t6, $t7 # a copy of address to array.
	# $t7 contains head of address to (m*n)+2 sized array.
	
	move $a0, $t0 # Gets $a0 again.
	
	move $t1, $a0 # For looping.
	mult_polynomial_outer_loop:
		beqz $t1, mult_polynomial_outer_loop_done
		move $t2, $a1 # For looping
		mult_polynomial_inner_loop:
			beqz $t2, mult_polynomial_inner_loop_done
			
				# Here should loop for every m term for every n term.
				# $t1, $t2, $t6, $t7 are all used, $a should not be changed.
				# Here I need to multiply coefficient, add exponent.
			
				lw $t3, 0($t1)
				lw $t4, 0($t2) # Loads coefficients
				mult $t3, $t4
				mflo $t3
				sw $t3, 0($t6) # coefficient * coefficient
				
				lw $t3, 4($t1)
				lw $t4, 4($t2) # Loads exponent
				add $t3, $t3, $t4
				sw $t3, 4($t6) # exponent + exponent
				
				# Increment address of array
				addi $t6, $t6, 8
			
			lw $t2, 8($t2) # For looping.
			j mult_polynomial_inner_loop
		mult_polynomial_inner_loop_done:
		lw $t1, 8($t1) # For looping
		j mult_polynomial_outer_loop
	mult_polynomial_outer_loop_done:
	# Saves end of array.
	sw $0, 0($t6)
	li $t0, -1
	sw $t0, 4($t6)
	
	
	
	move $a0, $t7
	li $a1, -1
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	jal special_c_p
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	
  jr $ra
  
  explode:
  	move $v0, $0
  	jr $ra
  
  
  special_c_p:
# Polynomial * special_c_p(int[] terms, N)

# Full implementation of part 2 but with dupe enabled.
# Literally just the whole method but with a single line commented lol.

	# Get array size:
	li $t3, 0 # Counter
	move $t0, $a0 # Copies memory address
	special_c_p_array_size_loop:
		lw $t1, 0($t0) # Loads the two values.
		lw $t2, 4($t0)
		
		bnez $t1, special_c_p_array_size_loop_cont
		li $t1, -1
		bne $t1, $t2, special_c_p_array_size_loop_cont
		
		# Here should be reached if the pair is (0,-1)
		j special_c_p_array_size_loop_done
	special_c_p_array_size_loop_cont:
		addi $t0, $t0, 8 # Increment address
		addi $t3, $t3, 1 # Increment counter.
		j special_c_p_array_size_loop

	# Get array size has been verified to work.
	# $a0-1 remain unchanged, $t3 is size of array.

	special_c_p_array_size_loop_done:
		
		beqz $t3, special_c_p_failed
		# jump to second part N is bigger than 0
		bgt $a1, $0, special_c_p_two
		li $t4, -1
	special_c_p_one:
		
		lw $t1, 0($a0)
		lw $t2, 4($a0)
		# Loads first pair
		
		# Saves the $a0 and $ra
		addi $sp, $sp, -4
		sw $ra, 0($sp)
		addi $sp, $sp, -4
		sw $a0, 0($sp)
		
		move $a0, $t1
		move $a1, $t2
		jal create_term
		move $t7, $v0
		# Loads address again
		lw $a0, 0($sp)
		
		addi $t4, $t4, -1
		
		# Up until here has been tested and is working
		# $a0 is address of pair
		# $sp is offset by 8, and contains return address, $a0
		# $v0 contains head of polynomial.
		
		special_c_p_one_loop:
			addi $a0, $a0, 8 # Increment address of pair
			lw $t1, 0($a0)
			lw $t2, 4($a0) # Reads the two integers.
			
			# $t4 keeps track of how much has been processed. it is only positive if this code follows part2.
			beqz $t4, special_c_p_one_loop_done
			
			bnez $t1, special_c_p_one_loop_cont		
			li $t0, -1
			bne $t2, $t0, special_c_p_one_loop_cont
			# Here should only be reached if there is no pairs left.
			j special_c_p_one_loop_done
		special_c_p_one_loop_cont:
			move $t0, $t7 # Copies address of head.
			li $t6, 0
			
			special_c_p_one_inner_loop:
				beq $t0, $0, special_c_p_one_tail
				lw $t3, 4($t0) # Loads exponent
				bgt $t2, $t3, special_c_p_one_inner_loop_esc
				beq $t2, $t3, special_c_p_one_check_dup
				
				move $t6, $t0 # Previous address.
				lw $t0, 8($t0) # The next address.
				j special_c_p_one_inner_loop
				
				
			special_c_p_one_inner_loop_esc:
				# This should happen when exponent is greater than exponent at current address.
				# This needs previous address.
				addi $sp, $sp, -4
				sw $a0, 0($sp) # Save $a0
				move $a0, $t1
				move $a1, $t2
				jal create_term
				lw $a0, 0($sp)
				addi $sp, $sp, 4
				# Creates a new term at $v0.
				# $t6 current has the previous term.
				beqz $t6, special_c_p_one_inner_loop_esc_new
				lw $t0, 8($t6) # current term
				sw $t0, 8($v0) # saves that to new term
				sw $v0, 8($t6) # saves new term to next of previous
				addi $t4, $t4, -1 # Decrement counter.
				j special_c_p_one_loop_tail
			special_c_p_one_inner_loop_esc_new:
				# This should run if and only there is no previous, this is the biggest exponent.
				sw $t7, 8($v0) # Saves head to next of new.
				move $t7, $v0 # Set head to new.
				addi $t4, $t4, -1 # Decrement counter.
				j special_c_p_one_loop_tail
				
			special_c_p_one_check_dup:
				# This should happen when exponent is equal
				# This doesn't need previous address.
				
				# $a0, $t0, $t1, $t2, $t7 shouldn't be changed.
				lw $t5, 0($sp) # should be the original $a0.
				special_c_p_one_check_dup_loop:
					beq $t5, $a0, special_c_p_one_check_dup_no_dup # If we reached current.
					lw $t3, 4($t5)
					beq $t3, $t2, special_c_p_one_check_dup_check
					j special_c_p_one_check_dup_loop_tail
				special_c_p_one_check_dup_check:
					lw $t3, 0($t5)
					#beq $t3, $t1, special_c_p_one_loop_tail # This is a dupe
					j special_c_p_one_check_dup_loop_tail
				special_c_p_one_check_dup_loop_tail:
					addi $t5, $t5, 8
					j special_c_p_one_check_dup_loop
					
				special_c_p_one_check_dup_no_dup:
					lw $t3, 0($t0)
					add $t3, $t3, $t1 # Adds coefficient
					sw $t3, 0($t0)
					addi $t4, $t4, -1 # Decrement counter
					
				j special_c_p_one_loop_tail
			special_c_p_one_tail:
				# This should happen when exponenet is the smallest.
				# This need previous address.
				addi $sp, $sp, -4
				sw $a0, 0($sp) # Save $a0
				move $a0, $t1
				move $a1, $t2
				jal create_term
				lw $a0, 0($sp)
				addi $sp, $sp, 4
				# Creates a new term at $v0.
				sw $v0, 8($t6)
				addi $t4, $t4, -1 # Decrement counter
				j special_c_p_one_loop_tail
				
		special_c_p_one_loop_tail:
			j special_c_p_one_loop
			
		special_c_p_one_loop_done:
			move $a0, $t7
			jal remove_zero_coefficients
			move $v0, $a0
		
			addi $sp, $sp, 4
			lw $ra, 0($sp)
			addi $sp, $sp, 4
			jr $ra
		
	special_c_p_two:
		# jumps back if N wasn't actually in range.
		bge $a1, $t3, special_c_p_one
		
		# Implementation idea:
		# Literally just sort those terms and then trim.
		# Then call special_c_p_one
		
		# What shouldn't be changed, $a0, $a1
		
		# Selection sort based on coefficients.
		
		# $a0 is the array of pairs.
		
		move $t0, $a0 # copy the array.
		move $t1, $t0 # memory of first.
		addi $t0, $t0, 8
		move $t2, $t0 # memory of second.
		
		li $t7, 0 # Counter for amount sorted.
		
		special_c_p_selection_sort:
			# exit condition, when this loop is done.
			lw $t3, 0($t2)
			bnez $t3, special_c_p_selection_sort_cont
			li $t4, -1
			lw $t3, 4($t2)
			bne $t3, $t4, special_c_p_selection_sort_cont
			# This should be reached when current iteration reaches (1,0)
			addi $t0, $t0, 8
			addi $t1, $t1, 8
			move $t2, $t0
			# Checks if this is end
			lw $t3, 0($t2)
			bnez $t3, special_c_p_selection_sort
			li $t4, -1
			lw $t3, 4($t2)
			bne $t3, $t4, special_c_p_selection_sort
			j special_c_p_selection_sort_done
			
			special_c_p_selection_sort_cont:
			lw $t3, 4($t2) # Coefficient2
			lw $t4, 4($t1) # Coefficient1
			
			bgt $t3, $t4, special_c_p_swap
			
			special_c_p_selection_sort_tail:
			addi $t2, $t2, 8
			j special_c_p_selection_sort
			
			special_c_p_swap:
				# Swaps components at $t1, $t2
				lw $t3, 0($t2)
				lw $t4, 0($t1)
				sw $t4, 0($t2)
				sw $t3, 0($t1)
				lw $t3, 4($t2)
				lw $t4, 4($t1)
				sw $t4, 4($t2)
				sw $t3, 4($t1)
				j special_c_p_selection_sort_tail
		
		special_c_p_selection_sort_done:
			# Sets after sorted part to 0,-1, since they aren't used anymore.
			move $t4, $a1
			j special_c_p_one
  
	special_c_p_failed:
		# This should only happen if there is no pairs.
		# Assuming $0 is NULL. Honestly don't know what else I can do.
		move $v0, $0
		jr $ra
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  