# Homework 4

## Learning Outcomes

After completion of this assignment, you should be able to:

- Work with graph-based structures.

- Write Functions and manage the call stack.

## Getting Started

To complete this homework assignment, you will need the MARS simulator. Download it from Blackboard. You can write your programs in the MARS editor itself. You can choose to use other text editors if you are not comfortable with the MARS editor. At any point, if you need to refer to instructions click on the *Help* tab in the MARS simulator.

Read the rest of the document carefully. This document describes everything that you will need to correctly implement the homework and submit the code for testing.

You should have already setup Git and configured it to work with SSH. If you haven't then do Homework 0 first!

The first thing you need to do is download or clone this repository to your local system. Use the following command:

`$ git clone <ssh-link>`

After you clone, you will see a directory of the form *cse220-hw4-<username>*, where *username* is your GitHub username.

In this directory, you will find *hw4.asm*. This file has function stubs that you will need to fill up. At the top of the file you will find hints to fill your full name, NetID, and SBU ID. Please fill them up accurately. This information will be used to collect your scores from GitHub. If you do not provide this information, your submission may not be graded. The directory also has a template test file ending with *hw4_test.asm*. Use the file for preliminary testing.  You can change the data section or the text section in this files to test different cases for each part (described later). You may also create your own *_test.asm* files if necessary. Don't push these additional *_test.asm* files to the repository. The *tests* directory contain the test cases for this homework. You can use the test cases as specifications to guide your code. Your goal should be to pass all the tests. If you do so, then you are almost guaranteed to get full credit. The files in the *tests* directory should not be modified. If you do, you will receive no credit for the homework.

**Note the hw4.asm file doent have a .data section. Do not add a .data section.**

**Don't forget to add you name and IDs at the top of hw4.asm. Follow the exact format, i.e, replace the hints with the correct information. You will be penalized if you do not follow the format.**

## Assembling and Running Your Program in MARS

To execute your MIPS programs in MARS, you will first have to assemble the program. Click on the *assemble* option in the *Run* tab at the top of the editor. If the instructions in your program are correctly specified, the MARS assembler will load the program into memory. You can then run the program by selecting the *Go* option in the same *Run* tab. To debug your program, add breakpoints. This is done after assembling the program. Select the *execute* tab, you will see the instructions in your program. Each instruction will have a checkbox associated with it. Clicking on the checkbox will add a breakpoint, that is, when the program is run, control will stop at that instruction allowing you to inspect the registers and memory up to that point. The execute tab will show you the memory layout in the bottom pane. The right hand pane shows the list of registers and their values.

Always assume that memory and registers will have garbage data. When using memory or registers, it is your responsibility to initialize it correctly before using it. You can enable the *Garbage Data* option under *Settings* in MARS to run your programs with garbage data in memory.

## How to read the test cases

As mentioned previously, the *tests* folder contains the test file *Hw4Test.java*. Each test is a Java function with a name prefixed with *verify_*. In each of these functions, you will find an assert statement that needs to be true for the test to pass. These asserts compare the expected result with the actual result returned by the function under test. The tests assume that the expected results will be in certain registers or labels as explained in the later parts of this README. In each test, we will execute your program by calling the *run* function along with the necessary arguments required to test that particular feature. If a test fails, the name of the failing test will be reported with an error message. You can use the error message from the failing test to diagnose and fix your errors. Another way is to look at the inputs of the failed test case and plug them into appropriate labels in the *_test.asm* file/s and run it to debug your program.

*Do not change any files in the tests directory. If you do then you won't receive any credit for this homework assignment*.

## Problem Specification

In this assignment, we will learn how to build and manipulate a custom data structure using MIPS.

Mark Jobs has a brilliant idea to create a network of people in which nodes represent people and edges between nodes represent relationships between people. Mark’s goal, through this network, is to capture various properties about people and their relationships. Furthermore, he wants to be able to query the network
- for persons using their names and
- for friends of friends of a person also by name

We need to help Mark build and manage such a network. To this end, we will construct and maintain a data structure called Network.

### The Network Data Structure

