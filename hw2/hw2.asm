################# Vincent Ke	 #################
################# YUKE 		 #################
################# 113778667 #################

################# DO NOT CHANGE THE DATA SECTION #################

.text
.globl to_upper # (char* s)
to_upper:
move $t3, $a0 # Loads the memory address of the string into $t3.
to_upper_loop:
	lbu $t0, 0($t3) # Loads a bit from the string.
	beq $t0, $0, to_upper_end # If the bit is null, terminate.
	li $t1, 'a' # Loads 'A' into $t1.
	bge $t0, $t1, to_upper_convert # If $t0 (bit) is bigger than 'a', then it means that it could be uppercase, so it goes to to_upper_convert
to_upper_restart_loop:
	addi $t3, $t3, 1 # Increment address value by 1.
	j to_upper_loop
	
to_upper_convert:
	li $t1, 'z' # Loads 'A' into $t1
	bgt $t0, $t1, to_upper_restart_loop # Verify that it is upper case, if it is greater than 'z' then it is not uppercase.
	li $t1, 32
	subu $t0, $t0, $t1 # Decrease the value of $t0 by 35, making it a positive ascii.
	sb $t0, 0($t3) # Saves the bit back in.
	j to_upper_restart_loop
	
to_upper_end:
 jr $ra



.globl remove # (char* s, int i)
remove:
move $t3, $a0 # Loads memory address into $t3.
move $t2, $a1 # Loads the index into $t2.
remove_loop1:
	lbu $t0, 0($t3) # Loads bit
	beq $t0, $0, remove_failed # If a bit is null here, that means the index is larger than char* size.
	beqz $t2, remove_loop2_start # if index == 0, then remove_loop2_start;
	li $t1, 1 
	sub $t2, $t2, $t1 # index = index - 1
	addi $t3, $t3, 1 # Increment address.
	j remove_loop1
	
remove_loop2_start:
	move $t4, $t3 # Copy the memory address of $t3 to $t4.
	addi $t4, $t4, 1 # Increment by 1, this is the bit after the index.
	lb $t0, 0($t4) # Loads bit after index.
	sb $t0, 0($t3) # Saves that bit to where it is at index.
	beqz $t0, remove_success # If that last bit was the null terminator, then all operation is done.
	j remove_loop2
	
remove_loop2:
	addi $t4, $t4, 1
	addi $t3, $t3, 1
	lb $t0, 0($t4)
	sb $t0, 0($t3) # Loads $t4($t3+1) bit into $t3.
	beqz $t0, remove_success # If it was the null terminator, then the operation is done.
	j remove_loop2
	
remove_failed:
	li $v0, -1 # Loads -1 into $v0, return value
	jr $ra
	
remove_success:
	li $v0, 1 # Loads 1 into $v0, return value
	jr $ra



.globl getRandomInt # (int n)
getRandomInt:
	blez $a0, n_less_or_equal_0 # If n <=0 then call n_less_or_equal_0 
	
	move $a1, $a0 # Moves argument 1 to $a1 for systemcall.
	li $v0, 42
	syscall # Generates the random number.

	move $v0, $a0 # Copies the generated number into $v0.
	li $v1, 1 # Loads 1 into register $v1.
	jr $ra
	
n_less_or_equal_0: # Loads -1 in register $v0 and loads 0 in register $v1.
	li $v0, -1
	li $v1, 0
	jr $ra



.globl cpyElems # char* src, int i, char* dest
cpyElems:
move $t3, $a0 # Loads src char array address into $t3.
move $t2, $a1 # Loads i (index) into $t2.
get_element:
	lbu $t0, 0($t3) # Loads bit of source.
	beqz $t0, index_exceed_src # If the bit is the null terminator, then the index exceeds the source length.
	beqz $t2, add_to_dest # Found the element at index.
	addi $t3, $t3, 1 # Increment address.
	li $t1, 1
	sub $t2, $t2, $t1 # Index = index - 1
	j get_element

add_to_dest:
	sb $t0, 0($a2) # Saves the element.
	addi $v0, $a2, 1 # Sets $v0 to the next address of destination string.
	sb $0, 0($v0) # Saves null terminator to the new destination address.
	jr $ra

 index_exceed_src: # Nothing in direction, but gonna put it here just in case.
 	move $v0, $a2 # Since the next address isn't going to change.
 	sb $0, 0($v0) # Saves null terminator.
 	jr $ra



.globl genKey # (char* alphabet, char* cipherKey)
genKey:
addi $sp, $sp, -4
sw $ra, 0($sp) # Saves the return address.
li $t1, 1 # Length of alphabet.
move $t3, $a0 # Copies memory address of alphabet to $t3.
get_length_of_alphabet:
	lbu $t0, 0($t3)
	beqz $t0, gen_key_start # Reached the end of alphabets.
	addi $t1, $t1, 1
	addi $t3, $t3, 1 # Increment address and length.
	j get_length_of_alphabet

