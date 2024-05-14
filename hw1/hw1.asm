################# Vincent Ke #################
################# YUKE #################
################# 113778667 #################

################# DO NOT CHANGE THE DATA SECTION #################


.data
arg1_addr: .word 0
arg2_addr: .word 0
num_args: .word 0
invalid_arg_msg: .asciiz "One of the arguments is invalid\n"
args_err_msg: .asciiz "Program requires exactly two arguments\n"
zero: .asciiz "Zero\n"
nan: .asciiz "NaN\n"
inf_pos: .asciiz "+Inf\n"
inf_neg: .asciiz "-Inf\n"
mantissa: .asciiz ""

.text
.globl hw_main
hw_main:
    sw $a0, num_args
    sw $a1, arg1_addr
    addi $t0, $a1, 2
    sw $t0, arg2_addr
    j start_coding_here

start_coding_here:

# Resets all register so that there are no junk values between runs.
li $a0, 0
li $a1, 0
li $a2, 0
li $a3, 0
li $t0, 0
li $t1, 0
li $t2, 0
li $t3, 0
li $t4, 0
li $t5, 0
li $t6, 0
li $t7, 0
li $t8, 0
li $t9, 0
li $s0, 0
li $s1, 0
li $s2, 0
li $s3, 0
li $s4, 0
li $s5, 0
li $s6, 0
li $s7, 0

# Checks if there is specifically two arguments.
    lw $t0, num_args
    li $t1, 2
    # $t0 = num_args, $t1 = 2, if num_args != 2, print args_err_msg
    bne $t0, $t1, print_args_err_msg

# Checks if there is NUL ending in the first argument and if there first element is not null.
    # $a0 = arg1_addr, $t0 = element in $a0
    lw $a0, arg1_addr # Loads arg1_addr into $a0
    lbu $t0, 0($a0)
    beqz $t0, print_invalid_arg_msg # If $a0[0] is null, print invalid arg msg
    lbu $t0, 1($a0)
    bnez $t0, print_invalid_arg_msg # If $a0[1] is not null, print invalid arg msg
    lw $a1, arg2_addr
    lbu $t0, 0($a1)
    beqz $t0, print_invalid_arg_msg # If $a1[0] is null, the second argument is empty.
    
# Checks the first argument is "D", "F", "L", "X"
    lbu $t0, 0($a0) # Loads in the first element.
    li $t1, 'D'
    bne $t1, $t0, check_F # If element != D, check F.
    j string_to_decimal # If argument is D
check_F:
    li $t1, 'F'
    bne $t1, $t0, check_L # If element != F, check L
    j hex_to_IEEE # If argument is F
check_L:
    li $t1, 'L'
    bne $t1, $t0, check_X # If element != L, check X
    j loot_verify # If argument is L
check_X:
    li $t1, 'X'
    bne $t1, $t0, print_invalid_arg_msg # If element != X, print invalid arg msg.
    j hex_to_decimal # If argument is X
    
# Part 2: String to Decimal   
string_to_decimal:
    lw $a0, arg2_addr # loads arg2 mem addr into $a0
    li $t1, '0'
    li $t2, '9'
check_string_is_decimal:
    lbu $t0, 0($a0) # load a bit into $t0
    beq $t0, $0, valid_string_to_decimal # When it reaches a NUL terminator.
    blt $t0, $t1, print_invalid_arg_msg # when $t0 < '0', ASCII out of range.
    bgt $t0, $t2, print_invalid_arg_msg # when $t0 > '9', ASCII out of range.
    addi $a0, $a0, 1 # Moves pointer forward.
    j check_string_is_decimal
valid_string_to_decimal:
    lw $a0, arg2_addr # loads arg2 mem addr into $a0 again.
    li $t1, 10 # loads 10 into $t1, thisis used for multiplication.
    li $t3, 0 # loads 0 into $t3, this is the decimal number.
