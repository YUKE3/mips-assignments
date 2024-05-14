######### Vincent Ke	 ##########
######### 113778667	 ##########
######### YUKE		 ##########

.text
.globl initialize

# int initialize(char* filename, Buffer* buffer)
# Uses $t0-7, $a0-3, all of which is not perserved
initialize:
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	move $t7, $a1 # Buffer address

	# Creating file descriptor. ($a0 is already set to filename)
	create_file_descriptor:
		li $a1, 0 # Code for read mode.
		li $v0, 13 # Syscall code
		syscall
		# $v0 is now file descriptor.
		bltz $v0, initialize_failed # Checks if creating file descriptor failed to initialize.
	
	first_line:
		move $a0, $v0 # $a0 = file descriptor.
		move $a1, $t7 # $a1 = Buffer address.
		li $a2, 1     # $a2 = Read only one character.
		li $v0, 14    # Syscall code.
		syscall
		
		lw $t0, 0($a1) # word that was read.
		# Checks if that byte was in range.
		li $t1, '1'
		blt $t0, $t1, initialize_failed
		li $t1, '9'
		bgt $t0, $t1, initialize_failed
		
		addi $t0, $t0, -48 # Convert to real number
		sw $t0, 0($a1)
		# $a0 is already the file descriptor.
		#move $a1, 8($sp) # loads the buffer address again MIGHT BE UNNECESSARY
		jal check_newline
		
		# After this call/section:
		# $a0 = file descriptor, should be on second line now.
		# $a1 = address of buffer, contains the first line.
		# $a2 = 1
		# $t7 = buffer address original.
		
	second_line:
		# other parameters are already set.
		addi $a1, $a1, 4 # Increment the place to store the byte. (4 because of word)
		li $v0, 14
		syscall
		
		lw $t0, 0($a1) # Byte that was read.
		# Checks if that byte was in range.
		li $t1, '1'
		blt $t0, $t1, initialize_failed
		li $t1, '9'
		bgt $t0, $t1, initialize_failed
		
		addi $t0, $t0, -48 # Convert to real number
		sw $t0, 0($a1)
		
		jal check_newline
		
		# The code SHOULD work until this point.
		
		# j initialize_complete
		
		# After this call/section:
		# $a0 = file descriptor, next character will be the first of the array.
		# $a1 = address of buffer 2
		# $a2 = 1, since this should have never been changed.
		# $t7 = buffer address.

	# Rewrite matrix bullshit
	build_matrix_start:
		move $t4, $t7 # Loads base buffer address into $t4.
		lw $t5, 0($t4) # Loads the rows
		lw $t6, 4($t4) # Loads the columns
		
		# Current registers that are useful:
		# $t5 = rows
		# $t6 = col
		# $a0 = file descriptor
		# $a1 = address of buffer before 
		
		addi $a1, $a1, 4 # Goes to next word address location.
		matrix_row_loop:
		move $t4, $t6 # Copies col number into $t4 as counter.
			matrix_col_loop:
			beqz $t4, matrix_col_loop_done
			
			sw $0, 0($a1) # Cleans $a1 just incase.
			# Read one character:
			# $a0 is already the file descriptor.
			# $a2 should be 1
			# $a1 should be fine, since we are replacing this later anyways.
			li $v0, 14 # Read code
			syscall
			
			lw $t0, 0($a1) # Loads what was read.
			
			# Checks if in range.
			li $t1, '0'
			blt $t0, $t1, initialize_failed
			li $t1, '9'
			bgt $t0, $t1, initialize_failed
			
			addi $t0, $t0, -48 # Convert to real number.
			sw $t0, 0($a1)
			
			addi $a1, $a1, 4
			addi $t4, $t4, -1 # Decrease index.
			j matrix_col_loop
			matrix_col_loop_done:
		
		addi $t5, $t5, -1
		# Here instead of start because when this hits 0, it shouldn't run newline check.
		beqz $t5, matrix_row_loop_done
		
		# Here we need to check line ending again.
		jal check_newline # should be fine since we dont use $t0-$t2 outside of col_loop
		
		j matrix_row_loop
		matrix_row_loop_done:
		
		# At this point:
		# $a0 = file descriptor, should be at the end of file.
		# $a1 = pointing to the last thing it read
		# $a2 = probably still 1.
		# Everything probably doesn't matter anymore.
		
		matrix_final_check:
			lw $t0, 0($a1) # Save the word here.
			sw $0, 0($a1) # Clean just in case.
			li $v0, 14 # Read code
			syscall
			
			lw $t1, 0($a1)
			li $t2, 10
			beq $t1, $t2, matrix_final_check_done # If it is UNIX new line
			li $t2, 13
			beq $t1, $t2, matrix_final_check_window # If it is windows new line
			
			beqz $v0, matrix_final_check_done # If it is end of file.
			
			j initialize_failed # If it is random stuff.
			
			matrix_final_check_window:
				li $v0, 14
				sw $0, 0($a1) # Clean address
				syscall
				lw $t1, 0($a1)
				li $t2, 10
				beq $t1, $t2, matrix_final_check_done
				j initialize_failed
			
			matrix_final_check_done:
				#FINAL FINAL check
				li $v0, 14
				syscall
				bnez $v0, initialize_failed # Since this HAS to be end of file.
				sw $t0, 0($a1) # Reloads $a1
				j initialize_complete
			
		
		sw $t0, 0($a1) # Place what was originally in $a1 back.
		
		beqz $v0, initialize_complete # It is actually end of file.
		
		j initialize_failed # If it gets here it failed again oof.
	
	
	# Uses $t0, $t1, $t2, $a0, $a1, $a2
	check_newline:
		# $a0 and $a1 is already set.
		lw $t0, 0($a1) # Saves the first byte of this buffer, using this as temp buffer.
		sw $0, 0($a1)
		li $a2, 1 # Read one character.
		li $v0, 14 # Read code.
		syscall
		
		lw $t1, 0($a1) # Read byte.
		
		li $t2, 13 # Checks if this character is \r / carriage return
		beq $t1, $t2, window_stupid # Since window also adds in \n after this.
		
		li $t2, 10 # Check if this character is \n / UNIX return
		beq $t1, $t2, jump_back
		
		j initialize_failed
		
		window_stupid: # All my homies hate windows.
			li $v0, 14
			syscall
			lw $t1, 0($a1)
			li $t2, 10
			beq $t1, $t2, jump_back
			j initialize_failed
		
		jump_back:
			sw $t0, 0($a1) # Saves byte back into temp buffer.
			jr $ra

	initialize_failed:
		li $v0, 16
		syscall # Close file descriptor
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		move $a1, $t7 # Somehow $t7 is still not used, nice.
		
		li $t0, 81
		erase_loop:
			beqz $t0, end_erase_loop
			sw $0, 0($a1)
			addi $a1, $a1, 4
			addi $t0, $t0, -1
			j erase_loop
		end_erase_loop:
		addi $a1, $a1, -324
		li $v0, -1 # Load error code
		jr $ra
		
	initialize_complete:
		li $v0, 16
		syscall # Close file descriptor
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		li $v0, 1 # Load success code
		jr $ra
	

