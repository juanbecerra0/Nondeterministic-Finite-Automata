package fa.nfa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
        states = new LinkedHashSet<NFAState>();
        alphabet = new LinkedHashSet<Character>();
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
        NFAState actualFromState = getState(fromState);
        NFAState actualToState = getState(toState);

        actualFromState.addTransition(onSymb, actualToState);
    }

    // returns state based on name
    private NFAState getState(String name) {
        for(NFAState state : states){
            if(state.getName() == name){
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
        // Returns set of all final states
        Set<NFAState> finalStates = new LinkedHashSet<NFAState>();

        for(NFAState state : states){
            if(state.isFinal(){
                finalStates.add(state);
            }
        }

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