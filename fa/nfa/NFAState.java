package fa.nfa;

import java.util.HashMap;
import java.util.Set;

/**
 * Class that represents a single state in a non-deterministic finite automata.
 * Extends the functionality of the abstract State class. Used by NFA instances
 * to represent the IO nature of states in finite state machines.
 * 
 * @author Juan Becerra
 * @author Keegan Saunders
 */
public class NFAState extends fa.State {

    private HashMap<Character, Set<NFAState>> delta;
    private boolean isFinal;

    /**
     * Constructs an NFAState.
     * 
     * Sets a name, a delta transition function that returns a set of NFA States
     * given a character, and sets final-status to false.
     * 
     * @param name
     */
    public NFAState(String name) {
        this.name = name;
        delta = new HashMap<Character, Set<NFAState>>();
        isFinal = false;
    }

    /**
     * Returns whether or not state is final
     * 
     * @return true if final, otherwise false
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Adds a transition 
     * 
     * @param onSymb
     * @param toState
     */
    public void addTransition(char onSymb, NFAState toState) {
        // TODO, we'll need to query the hashset to see if it exists.
        // If so, add toState state to list of transition states
        // If not, create set and add the toState

        //delta.put(onSymb, toState);
    }

    /**
     * Returns set of states of where this state can go given a symbol
     * 
     * @param symb
     * @return set of states if valid, error if cannot make transition
     */
    public Set<NFAState> getTo(char symb) {
        Set<NFAState> ret = delta.get(symb);
        if(ret == null){
            System.err.println("ERROR: DFAState.getTo(char symb) returns null on " + symb + " from " + name);
            System.exit(2);
        }
        return ret;
    }

}