.globl write_file
# void initialize(char* filename, Buffer* buffer)
write_file:
	addi $sp, $sp, -8
	sw $ra, 0($sp) # Saves return address.
	# $a0 = filename
	# $a1 = buffer
	move $t7, $a1 # Put buffer address in $t7.
	li $a1, 1 # Load writing code for file descriptor
	li $v0, 13
	syscall

	# File descriptor in $v0.
	move $a0, $v0 # moves file descriptor to $a0.
	move $a1, $t7 # moves input buffer back into $a1
	li $a2, 1 # All write syscall should only write one thing.
	
	# Syscall should directly write now as long as we define $v0 = 15.
	
	write_first_character: #ROW
		lw $t0, 0($a1)
		sw $t0, 4($sp) # Saves the first element, since I'll be using this as placeholder to keep new line.
		addi $t0, $t0, 48 # Converts first element into string. (Why do we even need to convert it to number in the first place)
		sw $t0, 0($a1)
	
		li $v0, 15
		syscall # This writes first row into file.
	
		li $t0, 10 # UNIX newline character.
		sw $t0, 0($a1) # Loads the new line charcter into the start of buffer address.
	
		jal write_new_line
		
	write_second_character: #COL
		addi $a1, $a1, 4
		lw $t0, 0($a1)
		addi $t0, $t0, 48 # Convert to string
		sw $t0, 0($a1)
		
		li $v0, 15
		syscall
		
		addi $t0, $t0, -48
		sw $t0, 0($a1) # Saves it back into buffer.
		
		jal write_new_line
		
	# At this point:
	# DO NOT CHANGE:
	# $t7 = base of buffer.
	# $a2 = 1.
	# NOT PERSERVED:
	# $t6, $v0
	# CURRENTLY USING:
	# $a0 = file descriptor, shouldn't touch this from code, only change in syscall
	# $a1 = current points to second character, next increment by 4 = start of matrix
	
	write_matrix:
		lw $t0, 4($sp) # Rows
		lw $t1, 4($t7) # Columns
		
		write_matrix_outer_loop:
			beqz $t0, write_matrix_outer_loop_done # Went through all rows.
			move $t2, $t1 # Resets counter for columns.
			write_matrix_inner_loop:
				beqz $t2, write_matrix_inner_loop_done # Went through all col.
				
				addi $a1, $a1, 4 # Increment to next word.
				lw $t4, 0($a1)
				addi $t4, $t4, 48 # Convert to string.
				sw $t4, 0($a1)
				li $v0, 15
				syscall # Writes to file.
				addi $t4, $t4, -48 # Convert back to real number.
				sw $t4, 0($a1)
				
				addi $t2, $t2, -1
				j write_matrix_inner_loop
			write_matrix_inner_loop_done:
			
			jal write_new_line # Writes line ending.
			
			addi $t0, $t0, -1
			j write_matrix_outer_loop
		write_matrix_outer_loop_done:
			j write_file_complete
	
	# Doesn't change $a1, changes $t6, assumes $t7 is base of buffer.
	write_new_line:
		move $t6, $a1 # Sets $t6 to $a1
		move $a1, $t7 # Set $a1 to base of buffer / new line character address.
		li $v0, 15
		syscall # Writes new line.
		move $a1, $t6 # Sets $a1 to $t6, its original value.
		jr $ra


	write_file_complete:
		lw $t0, 4($sp) # loads the old first number.
		sw $t0, 0($t7) # Saves it back in place of the line ending.
		li $v0, 16
		syscall # closes file descriptor.
		lw $ra, 0($sp)
		addi $sp, $sp, 8
		jr $ra

