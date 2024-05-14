############################ CHANGE THIS FILE AS YOU DEEM FIT ############################
.data
Person_prop: .asciiz "NAME"
Relation_prop: .asciiz "FRIEND"

Name1: .asciiz "Jane Doe"
Name2: .asciiz "Joe Stein"
Name3: .asciiz "Ali Toure"
Name4: .asciiz "Veena Lal"
Name5: .asciiz "Stan Kubrick"

Network:
  .word 3   #total_nodes
  .word 3   #total_edges
  .word 5   #size_of_node
  .word 12  #size_of_edge
  .word 0   #curr_num_of_nodes
  .word 0   #curr_num_of_edges
  .asciiz "NAME"
  .asciiz "FRIEND"
   # set of nodes
  .byte 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
   # set of edges
  .word 0 0 0 0 0 0 0 0 0
.text
main:
	  la $a0, Network
  	jal create_person
  	move $s0, $v0		# return person

  	#write test code



exit:
	li $v0, 10
	syscall
.include "hw4.asm"
