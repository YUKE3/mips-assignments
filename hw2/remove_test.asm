# Edit this file to write your tests. change data section if needed.

.data
 plaintext: .asciiz "Hi Mips"
 pos: .word 0

.text
main:
 la $a0, plaintext
 lw $a1, pos
 jal remove
term:
 li $v0, 10
 syscall


.include "hw2.asm"
