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
The eClosure() method returns a set of states that a state can transition to given the empty transition. It was implemented using a recursive depth-first search helper method, eClosureRecursiveSearch(Set<NFAState> eClosure, NFAState s). To start, eClosure() creates a set of NFAStates and adds the argument state to it. It then returns eClosureRecursiveSearch, where that set and the given state is passed in as an argument. From there, eClosureRecursiveSearch() iterates through every state that the given state can get to on an ‘e’. If that state is not within the set passed in, then it is an inserted, and the set of states is changed to whatever a further call of eClosureRecursiveSearch() returns. This will essentially keep viewing unexplored states and evaluating every state’s empty transitions in depth-first order, until the call stack returns a set of all explorable states. 

### getToState()
The getToState() method creates a “return set” of states that will be returned, iterates through the eClosure() of the given states, applies the transition to all of the eClosure(), then once again calls eClosure() on every transition. The resulting set of states is then added to the “return set”, which the method then returns. The idea of this method is to get all possible transitions that an NFA can take when it sees a symbol. This requires us to evaluate the eClosure() both before AND after applying the transition in order to correctly get all possible transitions that a set can take. As previously stated, we changed NFAState.getTo() to simply return an empty set rather than throwing an error. This is because we simply want to skip one of our for loops and return an empty set in getTo(), as this indicates that we can’t get anywhere given a start state and symbol.

### getDFA()
The getDFA() method returns a DFA equivalent to the current NFA. This was the most challenging method to implement, as it would essentially require us to program a series of steps used in NFA -> DFA transitions while adhering to the given DFA’s API. We begin by evaluating the eClosure of the NFA start state, which then becomes the start state of the DFA. We then enqueue this state and enter a loop that continues while the queue is not empty. The body of this loop iteratively evaluates states in a breadth-first manner, attempting to create transitions for every symbol in the alphabet, which inevitably creates new states that are inserted into the queue. Some other important details include iterating through the getToState() of the dequeued state, adding them into a bundled new state, tagging if this new state would be final, and seeing if this bundled state exists in a set (which would indicate that the state already exists and doesn’t need to be enqueued).

## Testing

// TODO

## Extra Credit

No extra credit was offered in this assignment, but we’ll absolutely take some 😉