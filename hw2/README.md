# Homework 1

## Learning Outcomes

After completion of this assignment, you should be able to:

- Work with 1D Arrays.

- Write Functions and manage the call stack.

- Implement a basic encryption algorithm.

## Getting Started

To complete this homework assignment, you will need the MARS simulator. Download it from Blackboard. You can write your programs in the MARS editor itself. You can choose to use other text editors if you are not comfortable with the MARS editor. At any point, if you need to refer to instructions click on the *Help* tab in the MARS simulator.

Read the rest of the document carefully. This document describes everything that you will need to correctly implement the homework and submit the code for testing.

You should have already setup Git and configured it to work with SSH. If you haven't then do Homework 0 first!

The first thing you need to do is download or clone this repository to your local system. Use the following command:

`$ git clone <ssh-link>`

After you clone, you will see a directory of the form *cse220-hw2-<username>*, where *username* is your GitHub username.

In this directory, you will find *hw2.asm*. This file has function stubs that you will need to fill up. At the top of the file you will find hints to fill your full name, NetID, and SBU ID. Please fill them up accurately. This information will be used to collect your scores from GitHub. If you do not provide this information, your submission may not be graded. The directory also has a host of template test files ending with *_test.asm*. Use the files for preliminary testing.  You can change the data section or the text section in these files to test different cases for each part (described later). The *tests* directory contain the test cases for this homework. You can use the test cases as specifications to guide your code. Your goal should be to pass all the tests. If you do so, then you are almost guaranteed to get full credit. The files in the *tests* directory should not be modified. If you do, you will receive no credit for the homework.

**Note the hw2.asm file doent have a .data section. Do not add a .data section.**

**Don't forget to add you name and IDs at the top of hw2.asm. Follow the exact format, i.e, replace the hints with the correct information. You will be penalized if you do not follow the format.**

## Assembling and Running Your Program in MARS

To execute your MIPS programs in MARS, you will first have to assemble the program. Click on the *assemble* option in the *Run* tab at the top of the editor. If the instructions in your program are correctly specified, the MARS assembler will load the program into memory. You can then run the program by selecting the *Go* option in the same *Run* tab. To debug your program, add breakpoints. This is done after assembling the program. Select the *execute* tab, you will see the instructions in your program. Each instruction will have a checkbox associated with it. Clicking on the checkbox will add a breakpoint, that is, when the program is run, control will stop at that instruction allowing you to inspect the registers and memory up to that point. The execute tab will show you the memory layout in the bottom pane. The right hand pane shows list of registers and their values.

Always assume that memory and registers will have garbage data. When using memory or registers, it is your responsibility to initialize it correctly before using it. You can enable the *Garbage Data* option under *Settings* in MARS to run your programs with garbage data in memory.

## How to read the test cases

As mentioned previously, the *tests* folder contains the test file *Hw2Test.java*. Each test is a Java function with a name prefixed with *verify_*. In each of these functions, you will find an assert statement that needs to be true for the test to pass. These asserts compare the expected result with the actual result returned by the function under test. The tests assume that the expected results will be in certain registers or labels as explained in the later parts of this README. In each test, we will execute your program by calling the *run* function along with the necessary arguments required to test that particular feature. If a test fails, the name of the failing test will be reported with an error message. You can use the error message from the failing test to diagnose and fix your errors. Another way is to look at the inputs of the failed test case and plug them into appropriate labels in *_test.asm* files and run the appropriate *_test.asm* to debug your program.

*Do not change any files in the tests directory. If you do then you won't receive any credit for this homework assignment*.

## Problem Specification

The Vatsyayana Cipher is one of the oldest encryption algorithms.It first appeared in ancient India as part of a series of commentaries known as Mlecchita Vikalpa, the art of secret writing and communications.