.globl rotate_clkws_90
# void rotate_clokws_90(Buffer* buffer, char* filename)
rotate_clkws_90:
	# Implementation idea:
	# Write to a out file first, writing a 90 degree rotation,
	# Then read the file to reload the buffer.
	# $a0 = buffer address ( flipped for some reason)
	# $a1 = filename

	addi $sp, $sp, -8
	sw $ra, 0($sp) # Saves return address.
	sw $a1, 4($sp) # Saves the filename.
	
	rotate_create_file_descriptor:
		move $t7, $a0 # Copies the memory address, since it is in the way.
		move $a0, $a1 # filename to $a0
		li $a1, 1 # Writing mode
		li $v0, 13
		syscall
		# $v0 now contains file descriptor.
		
		move $a0, $v0 # Moves file descriptor to its rightful place.
		
		# At this point:
		# $a0 = file descriptor
		# $t7 = buffer memory address base.
		
	rotate_write_first:
		move $a1, $t7 # Memory address into $a1
		addi $a1, $a1, 4 # Increment memory address by 4, next word
		lw $t0, 0($a1)
		addi $t0, $t0, 48 # Convert to string
		sw $t0, 0($a1)
		li $v0, 15 # Write syscall
		li $a2, 1 # Writes only one character
		syscall
		addi $t0, $t0, -48 # Back to number
		sw $t0, 0($a1)
		
		# At this point:
		# DO NOT CHANGE:
		# $a0 = file descriptor
		# $a2 = 1
		# $t7 = buffer memory address base.
		# CAN BE CHANGED:
		# $a1 = memory address at word 2 (4)
		# $t0, can be changed freely.
		
		jal rotate_write_new_line
		
	rotate_write_second:
		addi $a1, $a1, -4 #  Decremement memory address by 4, back to base address
		lw $t0, 0($a1)
		addi $t0, $t0, 48 # Convert to string
		sw $t0, 0($a1)
		li $v0, 15
		syscall # Writes second character (ROW)
		addi $t0, $t0, -48 # Back to number
		sw $t0, 0($a1)
		
		jal rotate_write_new_line
		
		## Code works fine until this point!
		
	rotate_write_matrix:
		lw $t3, 0($a1) # The row ( as in the original sense)
		lw $t4, 4($a1) # The column
		addi $a1, $a1, 8 # Now this is at index 0 of matrix.
		
		li $t5, 0 # index for outer loop.
		rotate_outer_loop:
			beq $t5, $t4, rotate_outer_loop_end # Branch if this iterated column times.
			
			mult $t4, $t3
			mflo $t6
			add $t6, $t6, $t5 # (row * col) + index - column
			sub $t6, $t6, $t4
			li $t0, 4
			mult $t6, $t0 # Mulitply by 4 because of words.
			mflo $t6
			rotate_inner_loop:
				bltz $t6, rotate_inner_loop_end
				
				# $t6 should be the index of the element we want.
				add $a1, $a1, $t6
				lw $t0, 0($a1)
				addi $t0, $t0, 48 # Convert to string
				sw $t0, 0($a1)
				li $v0, 15
				syscall
				addi $t0, $t0, -48
				sw $t0, 0($a1)
				sub $a1, $a1, $t6 # return to matrix index 0.
				li $t0, 4
				mult $t4, $t0 # Again multiply by 4 because words.
				mflo $t0 
				sub $t6, $t6, $t0
				
				j rotate_inner_loop
			rotate_inner_loop_end:
			jal rotate_write_new_line
			addi $t5, $t5, 1 # Increment index.
			j rotate_outer_loop
		rotate_outer_loop_end:
		
		# The file should have a 90 degree rotate now!
		li $v0, 16
		syscall # Close the file writer we have open.
		
		move $a1, $t7 # Should be buffer original memory address.
		lw $a0, 4($sp) # Filename address.
		jal initialize
		
		j rotate_finish
		
	# $a1 can be any address, assumes $a2 = 1 and $a0 = file descriptor, $t0 and $t1 is used.
	rotate_write_new_line: # New implementation that doesn't require a saved first element. Too lazy to update the other one.
		li $t0, 10 # UNIX newline
		lw $t1, 0($a1) # Grab the thing at the current memory address.
		sw $t0, 0($a1) # Saves newline into current memory address.
		li $v0, 15
		syscall
		sw $t1, 0($a1) # Put the thing back.
		jr $ra
		
	rotate_finish:
		lw $ra, 0($sp)
		addi $sp, $sp, 8
		jr $ra

