# Project 2: Nondeterministic Finite Automata

* Authors: Keegan Saunders and Juan Becerra
* Class: CS361 Section 1
* Semester: Spring 2020

## Overview

Nondeterministic Finite Automata builds off of our previous DFA assignment, which allowed a user to create a finite-state machine and test a list of strings on it to see if the machine would accept them. While NFA’s and DFA’s are equivalent in power, it’s often easier to develop a non-deterministic machine and converting it into a deterministic machine for real-world applications. One of the biggest features of this assignment is allowing users to create an NFA that can easily be converted into a DFA with a single method, getDFA(), which in turn calls several other useful functions, like getToState(from, symb) and eClosure(state).

## Compiling and Using

To compile the program, go to the project root directory type the console command:

```
javac fa/nfa/NFADriver.java
```

then, run the program by typing the console command:

```
java fa.nfa.NFADriver ./tests/[TESTFILE]
```

Where the TESTFILE options are:
- p2tc0.txt
- p2tc1.txt
- p2tc2.txt
- p2tc3.txt

## Discussion

// TODO

## Testing

// TODO

## Extra Credit

No extra credit was offered in this assignment, but we’ll absolutely take some 😉