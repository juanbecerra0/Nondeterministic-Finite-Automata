package fa.nfa;

import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;

/**
 * @author Juan Becerra
 * @author Keegan Saunders
 */
public class NFA implements NFAInterface {

    /**
     * Class used as a key value pair for transition functions
     */
    private class StateTransitionPair {

        // The hashcode computed from the state name string and transition char
        private String state;
        private char transition;
        private int hashCode;

        /**
         * Constructor for StateTransitionPair. Simply creates a hashcode from the 
         * concatenated state name and a transition.
         * 
         * @param s
         * @param c
         */
        public StateTransitionPair(String s, char c) {
            this.state = s;
            this.transition = c;
            this.hashCode = (s + c).hashCode();
        }

        /**
         * Returns unique computer hashcode.
         * 
         * Used to ensure that the key value of transitions Map is unique.
         * 
         * @return hashCode
         */
        @Override
        public int hashCode() {
            return hashCode;
        }

        /**
         * @return the state string
         */
        public String getState() {
            return state;
        }

        /**
         * @return the transition char
         */
        public char getTransition() {
            return transition;
        }

        /**
         * Returns whether two STP's are equal.
         * 
         * Used to ensure that the key value of transitions Map is unique.
         */
        @Override
        public boolean equals(Object object) {
            StateTransitionPair otherSTP = (StateTransitionPair) object;
            return this.hashCode() == otherSTP.hashCode();
        }
    }

    // State sets/maps
    private NFAState startState;
    private Set<NFAState> states;
    private Set<NFAState> finalStates;
    private Set<Character> alphabet;
    //private Map<StateTransitionPair, NFAState> transitions;   // TODO this will probably need to map to multiple states using eClosure

    // The current state in the DFA simulation. Used in "accepts" and "getToState" methods
    // private DFAState currentState;   // TODO this will probably need to change too

    /**
     * Initializes all set variables
     */
    public NFA() {
        states = new LinkedHashSet<NFAState>();
        finalStates = new LinkedHashSet<NFAState>();
        alphabet = new LinkedHashSet<Character>();
        //transitions = new HashMap<StateTransitionPair, NFAState>();
    }

    @Override
    public void addStartState(String name) {
        // Initializes the start state and adds it to the set of all states.
        startState = new NFAState(name);
        states.add(startState);

        // Also, initialize the start state as the currentState
        //currentState = startState; // TODO
    }

    @Override
    public void addState(String name) {
        // Initializes a new state and adds to set of all states.
        NFAState newState = new NFAState(name);

        // If new state is a duplicate, adding is ignored.
        states.add(newState);
    }

    @Override
    public void addFinalState(String name) {
        // Initializes a new state and adds to set of all states and final states.
        NFAState newState = new NFAState(name);

        // If new state is a duplicate, adding is ignored.
        states.add(newState);
        finalStates.add(newState);
    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {
        // Create a "toState" object
        NFAState newState = new NFAState(toState);

        // Check if the "new state" is within our set of all states. If not, simply return.
        if(!states.contains(new NFAState(toState)))
            return;

        // Add symbol to alphabet if it is not already in there
        alphabet.add(onSymb);

        // Create a "key" values, which is a StateTransitionPair object
        //StateTransitionPair stp = new StateTransitionPair(fromState, onSymb); // TODO

        // Maps the key to the corresponding state
        // transitions.put(stp, newState);  // TODO
    }

    @Override
    public Set<? extends State> getStates() {
        // Returns set of all states
        return states;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        // Returns set of all final states
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        // TODO Auto-generated method stub
        return null;
    }

    

}