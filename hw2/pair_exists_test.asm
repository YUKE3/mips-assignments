# Edit this file to write your tests. Change data section if needed.

.data
 key: .asciiz "I love Mips"

.text
main:
 li $a0, 71
 li $a1, 72
 la $a2, key
 jal pair_exists

term:
 li $v0, 10
 syscall


.include "hw2.asm"