build_decimal:
    lbu $t0, 0($a0)
    beq $t0, $0, print_decimal # reached the end of string.
    mult $t3, $t1 # decimal_result * 10
    mflo $t3 # Get the result.
    addi $t0, $t0, -48 # Offsets the string number by -48 (ASCII)
    add $t3, $t3, $t0 # Adds the current element to deciaml_result.
    addi $a0, $a0, 1 # Moves pointer forward.
    j build_decimal
print_decimal:
    li $v0, 1
    move $a0, $t3
    syscall # Print integer.
    j exit_program
    
# Part 3: Hex String to Decimal
hex_to_decimal:   
    lw $a0, arg2_addr # loads arg2 mem addr into $a0
    lbu $t0, 0($a0) # loads first bit
    li $t1, '0' # loads '0' into $t1
    bne $t0, $t1, part_three_invalid_arg_msg # If first element is not 0
    addi $a0, $a0, 1 # Move pointer to second element
    li $t1, 'x' # loads 'x' into $t1
    lbu $t0, 0($a0) # loads second element
    bne $t0, $t1, part_three_invalid_arg_msg # If second element is not x
    li $t3, 2 # $t3 use to count amount of valid character
    addi $a0, $a0, 1 # Move pointer to third element.
    li $t4, '0'
    li $t5, '9' # Immediates used for validation.
    li $t6, 'A'
    li $t7, 'F'
check_string_is_hex:
    lbu $t0, 0($a0) # loads element
    beq $t0, $0, finish_check_string_is_hex # Reached null terminator
    blt $t0, $t4, part_three_invalid_arg_msg # If $t0 < '0'
    bgt $t0, $t7, part_three_invalid_arg_msg # If $t0 > 'F'
    # We establish now $t0 is between '0' and 'F', there is still some ascii to remove.
    ble $t0, $t5, valid_hex_num # If $t0 <= '9'
    bge $t0, $t6, valid_hex_num # If $t0 >= 'A'
    j part_three_invalid_arg_msg
    # If the element is some ASCII between '9' and 'F' non inclusive. 
valid_hex_num:
    addi $t3, $t3, 1 # Adds 1 to amount of valid characters.
    addi $a0, $a0, 1 # Moves pointer forward 1.
    j check_string_is_hex
part_three_invalid_arg_msg : # For some reason program break if j print_invalid_arg_msg 
    li $v0, 4            # is used here, probably because its too far or something.
    la $a0, invalid_arg_msg
    syscall
    li $v0, 10 # Exit program call.
    syscall
finish_check_string_is_hex:
    li $t4, 3
    li $t5, 10
    blt $t3, $t4, part_three_invalid_arg_msg # If amount of character < 3
    bgtu $t3, $t5, part_three_invalid_arg_msg # If amount of character > 10
    li $t1, 10
    sub $t0, $t1, $t3 # $t0 = 10 - amount of character. (Gives the amount of 0000 is in binary)
    li $t7, '0'
    li $t6, 2147483648 # 2^31
    move $a0, $t7
    li $v0, 11 # Sets up syscall to print 0000
print_empty_binary_fours:
    beq $t0, $0, print_hex_start # Prints until there should be no more blanks.
    li $t7, 16
    divu $t6, $t7
    mflo $t6
    li $t7, 1
    sub $t0, $t0, $t7 # Reduces amount by 1.
    j print_empty_binary_fours
print_hex_start:
    lw $t7, arg2_addr # Loads arg2 mem addr again.
    addi $t7, $t7, 2 # Changes pointer to start of hex.
    li $s0, 0 # Loads 0 into $t9, decimal output.
    li $t9, 2 # Load two immediate into $t9
