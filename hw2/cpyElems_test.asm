# Edit this file to write your tests. Change data section if needed.

.data
 src: .asciiz "mips"
 indx: .word 2
 dest: .space 1

.text
main:
 la $a0, src
 lw $a1, indx
 la $a2, dest
 jal cpyElems
term:
 li $v0, 10
 syscall


.include "hw2.asm"