The Network data structure uses two other structures called Node and Edge. In order to understand, Network, we first have to understand the structure of Node and Edge. The Node data structure represents a person with a name and defined as follows:

```
struct Node {
   byte[N] name	// null-terminated string of N characters
}
```

The Edge data structure represents a relationship between two people. It is defined as follows:

```
struct Edge {
Node* p1	// reference to a person
Node* p2	// reference to a person
int friend	//attribute to indicate presence or absence of friendship
}
```

Notice that Edge does not contain the actual person nodes in a relationship, but a reference to the nodes that are related. The friend attribute/property is 1 if the relationship is a friendship, and any non negative integer otherwise.

We define Network as follows:

```
struct Network {
  int total_num_nodes   # max no. of nodes in the network
  int total_num_edges   # max no. of edges in the network
  int size_of_node      # The size of a node
  int size_of_edge      # The size of an edge
  int curr_num_nodes    # No. of nodes currently in Network
  int curr_num_edges    # No. of edges currently in Network
  char[] name_prop      # Name property; always set to "NAME"
  char[] frnd_prop      # Friend property; always set to "FRIEND"
  Node[] nodes          # The set of nodes in the Network;
                        # nodes_capacity = total_num_nodes *
                        # size_of_node
  Edge[] edges          # The set of edges in the Network
                        # edges_capacity = Total_num_edges *
                        # size_of_edge
}
```

Here is a brief description of the elements in Network:

1. *total_num_nodes* is a 4-byte nonnegative integer that represents the maximum no. of nodes that the network can hold. If this limit is reached, the network should not accept anymore people.
2. *total_num_edges* is a 4-byte nonnegative integer that indicates the maximum no. of edges that the network can hold. If the limit is reached, then the network should not accept anymore relationships.
3. *size_of_node* is a 4-byte nonnegative integer that indicates the size of a node. Essentially, this attribute represents the maximum length of a person’s name. We assume that a person’s name will always be a null-terminated string.
4. *size_of_edge* is a 4-byte nonnegative integer that denotes the size of an edge (i.e., a relationship between two people). Assume this size is always 12 as an edge is made of 3 entities each of size 4 -- two person references and one friend attribute.
5. *curr_num_nodes* is a 4-byte nonnegative integer that represents the no. of nodes (people) currently in the network. Assume this number is never greater than *total_num_nodes*.
6. *curr_num_edges* is a 4-byte nonnegative integer that represents the no. of edges currently in the network. This number is never greater than total_num_edges.
7. *name_prop* is a null-terminated string and always set to “NAME”. This is used to add a name property to a person node. We will see its use later.
8. *frnd_prop* is a null-terminated string always set to “FRIEND”. This is used to add a friend relationship to a relationship between two people. Its use will be evident later.
9. *nodes* is a set of nodes (or people) currently in the Network. It is bound by *total_num_nodes*. A node will will typically hold a person's name. In the absence of a name, you may assume the node to be filled with 0s.
10. *edges* is a set of relationships between people currently in the Network. It is bound by *total_num_edges*.

Assume nothing about the *Network* data structure other than what is specified above.

As an example, consider the following instantiation of Network in MIPS:

```
Network:
  .word 3   #total_nodes (bytes 0 - 3)
  .word 6   #total_edges (bytes 4- 7)
  .word 12  #size_of_node (bytes 8 - 11)
  .word 12  #size_of_edge (bytes 12 - 15)
  .word 0   #curr_num_of_nodes (bytes 16 - 19)
  .word 0   #curr_num_of_edges (bytes 20 - 23)
  .asciiz "NAME" # Name property (bytes 24 - 28)
  .asciiz "FRIEND" # FRIEND property (bytes 29 - 35)
   # set of nodes (bytes 36 - 71)
  .byte 1 2 3 4 5 ...
   # set of edges (bytes 72 - 143)
  .word 1 2 3 4 5 ...

```