get_hex:
    lbu $t0, 0($t7) # loads element
    beqz $t0, finish_print_hex
    
    # $t2 $t3 $t4 $5 are binary.
    li $t2, 0
    li $t3, 0 # This implementation is really inefficient and ugly, but it's 2:05 AM and I can't really think.
    li $t4, 0
    li $t5, 0
    li $t1, '0'
    beq $t0, $t1, print_hex
    li $t5, 1
    li $t1, '1'
    beq $t0, $t1, print_hex
    li $t4, 1
    li $t5, 0
    li $t1, '2'
    beq $t0, $t1, print_hex
    li $t5, 1
    li $t1, '3'
    beq $t0, $t1, print_hex
    li $t3, 1
    li $t4, 0
    li $t5, 0
    li $t1, '4'
    beq $t0, $t1, print_hex
    li $t5, 1
    li $t1, '5'
    beq $t0, $t1, print_hex
    li $t4, 1
    li $t5, 0
    li $t1, '6'
    beq $t0, $t1, print_hex
    li $t5, 1
    li $t1, '7'
    beq $t0, $t1, print_hex
    li $t2, 1
    li $t3, 0
    li $t4, 0
    li $t5, 0
    li $t1, '8'
    beq $t0, $t1, print_hex
    li $t5, 1
    li $t1, '9'
    beq $t0, $t1, print_hex
    li $t5, 0
    li $t4, 1
    li $t1, 'A'
    beq $t0, $t1, print_hex
    li $t5, 1
    li $t1, 'B'
    beq $t0, $t1, print_hex
    li $t3, 1
    li $t4, 0
    li $t5, 0
    li $t1, 'C'
    beq $t0, $t1, print_hex
    li $t5, 1
    li $t1, 'D'
    beq $t0, $t1, print_hex
    li $t4, 1
    li $t5, 0
    li $t1, 'E'
    beq $t0, $t1, print_hex
    li $t5, 1
    li $t1, 'F'
    beq $t0, $t1, print_hex
print_hex:
    beqz $t2, print_hex2 # Branch used to skip addition if first binary is 0.
    addu $s0, $s0, $t6 # Add result to $s0
print_hex2:
    divu $t6, $t9 # Divide $t6 by 2
    mflo $t6
    beqz $t3, print_hex3 # Branch used to skip addition if second binary is 0.
    addu $s0, $s0, $t6
print_hex3:
    divu $t6, $t9 # Divide $t6 by 2
    mflo $t6
    beqz $t4, print_hex4 # Branch used to skip addition if third binary is 0.
    addu $s0, $s0, $t6
print_hex4:
    divu $t6, $t9 # Divide $t6 by 2
    mflo $t6
    beqz $t5, print_hex5 # Branch used to skip addition if fourth is 0.
    addu $s0, $s0, $t6
print_hex5:
    divu $t6, $t9 # Divide $t6 by 2
    mflo $t6

    addi $t7, $t7, 1 # Increment memory reference by 1.
    j get_hex
finish_print_hex: # Prints out the decimal output.
    li $v0, 36 # Print unsigned integer.
    move $a0, $s0
    syscall
    li $v0, 10 # Exit program call.
    syscall
    
# Part 4: Hex String to 32-bit Floating Point in IEEE 754
hex_to_IEEE:
    lw $a0, arg2_addr # Load arg2 address.
    li $9, 0 # length of argument.
    li $t4, '0'
    li $t5, '9' # Immediates used for validation.
    li $t6, 'A'
    li $t7, 'F'
check_string_is_hex_part_four: # Basically recycling code from part 3.
    lbu $t0, 0($a0) # loads element
    beq $t0, $0, finish_check_string_is_hex_part_four # Reached null terminator
    blt $t0, $t4, part_four_invalid_arg_msg # If $t0 < '0'
    bgt $t0, $t7, part_four_invalid_arg_msg # If $t0 > 'F'
    # We establish now $t0 is between '0' and 'F', there is still some ascii to remove.
    ble $t0, $t5, valid_hex_num_part_four # If $t0 <= '9'
    bge $t0, $t6, valid_hex_num_part_four # If $t0 >= 'A'
    j part_four_invalid_arg_msg
    # If the element is some ASCII between '9' and 'F' non inclusive.
part_four_invalid_arg_msg: # Just incase.
    li $v0, 4
    la $a0, invalid_arg_msg
    syscall
    li $v0, 10
    syscall
