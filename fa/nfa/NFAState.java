package fa.nfa;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

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
    public NFAState(String name, boolean flag) {
        this.name = name;
        delta = new HashMap<Character, Set<NFAState>>();
        isFinal = flag;
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
        // If not, create set 
        if(delta.get(onSymb) == null){
            Set<NFAState> states = new HashSet<NFAState>();
            delta.put(onSymb, states);
        }

        // If so, add toState state to list of transition states
        Set<NFAState> transitions = delta.get(onSymb);
        
        if(!transitions.contains(toState)){
            transitions.add(toState);
        }
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
            ret = new HashSet<NFAState>();
        }
        return ret;
    }

}