.globl rotate_clkws_180
# void rotate_clkws_180(Buffer* buffer, char* filename)
rotate_clkws_180:
	addi $sp, $sp, -12
	sw $ra, 0($sp) # Return address
	sw $a0, 4($sp) # Buffer
	sw $a1, 8($sp) # filename
	
	jal rotate_clkws_90
	
	lw $a0, 4($sp) # Resets parameters since they aren't perserved in callee.
	lw $a1, 8($sp) 
	
	jal rotate_clkws_90
	
	lw $ra, 0($sp) # Reverts return address
	addi $sp, $sp, 12
	jr $ra

.globl rotate_clkws_270
# void rotate_clkws_270(Buffer* buffer, char* filename)
rotate_clkws_270:
	addi $sp, $sp, -12
	sw $ra, 0($sp) # Return address
	sw $a0, 4($sp) # Buffer
	sw $a1, 8($sp) # filename
	
	jal rotate_clkws_180
	
	lw $a0, 4($sp) # Resets parameters since they aren't perserved in callee.
	lw $a1, 8($sp) 
	
	jal rotate_clkws_90
	
	lw $ra, 0($sp) # Reverts return address
	addi $sp, $sp, 12
	jr $ra

.globl mirror
# void mirror(Buffer* buffer, char* filename)
mirror:
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	# Creating file descriptor
	move $t7, $a0 # Copies buffer base address to $t7.
	move $a0, $a1 # Copies file name into $a0.
	li $a1, 1 # For writing
	li $v0, 13
	syscall
	# $v0 should be file descriptor now.
	move $a0, $v0
	
	mirror_first_letter:
		lw $t0, 0($t7) # word that base address of buffer
		addi $t0, $t0, 48 # Convert to string
		sw $t0, 0($t7)
		move $a1, $t7
		li $a2, 1 # Write 1 character.
		li $v0, 15
		syscall # Writes the first letter.
		addi $t0, $t0, -48
		sw $t0, 0($t7)
		move $t3, $t0
		
		# $t0, $t1 is used.
		jal rotate_write_new_line
		
	mirror_second_letter:
		addi $a1, $a1, 4
		lw $t0, 0($a1)
		addi $t0, $t0, 48 # convert to string
		sw $t0, 0($a1)
		li $v0, 15
		syscall # writes second letter
		addi $t0, $t0, -48
		sw $t0, 0($a1)
		move $t4, $t0
		
		jal rotate_write_new_line
		
		# At this point:
		# $a0 = file descriptor
		# $a1 = memory address on second letter
		# $a2 = 1
		# $t0 and $t1 will be changed everytime new line is wrote.
		# $t3 = rows
		# $t4 = columns
		
	mirror_matrix:
		li $t5, 0 # Counter for outer loop.
		addi $a1, $a1, 4
		mirror_matrix_outer_loop:
			beq $t5, $t3, mirror_matrix_outer_loop_end # If outer index == rows
			move $t6, $t4 # Counter for inner = col
			addi $t6, $t6, -1
			mirror_matrix_inner_loop:
				bltz $t6, mirror_matrix_inner_loop_end
			
				li $t1, 4
				mult $t6, $t1
				mflo $t1 # Amount to increment address.
				add $a1, $a1, $t1 # Increment address
				
				lw $t0, 0($a1)
				addi $t0, $t0, 48 # convert to string
				sw $t0, 0($a1)
				
				li $v0, 15
				syscall # Write
				
				addi $t0, $t0, -48
				sw $t0, 0($a1) # Revert
				
				sub $a1, $a1, $t1
			
				addi $t6, $t6, -1
				j mirror_matrix_inner_loop
			mirror_matrix_inner_loop_end:
		
			jal rotate_write_new_line
			
			li $t0, 4
			mult $t4, $t0
			mflo $t0
			add $a1, $a1, $t0 # Increment to next row.
		
			addi $t5, $t5, 1
			j mirror_matrix_outer_loop
		mirror_matrix_outer_loop_end:
			j mirror_done
		
	mirror_done:	
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		jr $ra