valid_hex_num_part_four:
    addi, $t9, $t9, 1 # Increment length or argument.
    addi, $a0, $a0, 1 # Increment address.
    j check_string_is_hex_part_four
finish_check_string_is_hex_part_four:
    li $t0, 8 # Checks if the length is == 8
    beq $t0, $t9, valid_hex_part_four
    j part_four_invalid_arg_msg
valid_hex_part_four:
    # Input is valid.
    lw $t0, arg2_addr # Loads address
    li $t5, '0' # For comparison
    lbu $t2, 0($t0) # Loads first bit
# Checks which character it starts with.
    li $t3, '0'
    beq $t2, $t3, start_check_zero # If first element is '0'
    li $t3, '8'
    beq $t2, $t3, start_check_zero # If first element is '8'
    li $t3, 'F'
    beq $t2, $t3, start_check_F # If first element is F.
    li $t3, '7'
    beq $t2, $t3, start_check_seven # If first element is seven.
    j not_special
# Check for 00000000 or 80000000
start_check_zero: # When first element is either '0' or '8'
    addi $t0, $t0, 1
check_zero:
    lbu $t2, 0($t0) # Loads bit.
    beq $t2, $0, print_zero
    bne $t2, $t5, not_special # If $t2 is not zero.
    addi $t0, $t0, 1 # Increment index.
    j check_zero
print_zero:
    la $a0, zero
    li $v0, 4
    syscall
    li $v0, 10
    syscall
# Check for FF800000000 or FF800000000+
start_check_F:
    addi $t0, $t0, 1 # Second character.
    lbu $t1, 0($t0)
    # $t3 is still 'F'
    bne $t1, $t3, not_special # If second character is not 'F'
    addi $t0, $t0, 1 # Third character.
    lbu $t1, 0($t0)
    li $t3, '8'
    blt $t1, $t3, not_special # If the third character is less than '8'
    bgt $t1, $t3, print_nan # If the third character is bigger than '8'
    addi $t0, $t0, 1 # Fourth character.
F_nan_or_inf:
    lbu $t1, 0($t0) # load element.
    beqz $t1, print_inf_neg # It run if all element is zero, therefore inf_neg.
    li $t3, '0'
    bne $t1, $t3, print_nan # If element is not 0, then it means that it has to be nan.
    addi $t0, $t0, 1
    j F_nan_or_inf
print_inf_neg:
    la $a0, inf_neg
    li $v0, 4
    syscall
    li $v0, 10
    syscall
print_nan:
    la $a0, nan
    li $v0, 4
    syscall
    li $v0, 10
    syscall
print_inf_pos:
    la $a0, inf_pos
    li $v0, 4
    syscall
    li $v0, 10
    syscall
# Check for 7F800000 or 7FFFFFFFF
start_check_seven:
    addi $t0, $t0, 1 # Second character.
    lbu $t1, 0($t0)
    li $t3, 'F'
    bne $t1, $t3, not_special # If the second character is not 'F'
    addi $t0, $t0, 1 # Third character.
    lbu $t1, 0($t0)
    li $t3, '8'
    blt $t1, $t3, not_special # If the third character is less than '8'
    bgt $t1, $t3, print_nan # If the third character is bigger than '8'
    addi $t0, $t0, 1 # Fourth character.
seven_nan_or_inf: # Recycled code.
    lbu $t1, 0($t0) # load element.
    beqz $t1, print_inf_pos # It run if all element is zero, therefore inf_pos.
    li $t3, '0'
    bne $t1, $t3, print_nan # If element is not 0, then it means that it has to be nan.
    addi $t0, $t0, 1
    j seven_nan_or_inf
# Not special floating-point number.
not_special:
    lw $t7, arg2_addr
    li $t6, 256 # 2^8
    li $s0, 0 # For exponent.
    la $s1, mantissa # For mantissa.
    li $s2, 0 # For index.
    li $t9, 2
    li $s4, 8 # For checking if index is after 8.