gen_key_start:
	move $t3, $t1 # Moves the length into $s3     > $t3
	move $t1, $a0 # Get parameters 1. alphabet    > $t1
	move $t2, $a1 # Get parameters 2. cipherKey.  > $t2
	
	addi $sp, $sp, -12 # Allocate space for 4 temp variables.
	sw $t1, 0($sp)  # alphabet
	sw $t2, 4($sp)  # cipherkey
	sw $t3, 8($sp)  # length

gen_key_loop:
	lw $a0, 8($sp) # Put the length into $a0, to generate random num.
	jal getRandomInt # Get random does not modify $t variables, no need for perserving $t.
	# Return in $v0
	
	
	#cpyElems: src alphabet, int random, dest cipherkey
	lw $a0, 0($sp)
	move $a1, $v0 # Random number
	lw $a2, 4($sp)
	jal cpyElems
	sw $v0, 4($sp) # new address for new element > saved location for cipherkey.
	
	#remove: src alphabet, index length
	lw $a0, 0($sp)
	# $a1 remain unchanged in cpyElems.
	jal remove
	sw $a0, 0($sp) # Updates the alphabet.
	
	li $t4, 1
	lw $t3, 8($sp) # Loads the length.
	subu $t3, $t3, $t4 # Decrement length
	sw $t3, 8($sp) # Update the length.
	
	beqz $t3, gen_key_done # GenKey done.
	j gen_key_loop

gen_key_done:
	addi $sp, $sp, 12 # Unallocated what is used in the loop.
	lw $ra, 0($sp) # Get return address.
	addi $sp, $sp, 4 # Unallocated return address.

	jr $ra



.globl contains # char* s, char ch
contains:
move $t3, $a0 # Moves the string address to $t3.
move $t2, $a1 # Moves the character to $t2.
li $t1, 0 # Index of element.
contains_loop:
	lbu $t0, 0($t3) # Load bit
	beq $t0, $t2, contain_element # If bit == char then contain_element
	beqz $t0, contain_element_not # If bit == null then contain_element_not
	addi $t3, $t3, 1
	addi $t1, $t1, 1 # Increment address and index.
	j contains_loop
	
contain_element:
	move $v0, $t1 # Moves t1 to $v0
	jr $ra
	
contain_element_not:
	li $v0, -1 # Return -1
	jr $ra



.globl pair_exists # char c1, char c2, char* key
pair_exists:

# Check to see if the two char are uppercase case letters.
li $t0, 'A'
li $t1, 'Z'
blt $a0, $t0, no_pair_exists
blt $a1, $t0, no_pair_exists
bgt $a0, $t1, no_pair_exists
bgt $a1, $t1, no_pair_exists

pair_exists_linear: # pass in key and c1, c2
	move $t3, $a2 # key address > $t3
	pair_exists_linear_loop:
		lbu $t0, 0($t3) # Loads bit from key.
		beqz $t0, pair_exists_linear_2 # If it doesn't exists, then check again with the other formation.
		beq $t0, $a0, pair_exists_check # If the first pair is found.
		addi $t3, $t3, 1 # Increment address.
		j pair_exists_linear_loop
		
	pair_exists_check:
		addi $t3, $t3, 1
		lbu $t0, 0($t3) # Char after found char.
		bne $t0, $a1, pair_exists_linear_2 # If not equal.
		li $v0, 1
		jr $ra
		
	pair_exists_linear_2:
		move $t3, $a2 # key address > $t3
	pair_exists_linear_loop_2:
		lbu $t0, 0($t3) # load bit
		beqz $t0, no_pair_exists # If bit is null terminator, then there means no pair exists here.
		beq $t0, $a1, pair_exists_check_2 # If the second char is found.
		addi $t3, $t3, 1 # Increment address.
		j pair_exists_linear_loop_2
	
	pair_exists_check_2:
		addi $t3, $t3, 1
		lbu $t0, 0($t3)
		bne $t0, $a0, no_pair_exists
		li $v0, 1
		jr $ra
		
	no_pair_exists:
		li $v0, 0
		jr $ra



.globl encrypt # char* plaintext, char* cipherkey, char* ciphertext
encrypt:
addi $sp, $sp, -4 # Save the return address.
sw $ra, 0($sp)

# $a0 is already plain text.
jal to_upper
# to_upper doesn't change $a, so it should be fine.

move $t3, $a0 # $t3 = plaintext address
move $t4, $a1 # $t4 = cipherkey address
move $t5, $a2 # $t5 = ciphertext address

