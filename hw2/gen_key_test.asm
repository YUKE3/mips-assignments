# Edit this file to write your tests. Change data section if needed.

.data
 alphabet: .asciiz "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
 key: .space 26

.text
main:
 la $a0, alphabet
 la $a1, key
 jal genKey
term:
 li $v0, 10
 syscall


.include "hw2.asm"
