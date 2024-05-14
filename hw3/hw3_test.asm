# This is a test file. Use this file to run the functions in hw3.asm
#
# Change data section as you deem fit.
# Change filepath if necessary.
.data
Filename: .asciiz "inputs/input1.txt"
OutFile: .asciiz "out.txt"
Buffer:
    .word 0	# num rows
    .word 0	# num columns
    # matrix
    .word 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0


.text
main:
 la $a0, Filename
 la $a1, Buffer
 jal initialize

 # write additional test code as you deem fit.

 li $v0, 10
 syscall

.include "hw3.asm"
