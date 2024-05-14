.data
args: .asciiz "D" "1234"
n: .word 2

.text
main:
 lw $a0, n
 la $a1, args
 j hw_main

.include "hw1.asm"
