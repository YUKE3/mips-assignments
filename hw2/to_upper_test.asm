# Edit this file to write your tests. change data section if needed.
.data
 plaintext: .asciiz "Hi Mips"

.text
main:
 la $a0, plaintext
 jal to_upper
term:
 li $v0, 10
 syscall


.include "hw2.asm"
