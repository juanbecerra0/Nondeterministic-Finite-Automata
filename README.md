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

Much of our approach for this assignment was modeled after the provided DFA.java and DFAState.java files, in addition to some of the work we accomplished in the previous assignment. We started work in NFAState.java where much of the code reflected the DFAState equivalent. However, one of the biggest changes we made was changing the delta hashmap from a key value pair of “Character, DFAState” to “Character, Set<NFAState>”. This was because, while it was true that a DFA state would deterministically transition to another state on a character, an NFA could go to any number of states (or even none at all) on a character. From there, we modified addTransition() to add to this set of states rather than simply mapping a single character to a single state, like in DFAState. We then modified getTo() to return an empty set if the transition does not exist rather than throwing an error (we’ll get into why we did this later in the “Testing” section).

Much of our approach for this assignment was modeled after the provided DFA.java and DFAState.java files, in addition to some of the work we accomplished in the previous assignment. We started work in NFAState.java where much of the code reflected the DFAState equivalent. However, one of the biggest changes we made was changing the delta hashmap from a key value pair of “Character, DFAState” to “Character, Set<NFAState>”. This was because, while it was true that a DFA state would deterministically transition to another state on a character, an NFA could go to any number of states (or even none at all) on a character. From there, we modified addTransition() to add to this set of states rather than simply mapping a single character to a single state, like in DFAState. We then modified getTo() to return an empty set if the transition does not exist rather than throwing an error (we’ll get into why we did this later in the “Testing” section).

In NFA.java, we also modeled many of our basic methods after what we found in the provided DFA.java, and our old DFA implementation. The following methods:
-	Constructor
-	addStartState()
-	addState()
-	addFinalState()
-	addTransition()
-	getStates()
-	getFinalStates()
-	getStartState()
-	getABC()

were implemented almost exactly like they were in DFA, as one would expect. We excluded some error checking as they weren’t really needed for this assignment besides debugging during development. The most significant changes made for this assignment were done in getDFA(), getToState(), and eClosure().

### eClosure()
// TODO

### getToState()
// TODO

### getDFA()
// TODO

## Testing

// TODO

## Extra Credit

No extra credit was offered in this assignment, but we’ll absolutely take some 😉