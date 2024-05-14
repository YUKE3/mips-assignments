# Edit this file to write your tests. Change data section if needed.

.data
 bound: .word 20

.text
main:
 lw $a0, bound
 jal getRandomInt
term:
 li $v0, 10
 syscall


.include "hw2.asm"