In this example, Network has 3 nodes and 6 edges. This means that the Network can hold 3 people and 6 relationships. Further, each node can hold exactly 12 characters (including the null character). Every element in the set of nodes is a 11-character string indicating the name of a person. Every element in the set of edges is a collection of 3 attributes -- the first two are node (or person) addresses and the third one is the friend indicator. The total no. of bytes occupied by Network in this example is 4 + 4 +  4 + 4 + 4 + 4 + 5 + 7 + 36 + 72 = 144 bytes.

Notice that the set of nodes is really an array of strings, where the strings are names of people in the Network. Similarly, the set of edges is really an array of references, where the references are addresses of person nodes in the network. Since both these sets are actually arrays, we can access every element in them by calculating an offset from the base address of the set (array indices start at 0). The base address of nodes and edges will be at a fixed location relative to the base address of Network.

We will use these data structures to define operations/functions that will help us implement the social network envisioned by Mark Jobs. Your job in this assignment will be to implement each of the defined functions.

### Part 1: Create A Person

**Node\* create_person(Network\* ntwrk)**

The function create_person takes the address of Network (as defined above), creates a person node, adds it to the network, and returns the person node. Creating a person involves obtaining a reference (address) to the first free node in the Network's nodes set. Once we have the address of such a node, we initialize the node with 0s, increment the current no. of nodes in the Network by 1, and return the node address.

It is possible that the Network is at capacity, that is, no free nodes are available. In that case, the function should return -1.

In summary, the function takes the base address of Network as an argument and returns the address of a node in the network or -1 in register *$v0*.

### Part 2: Check A Person's Existence

**int is_person_exists(Network\* ntwrk, Node\* person)**

The function *is_person_exists* verifies if a person exists in the Network. It takes a reference to the Network structure and a reference to a person node as arguments. It returns 1 if the person node had been created earlier; otherwise it returns 0.

We say that a person node exists in the Network if it is in the Network’s set of currently instantiated nodes.

In summary, the function takes the base address of Network as 1st argument and address of a person as 2nd argument. The function returns 0 or 1 in register *$v0*.

### Part 3: Check A Person's Name

**int is_person_name_exists(Network\* ntwrk, char\* name)**

The function *is_person_name_exists* verifies if a name exists in Network. It takes a reference to the Network structure and a (null-terminated) string as arguments. It returns 1 if any person in Network’s nodes set has the same name; otherwise it returns 0. Further, if a name exists in the Network, then the function also returns a reference to the person with the name.

In summary, the function takes the base address of Network as 1st argument and a string as 2nd argument. The function returns 0 or 1 in register *$v0*.

### Part 4: Add Person Property

**int add_person_property(Network\* ntwrk, Node\* person, char\* prop_name, char\* prop_val)**

A person may have various properties. One common property is a person’s name. This function *add_person_property* sets the name property of an existing person in the Network. It takes as input the address of Network, the address of the person whose property needs to be set, the property name (null-terminated string), and the property value (null-terminated string) to be added. Since we are primarily interested in a person’s name, we will use this function to add the name property of a person. All other properties should be ignored. So, the function should add/set the name of an existing person in the Network to string *prop_val* only if:

1. *prop_name* is equal to the string “NAME”,
2. *person* exists in Network
3. The no. of characters in *prop_val*(excluding null character) < *Network.size_of_node*
4. *prop_val* is unique in the Network.

The function should return 1 if the name property is added successfully to the person. It should return 0 if condition 1 is violated, -1 if condition 2 is violated, -2 if condition 3 is violated, and -3 if condition 4 is violated. You can assume that only one violation will occur at a time. The return value should be in register *$v0*.

### Part 5: Query Network

**Node\* get_person(Network\* network, char\* name)**

The function *get_person* takes two arguments -- a reference to Network and a string indicating a person’s name. The function should return a reference (or address) to the person node in Network that has its name property set to name. If no such person is found, then the function should return 0. The return value should be in register *$v0*.

### Part 6: Check If People Are Related

**int is_relation_exists(Network\* ntwrk, Node\* person1, Node\* person2)**

Two people in the Network are related if *Network.edges* contains an edge between person1 and person2. An edge in *Network.edges* is undirected, that is, if there is an edge between person1 and person2, then there is also an edge between person2 and person1.