get_hex_part_four: # Recycled code again.
    lbu $t0, 0($t7) # loads element
    beqz $t0, finish_print_hex_part_four
    
    # $t2 $t3 $t4 $5 are binary.
    li $t2, 0
    li $t3, 0 # This implementation is really inefficient and ugly, but it's 2:05 AM and I can't really think.
    li $t4, 0
    li $t5, 0
    li $t1, '0'
    beq $t0, $t1, print_hex_part_four
    li $t5, 1
    li $t1, '1'
    beq $t0, $t1, print_hex_part_four
    li $t4, 1
    li $t5, 0
    li $t1, '2'
    beq $t0, $t1, print_hex_part_four
    li $t5, 1
    li $t1, '3'
    beq $t0, $t1, print_hex_part_four
    li $t3, 1
    li $t4, 0
    li $t5, 0
    li $t1, '4'
    beq $t0, $t1, print_hex_part_four
    li $t5, 1
    li $t1, '5'
    beq $t0, $t1, print_hex_part_four
    li $t4, 1
    li $t5, 0
    li $t1, '6'
    beq $t0, $t1, print_hex_part_four
    li $t5, 1
    li $t1, '7'
    beq $t0, $t1, print_hex_part_four
    li $t2, 1
    li $t3, 0
    li $t4, 0
    li $t5, 0
    li $t1, '8'
    beq $t0, $t1, print_hex_part_four
    li $t5, 1
    li $t1, '9'
    beq $t0, $t1, print_hex_part_four
    li $t5, 0
    li $t4, 1
    li $t1, 'A'
    beq $t0, $t1, print_hex_part_four
    li $t5, 1
    li $t1, 'B'
    beq $t0, $t1, print_hex_part_four
    li $t3, 1
    li $t4, 0
    li $t5, 0
    li $t1, 'C'
    beq $t0, $t1, print_hex_part_four
    li $t5, 1
    li $t1, 'D'
    beq $t0, $t1, print_hex_part_four
    li $t4, 1
    li $t5, 0
    li $t1, 'E'
    beq $t0, $t1, print_hex_part_four
    li $t5, 1
    li $t1, 'F'
    beq $t0, $t1, print_hex_part_four
print_hex_part_four:
    bgt $s2, $s4, hex_build_mantissa # Go build mantissa.
    beqz $t2, print_hex2_part_four # Branch used to skip addition if first binary is 0.
    beqz $s2, sign_bit_one # If this is index 0, set sign bit to 1, (only when binary is not 0)
    bgt $s2, $s4, print_hex2_part_four # If index is greater than 8, it's not in the exponenent range anymore.
    addu $s0, $s0, $t6 # Add result to $s0
    j print_hex2_part_four
hex_build_mantissa:
    li $s3, 10
    blt $s2, $s3, skip_first_element # If first run.
    addi $s1, $s1, 1 # Move forward mantissa cursor.1
    addi $t2, $t2, 48 # Offset to make char
    sb $t2, 0($s1) # Saves byte.
skip_first_element:
    addi $s1, $s1, 1 # Move forward mantissa cursor.2
    addi $t3, $t3, 48 # Offset to make char
    sb $t3, 0($s1) # Saves byte.
    addi $s1, $s1, 1 # Move forward mantissa cursor.3
    addi $t4, $t4, 48 # Offset to make char
    sb $t4, 0($s1) # Saves byte.
    addi $s1, $s1, 1 # Move forward mantissa cursor.4
    addi $t5, $t5, 48 # Offset to make char
    sb $t5, 0($s1) # Saves byte.
    addi $t7, $t7, 1 # Increment address
    addi $s2, $s2, 10 # Make $s2 pretty big.
    j get_hex_part_four
sign_bit_one:
    li $s3, '-'
    sb $s3, 0($s1) # Sets sign bit to 1.
    addi $s1, $s1, 1
