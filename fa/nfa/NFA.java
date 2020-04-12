package fa.nfa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Queue;

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
        Set<NFAState> finalStates = new LinkedHashSet<NFAState>();

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
    
        Set<Set<NFAState>> dfaStates = new HashSet<Set<NFAState>>();
        NFAState nullState = new NFAState("[]", false);
        DFA dfa = new DFA();

        //start state
        Set<NFAState> start = eClosure(this.startState);
        for(NFAState s : start){
            if(s.isFinal()){
                dfa.addFinalState(start.toString());
                break;
            }
        }
        dfaStates.add(start);
        dfa.addStartState(start.toString());


        for(NFAState state : this.states){
            for(Character c : this.alphabet){
                Set<NFAState> curr = getToState(state, c);
                if (curr != null) {
                    System.out.println("zabeep" + curr.toString());
                    for(NFAState s : curr){
                        if(s != null && s.isFinal()){
                            dfa.addFinalState(curr.toString());
                            break;
                        }
                    }
                    System.out.println("beep" + curr.toString());
                    dfa.addState(curr.toString());
                } else {
                    System.out.println("null transition found");
                    dfa.addState("[]");
                }
            }
        }

        for(Set<NFAState> dfaState : dfaStates){
            for(Character c : this.alphabet){
                Set<NFAState> transition = new LinkedHashSet<>();
                for(NFAState s : dfaState){
                    for(NFAState t : getToState(s, c)){
                        if(t == null){
                            t = nullState;
                        }
                        transition.add(t);
                    }
                }
                dfa.addTransition(dfaState.toString(), c, transition.toString());
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