The function is_relation_exists takes a reference to Network (ntwrk), and two references to two person nodes (person1 and person2) . If *Network.edges* contains an edge between person1 and person2, the function returns 1; otherwise the function returns 0. The return value should be in register *$v0*.

### Part 7: Add Relationship

**int add_relation(Network\* ntwrk, Node\* person1, Node\* person2)**

The function *add_relation* takes a reference to Network and two references to two person nodes. If both persons exist in the Network, then an edge between person1 and person2 should be added to *Network.edges*. At this moment, the friend property of the edge should be 0. The function returns 1 if the relation was added successfully. It fails to add the relation if:

1. Neither person1 nor person2 exists in Network.
2. The Network is at capacity, that is, it already contains the maximum no. of edges possible.
3. A relation between person1 and person2 already exists in Network. Relations must be unique.
4. person1 == person2. A person cannot be related to herself.

The function should return 0 if failure to add the relation is due to condition 1, -1 if the failure is due to condition 2, and -2 if the failure is due to condition 3, and -3 if the failure is due to condition 4. You can assume that only one violation will occur at a time. The function should return values in register *$v0*.

### Part 8: Add Friendship

**int add_relation_property(Network\* ntwrk, Node\* person1, Node\* person2, char\* prop_name)**

The function *add_relation_property* takes as arguments a reference to Network, two references to two person nodes (person1 and person2), and a null-terminated string (prop_name). It sets the friend property of an existing relation in Network to 1. A positive integer in friend property of the relation person1, person2 indicates that person1 and person2 are friends. The function returns 1 if:

1. A relation between person1 and person2 does not exist in Network, or
2. prop_name is not the string “FRIEND”.

The function should return 0 in register *$v0* if failure to add the property is due to condition 1 and -1 in register *$v0* if the failure is due to condition 2. You can assume that only one violating condition will occur at a time.

### Part 9: Check Common Friend

**int is_friend_of_friend(Network\* ntwrk, char\* name1, char\* name2)**

We say that *person1* is a friend-of-friend of *person3* if *person2* is friend of *person3* and *person2* is also friend of *person1* but *person1* is not directly a friend of *person3*.

The function *is_friend_of_friend* takes a reference to Network and two null-terminated person names, name1 and name2. The function returns 1 if a person with name1 is a friend-of-friend of a person with name2 or vice-versa. It returns 0 if a person with name1 is not friend-of-friend of a person with name2. Further, it returns -1 if neither a person with name1 nor a person with name2 exists in Network. The return value should be in register *$v0*.

## Submitting Code to GitHub

You can submit code to your GitHub repository as many times as you want till the deadline. After the deadline, any code you try to submit will be rejected. To submit a file to the remote repository, you first need to add it to the local git repository in your system, that is, directory where you cloned the remote repository initially. Use following commands from your terminal:

`$ cd /path/to/cse220-hww-<username>` (skip if you are already in this directory)

`$ git add hw4.asm`

To submit your work to the remote GitHub repository, you will need to commit the file (with a message) and push the file to the repository. Use the following commands:

`$ git commit -m "<your-custom-message>"`

`$ git push`

Every time you push code to the GitHub remote repository, the test cases in the *tests* folder will run and you will see either a green tick or a red cross in your repository just like you saw with homework0. Green tick indicates all tests passed. Red cross indicates some tests failed. Click on the red cross and open up the report to view which tests failed. Diagnose and fix the failed tests and push to the remote repository again. Repeat till all tests pass or you run out of time!

**After you submit your code on GitHub. Enter your GitHub username in the Blackboard homework assignment and click on Submit**. This will help us find your submission on GitHub.

## Running Test Cases Locally

It may be convenient to run the test cases locally before pushing to the remote repository. To run a test locally use the following command:

`$ java -jar munit.jar tests/Hw4Test.class hw4.asm`


Remember to set java in your classpath. Your test cases may fail if you do not have the right setup. If you do not have the right setup it is most likely because you did not do homework 0 correctly. So, do homework 0 first and then come back here!