.globl duplicate
# (int, int) duplicate(Buffer* buffer)
duplicate:
	lw $t0, 0($a0) # Row
	lw $t1, 4($a0) # Col
	
	addi $a0, $a0, 8 # Moves address to start of matrix.
	
	li $v0, -1 # Starts assuming there is not duplicate.
	li $v1, 10 # Ten is out of bounce for the problem, so it serves well for upper bound.
	li $t3, 0 # Counter for outer loop.
	duplicate_outer_loop:
		beq $t3, $t0, duplicate_outer_loop_end
		li $t4, 0 # Counter for inner loop.
		add $t4, $t4, $t3 # Inner loop counter = outerloop counter.
		duplicate_inner_loop:
			beq $t4, $t0, duplicate_inner_loop_end
			beq $t4, $t3, duplicate_skip_check
			
			### This part right here compares every row to all row except itself
			# O(n^2) go brrr
			
			li $t5, 0 # index for the inner-inner loop, O(n^3)??!?
			duplicate_compare_loop:
				beq $t5, $t1, duplicate_found # If this equals, that means it was equal.
				
				# 4 ((Outer index * col) + compare index)
				li $t7, 4
				mult $t3, $t1
				mflo $t6
				add $t6, $t6, $t5
				mult $t6, $t7
				mflo $t6
				
				add $a0, $a0, $t6
				lw $a1, 0($a0) # Gets the word at this index. Putting it in $a1 since I ran out of room.
				sub $a0, $a0, $t6
				
				mult $t4, $t1
				mflo $t6
				add $t6, $t6, $t5
				mult $t6, $t7
				mflo $t6
				
				add $a0, $a0, $t6
				lw $a2, 0($a0) # Gets the word at this index. Putting it in $a2 since I ran out of room.
				sub $a0, $a0, $t6
				
				bne $a1, $a2, duplicate_compare_loop_end # If not equal, end immediately.
				
				addi $t5, $t5, 1
				j duplicate_compare_loop
			duplicate_compare_loop_end:
			duplicate_skip_check:
			addi $t4, $t4, 1
			j duplicate_inner_loop
		duplicate_inner_loop_end:
		addi $t3, $t3, 1
		j duplicate_outer_loop
	duplicate_outer_loop_end:
		li $t0, -1
		beq $t0, $v0, no_duplicate_found # If $v0 is still -1, then there is no duplicate.
		addi $v1, $v1, 1 # Increment because my indexes are 0-(length-1)
		jr $ra
	
	no_duplicate_found:
		li $v1, 0
		jr $ra
	
	duplicate_found:
		li $v0, 1 # Return code.
		blt $t4, $v1, new_lower_duplicate
		j duplicate_compare_loop_end
		new_lower_duplicate: # A new lower duplicate is found.
			move $v1, $t4
			j duplicate_compare_loop_end
