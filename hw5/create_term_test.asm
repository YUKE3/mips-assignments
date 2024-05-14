############################ CHANGE THIS FILE AS YOU DEEM FIT ############################
.data
coeff: .word 7
exp: .word 2

.text
main:
	  lw $a0, coeff
    lw $a1, exp
  	jal create_term
  	# write test code

exit:
	li $v0, 10
	syscall
.include "hw5.asm"
