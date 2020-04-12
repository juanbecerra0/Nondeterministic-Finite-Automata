package fa.nfa;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import fa.State;
import fa.dfa.DFA;

/**
 * @author Juan Becerra
 * @author Keegan Saunders
 */
public class NFA implements NFAInterface {

    // State sets/maps
    private NFAState startState;
    private Set<NFAState> states;
    private Set<Character> alphabet;

    /**
     * Initializes all set variables
     */
    public NFA() {
        states = new HashSet<NFAState>();
        alphabet = new HashSet<Character>();
    }

    @Override
    public void addStartState(String name) {
        // Initializes the start state and adds it to the set of all states.
        if(getState(name) == null){
            startState = new NFAState(name, false);
            states.add(startState);
        } else {
            startState = getState(name);
        } 
    }

    @Override
    public void addState(String name) {
        // Initializes a new state and adds to set of all states.
        NFAState newState = new NFAState(name, false);

        // If new state is a duplicate, adding is ignored.
        states.add(newState);
    }

    @Override
    public void addFinalState(String name) {
        // Initializes a new state and adds to set of all states and final states.
        NFAState newState = new NFAState(name, true);

        // If new state is a duplicate, adding is ignored.
        states.add(newState);
    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {
        // Find the actual from and to state in our set
        NFAState actualFromState = getState(fromState);
        NFAState actualToState = getState(toState);

        // Add alphabet symbol
        alphabet.add(onSymb);

        // Add transition to fromState
        actualFromState.addTransition(onSymb, actualToState);
    }

    // returns state based on name
    private NFAState getState(String name) {
        for(NFAState state : states){
            if(state.getName().equals(name)){
                return state;
            }
        }
        return null;
    }

    @Override
    public Set<? extends State> getStates() {
        // Returns set of all states
        return states;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        // Create set of final states
        Set<NFAState> finalStates = new HashSet<NFAState>();

        // Look through set of all states, adding only final states
        for(NFAState state : states){
            if(state.isFinal()){
                finalStates.add(state);
            }
        }

        // Return created set
        return finalStates;
    }

    @Override
    public State getStartState() {
        // Returns the start state
        return startState;
    }

    @Override
    public Set<Character> getABC() {
        // Returns the alphabet set
        return alphabet;
    }

    @Override
    public DFA getDFA() {
        // DFA to return
        DFA dfa = new DFA();

        // Create the start state, set it to be the start of the NFA, and determine 
        // if it is final
        Set<NFAState> startNFAState = eClosure(startState);
        for (NFAState state : startNFAState) {
            if (state.isFinal()) {
                dfa.addFinalState(startNFAState.toString());
                break;
            }
        }
        dfa.addStartState(startNFAState.toString());

        // Create a queue to explore new DFA states, and add start state to it
        Queue<Set<NFAState>> queue = new LinkedBlockingQueue<Set<NFAState>>();
        queue.add(startNFAState);

        // In addition, keep a set of these states saved to avoid duplicates
        Set<Set<NFAState>> dfaStates = new HashSet<Set<NFAState>>();
        dfaStates.add(startNFAState);

        // Iteratively perform BFS on the branching paths of this startNFA while creating 
        // new states and transitions for the DFA
        while(!queue.isEmpty()) {
            // Dequeue a state
            Set<NFAState> fromNFAState = queue.remove();

            // Iterate through all alphabet symbols
            for (Character symb: alphabet) {
                // Create a new possible state, and keep a flag to see if it would be final
                Set<NFAState> toNFAState = new HashSet<NFAState>();
                Boolean isFinal = false;
                // Iterate through the states within this DFA state
				for (NFAState state : fromNFAState) {
                    // For this set of states, find all the possible transitions given this alphabet symbol
                    Set<NFAState> transitions = getToState(state, symb);
                    for (NFAState t : transitions) {
                        toNFAState.add(t);
                        // Flag if this transition-to state is final
                        if (t.isFinal())
                            isFinal = true;
                    }
                }

                // If toState is empty, then this symbol would go to the error state
                if (toNFAState.isEmpty()) {
                    if(!dfaStates.contains(toNFAState))
                        dfa.addState("[]");
                    dfa.addTransition(fromNFAState.toString(), symb, "[]");
                }
                // Otherwise, if final, add this as a final state
                else if (isFinal) {
                    if(!dfaStates.contains(toNFAState))
                        dfa.addFinalState(fromNFAState.toString());
                    dfa.addTransition(fromNFAState.toString(), symb, toNFAState.toString());
                } else {
                    if(!dfaStates.contains(toNFAState))
                        dfa.addState(fromNFAState.toString());
                    dfa.addTransition(fromNFAState.toString(), symb, toNFAState.toString());
                }
            }
        }

        return dfa;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        // Keep track of a set to return
        Set<NFAState> ret = new HashSet<NFAState>();

        // Get eClosure of from
        for (NFAState closureState : eClosure(from)) {
            // Apply the transition
            for(NFAState transState : closureState.getTo(onSymb)) {
                // For each of the eClosures of the transitions, add to ret
                for(NFAState transClosureState : eClosure(transState)) {
                    ret.add(transClosureState);
                }
            }
        }

        // Return the set
        return ret;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        // Create a new set with this current state
        Set<NFAState> eClosure = new HashSet<NFAState>();
        eClosure.add(s);

        // Perform initial recursive call on this state
        return eClosureRecursiveSearch(eClosure, s);
    }

    private Set<NFAState> eClosureRecursiveSearch(Set<NFAState> eClosure, NFAState s) {
        for (NFAState transitionState : s.getTo('e')) {
            if (!eClosure.contains(transitionState)) {
                eClosure.add(transitionState);
                eClosure = eClosureRecursiveSearch(eClosure, transitionState);
            }
        }
        return eClosure;
    }

}