print_hex2_part_four:
    addi $s2, $s2, 1 # Increment index.
    bgt $s2, $s4, hex_build_mantissa # Go build mantissa.
    divu $t6, $t9 # Divide $t6 by 2
    mflo $t6
    beqz $t3, print_hex3_part_four # Branch used to skip addition if second binary is 0.
    bgt $s2, $s4, print_hex3_part_four # Branch if not exponenent
    addu $s0, $s0, $t6
print_hex3_part_four:
    addi $s2, $s2, 1 # Increment index.
    divu $t6, $t9 # Divide $t6 by 2
    mflo $t6
    beqz $t4, print_hex4_part_four # Branch used to skip addition if third binary is 0.
    bgt $s2, $s4, print_hex3_part_four # Branch if not exponenent
    addu $s0, $s0, $t6
print_hex4_part_four:
    addi $s2, $s2, 1 # Increment index.
    divu $t6, $t9 # Divide $t6 by 2
    mflo $t6
    beqz $t5, print_hex5_part_four # Branch used to skip addition if fourth is 0.
    bgt $s2, $s4, print_hex3_part_four # Branch if not exponenent
    addu $s0, $s0, $t6
print_hex5_part_four:
    divu $t6, $t9 # Divide $t6 by 2
    mflo $t6
    addi $t7, $t7, 1 # Increment address
    addi $s2, $s2, 1 # Increment index.
    li $s3, 4 # For comparison
    beq $s2, $s3, build_mantissa_start
    j get_hex_part_four
build_mantissa_start: # build '1.' on the mantissa.
    li $s3, '1'
    sb $s3, 0($s1) # Set 1 to second element of mantissa.
    addi $s1, $s1, 1
    li $s3, '.'
    sb $s3, 0($s1) # Set . to third element of manitssa. 
    j get_hex_part_four
finish_print_hex_part_four:
    addi $s1, $s1, 1
    sb $0, 0($s1) # Add null terminator to mantissa
    la $a1, mantissa # Store base of mantissa in $a1
    li $t1, 127
    subu $s0, $s0, $t1 
    move $a0, $s0
    li $v0, 10 # Exit program call.
    syscall
    
# Part 5: Verify Hand in The Loot Card Game
loot_verify:
    lw $t0, arg2_addr # Loads arg2 address
    li $t1, 0 # Use as index.
    li $t2, 11 # There is only index 0-11 in the string.
    li $t9, 0 # Total point counter.
verify_hand:
    bgt $t1, $t2, hand_ok # Goes through the whole hand without any issues.
    lbu $t3, 0($t0) # Gets element, this element should be a number.
    beqz $t3, print_invalid_arg_msg # the element is null, which shouldn't happen in range 0-11
    li $t4, '0'
    blt $t3, $t4, print_invalid_arg_msg
    li $t4, '9'
    bgt $t3, $t4, print_invalid_arg_msg
    addi $t0, $t0, 1
    lbu $t5, 0($t0) # Gets element, this element should either be 'M' or 'P'
    beqz $t5, print_invalid_arg_msg # the element is null again.
    li $t4, 48
    sub $t3, $t3, $t4
    li $t4, 'M'
    beq $t5, $t4, merchant_ship
    li $t4, 'P'
    beq $t5, $t4, pirate_ship
    j print_invalid_arg_msg # If its not 'M' or 'P'
merchant_ship:
    add $t9, $t9, $t3 # adds the merchant ship value.
    addi $t0, $t0, 1 # increment address
    addi $t1, $t1, 2 # increment counter by 2
    j verify_hand
pirate_ship:
    sub $t9, $t9, $t3 # Subtracts pirate ship value.
    addi $t0, $t0, 1 # increment address
    addi $t1, $t1, 2 # increment counter by 2
    j verify_hand
hand_ok:
    move $a0, $t9
    li $v0, 1
    syscall
    li $v0, 10
    syscall
    
print_invalid_arg_msg:
    li $v0, 4
    la $a0, invalid_arg_msg
    syscall
    j exit_program
    
print_args_err_msg:
    li $v0, 4
    la $a0, args_err_msg
    syscall
    j exit_program
    
exit_program:
    li $v0, 10
    syscall

