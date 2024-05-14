# Edit this file to write your tests. Change data section if needed.

.data
 plaintext: .asciiz "Random test data"

.text
main:
 la $a0, plaintext
 li $a1, 65
 jal contains
term:
 li $v0, 10
 syscall


.include "hw2.asm"