The Vatsyayana cipher is a simple [substitution cipher](https://en.wikipedia.org/wiki/Substitution_cipher) where the letters of an alphabet are organized into random pairs of letters. These pairs are used to encrypt a plain text by direct substitution of the letters in the pairs. For example, using the English alphabet [A-Z], the Vatsyayana cipher may generate the following 13 pairs:

- A/E
- B/T
- C/J
- D/N
- F/W
- G/S
- H/K
- I/Z
- L/V
- M/R
- O/Y
- P/U
- Q/X

Using these pairs as our cipher key, the cipher text for a plain text "A long time ago" will be "E VYDS BZRA ESY". Notice that the algorithm is case insensitive. It will always result in a cipher text with capital letters. Also, non-letters such as spaces are preserved.

For simplicity, we will assume that the alphabet will always be the English alphabet [A-Z] with capital letters. The plain texts we want to encrypt our sentences with English words separated by spaces.

There are many ways to implement the algorithm. We will implement this algorithm using the functions described in the following nine parts. When defining the functions in each part, we will use C-like syntax. For example, *char\** means the address of a string.

For each function defined in the parts, you must follow the **register conventions** discussed in class. If you violate the register conventions, the test cases will fail and you will get no credit for a failing test case.

You may also refer to the guide to register conventions posted in Blackboard under *Readings* in *Course Documents*. The guide provides a crisp explanation of the conventions with examples.   

### Part 1: Convert to Upper Case

**void to_upper(char\* s)**

This function takes the base address of a string of letters (null-terminated). It converts the letters in the string to upper case. The string (passed as argument) should be modified in-place, i.e, without using additional memory. The function returns nothing as indicated by the return type *void*.

### Part 2: Remove Character

**int remove(char\* s, int i)**

This function takes the base address of a string (null-terminated) as first argument and an integer as second argument. It removes the character at index *i* (starting at 0) of the string. The string should be modified in-place. The function returns 1 in the register *$v0* if the removal is successful. It returns -1 in the register *$v0* if the removal fails. Removal should fail if the index passed as argument is out of bounds.


### Part 3: Get Random Number

**int getRandomInt(int n)**

This function takes a positive integer *n* as argument and returns a random number in the range [0,N-1] (inclusive) in the register *$v0* and 1 in the register *$v1*. If *n* is less than or equal to 0 then the function should return -1 in *$v0* and 0 in *$v1*.

Use the following syscall to generate a random number between 0 to N.

```
# $a1 = N
li $v0 42
syscall
```

You can also refer to the syscalls in *Help* in MARS to generate a random number.

### Part 4: Copy Character

**char\* cpyElems(char\* src, int i, char \* dest)**

This function takes three arguments -- the base address of a source string, a non-negative integer index, and the base address of an empty destination string. The function should copy the character at the index of the source string to the first address in the destination string. It should return the next address of the destination string in *$v0*.  

You can assume that the source and destination strings will be null terminated. The purpose of this function is to facilitate copying one string to another string one character at a time.

### Part 6: Character Existence

**int contains(char\* s, char ch)**

This function takes the base address of a string (null-terminated) as the first argument and a character as the second argument. If the character exists in the string then it returns the index of the first occurrence of the character in the string (starting at 0) in the register *$v0*. If the character does not exist then the function returns -1 in *$v0*. You can assume the string is null-terminated.

### Part 7: Generate Cipher Key

**void genKey(char\* alphabet, char\* cipherKey)**

This function is used to generate a cipher key from a given alphabet of letters. It takes two arguments -- a string of letters (null-terminated) indicating the alphabet and an empty string which will contain the cipher key. The function should generate the cipher key string as per the rules of the Vatsyayana Cipher described in the *Problem Specification* section. According to these rules, the cipher string key should be formed by randomly selecting pair of letters from the alphabet. For example, if the alphabet is "ABCD", then one cipher key string could be "ADCB", where AD is a pair and CB is a pair. The cipher key string must be null terminated. Due to the randomness of the algorithm, the function may return different pairs every time it is called. The function returns nothing as indicated by the return type *void*.

### Part 8: Check Pair in Key

**int pair_exists(char c1, char c2, char\* key)**

This function takes two characters and a cipher key string (null-terminated) as arguments. If the pair (c1,c2) or (c2,c1) exists in the cipher key string then the function returns 1 in register *$v0* and returns 0 in *$v0* if the pair does not exist. For example, if c1 is 'A' and c2 is 'Y' and the cipher key string is "AYGH" then the function should return 1 in $v0. Similarly, if c1 is 'Y' and c2 is 'A' then the function should still return 1 in $v0.

### Part 9: Encrypt

**int encrypt(char\* plaintext, char\* cipherkey, char\* ciphertext)**

This function takes three arguments -- the base addresses of a plain text string, a cipher key string, and a cipher text string. The plain text will always be a collection of words separated by spaces. The function uses the cipher key to encrypt the plain text. The cipher text obtained must preserve the spaces between the words in the original plain text. The result of the encryption should be stored in the cipher text string and null-terminated. The function should return 1 in *$v0* if the encryption is successful. If the encryption fails then the function should return 0 in *$v0*. Encryption fails when it cannot find a cipher key substitute for a letter in the plain text.

The encryption operation should follow the Vatsyayana cipher algorithm described above.


### Part 10: Decipher Key

The Vatsyayana cipher algorithm isn't secure. If an attacker has the ability to choose a plain text and has access to the cipher text, then they will be able to infer the cipher key easily since every encryption with the key will produce the same cipher text for a chosen plain text. To help an attacker decipher keys at scale, we will write the following function:

**void decipher_key_with_chosen_plaintext(plaintext, ciphertext, key)**

This function takes three arguments -- base addresses of a plain text string (null-terminated), a cipher text string (null-terminated), and a cipher key string. The cipher key inferred from the plaintext and ciphertext pair should be stored in the cipher key string and null-terminated. The function returns nothing.  


## Submitting Code to GitHub

You can submit code to your GitHub repository as many times as you want till the deadline. After the deadline, any code you try to submit will be rejected. To submit a file to the remote repository, you first need to add it to the local git repository in your system, that is, directory where you cloned the remote repository initially. Use following commands from your terminal:

`$ cd /path/to/cse220-hw2-<username>` (skip if you are already in this directory)

`$ git add hw2.asm`

To submit your work to the remote GitHub repository, you will need to commit the file (with a message) and push the file to the repository. Use the following commands:

`$ git commit -m "<your-custom-message>"`

`$ git push`

Every time you push code to the GitHub remote repository, the test cases in the *tests* folder will run and you will see either a green tick or a red cross in your repository just like you saw with homework0. Green tick indicates all tests passed. Red cross indicates some tests failed. Click on the red cross and open up the report to view which tests failed. Diagnose and fix the failed tests and push to the remote repository again. Repeat till all tests pass or you run out of time!

**After you submit your code on GitHub. Enter your GitHub username in the Blackboard homework assignment and click on Submit**. This will help us find your submission on GitHub.

## Running Test Cases Locally

It may be convenient to run the test cases locally before pushing to the remote repository. To run a test locally use the following command:

`$ java -jar munit.jar tests/Hw2Test.class hw2.asm`


Remember to set java in your classpath. Your test cases may fail if you do not have the right setup. If you do not have the right setup it is most likely because you did not do homework 0 correctly. So, do homework 0 first and then come back here!