encrypt_loop:
	lbu $t0, 0($t3)
	beqz $t0, done_encrypt
	
	addi $sp, $sp, -12
	sw $t3, 0($sp)
	sw $t4, 4($sp)
	sw $t5, 8($sp)
	# Contain method
	move $a0, $t4 # Cipherkey
	move $a1, $t0 # char we're looking for
	jal contains  # $v0 contains the index now.
	lw $t3, 0($sp)
	lw $t4, 4($sp)
	lw $t5, 8($sp)
	addi $sp, $sp, 12
	
	li $t0, -1
	beq $v0, $t0, char_not_contain # If the key does't contain this, it might be fail.
	
	move $t6, $v0
	li $t7, 2
	div $t6, $t7 # Divide by 2
	mfhi $t6 # Either 1 or 0 base on odd or even.
	beqz $t6, even_key
	
	odd_key:
		move $t6, $t4
		add $t6, $t6, $v0
		addi $t6, $t6, -1 # char of key 1 before index.
		lbu $t7, 0($t6)
		sb $t7, 0($t5)
		j continue_loop
	
	even_key:
		move $t6, $t4
		add $t6, $t6, $v0 # $t6 is now address at index.
		addi $t6, $t6, 1 # char of key 1 after index.
		lbu $t7, 0($t6)
		sb $t7, 0($t5)
		j continue_loop
	
	continue_loop:
		addi $t3, $t3, 1 # Increment text addresses.
		addi $t5, $t5, 1
		j encrypt_loop
	
	add_previous_key:
		addi $t6, $t6, -2 # Goes to one before the index
		lbu $t7, 0($t6)
		beqz $t7, failed_encrypt # If this is also nothing, then it fails, but this shouldn't happen.
		sb $t7, 0($t5)
		j continue_loop
		
	char_not_contain:
		lbu $t0, 0($t3)
		li $t7, ' '
		beq $t0, $t7, just_a_space
	erase_loop:
		sb $0, 0($t5)
		addi $t5, $t5, -1
		blt $t5, $a2, failed_encrypt
		j erase_loop
		
	just_a_space:
		sb $t0, 0($t5)
		j continue_loop
		
	done_encrypt:
		li $v0, 1
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		jr $ra
		
	failed_encrypt:
		li $v0, 0
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		jr $ra



.globl decipher_key_with_chosen_plaintext # char* plaintext, char* ciphertext, char* key
decipher_key_with_chosen_plaintext: 
	addi $sp, $sp, -4
	sw $ra, 0($sp) # Save return address
	
	jal to_upper # Since plaintext is already in $a0.

	move $t3, $a0 # Copy from parameter.
	move $t4, $a1
	move $t5, $a2
	move $t6, $a2 # Used for adding to end.
	
	decipher_loop:
		lbu $t0, 0($t3) # load plaintext bit
		beqz $t0, decipher_done_maybe # Exit statement.
		
		lbu $t1, 0($t4) # load ciphertext bit
		beqz $t1, decipher_fail # This means plaintext is larger than ciphertext.
		
		beq $t0, $t1, could_be_space # They should only be equal if they are both spaces.
		
		addi $sp, $sp, -16 # Save the temps real quick.
		sw $t3, 0($sp)
		sw $t4, 4($sp)
		sw $t5, 8($sp)
		sw $t6, 12($sp)
		
		move $a0, $t0
		move $a1, $t1
		move $a2, $t5
		jal pair_exists
		# output 1 in $v0 if exists, else 0
		
		lw $t3, 0($sp) # Reload temps real quick
		lw $t4, 4($sp)
		lw $t5, 8($sp)
		lw $t6, 12($sp)
		addi $sp, $sp, 16
		
		beqz $v0, add_to_predicted_key
	continue_decipher_loop:
		addi, $t3, $t3, 1 # Increment addresses.
		addi, $t4, $t4, 1
		j decipher_loop
		
	add_to_predicted_key:
		lbu $t0, 0($t3) # Reload those bits
		lbu $t1, 0($t4)
		
		sb $t0, 0($t6) # Add to predicted key.
		addi $t6, $t6, 1
		sb $t1, 0($t6)
		addi $t6, $t6, 1
		j continue_decipher_loop
		
	could_be_space:
		li $t7, ' '
		beq $t0, $t7, continue_decipher_loop
		j decipher_fail
		
	decipher_done_maybe:
		lbu $t1, 0($t4)
		beq $t0, $t1, decipher_done
		j decipher_fail # If they weren't equal, that means that the ciphertext was longer
		
	decipher_done:
	decipher_fail: # noticed that there isn't anything to return, no there is no way to tell if it failed or not.
		lw $ra, 0($sp)
		addi $sp, $sp, 4 # Get $ra back
		
		jr $ra
		
 jr $ra
