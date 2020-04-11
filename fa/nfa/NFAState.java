package fa.nfa;

/**
 * Class that represents a single state in a non-deterministic finite automata.
 * Extends the functionality of the abstract State class. Used by NFA instances 
 * to represent the IO nature of states in finite state machines.
 * 
 * @author Juan Becerra
 * @author Keegan Saunders
 */
public class NFAState extends fa.State {

    // Hash code used in 
    private final int hashCode;

    /**
     * Constructs a DFAState object given a unique name.
     * initializes a hash code based on the name.
     * 
     * @param name
     */
    public NFAState(String name) {
        this.name = name;
        this.hashCode = name.hashCode();
    }

    /**
     * Returns a hashcode based on the DFAState's name
     * 
     * This is used to ensure that there are never duplicate states in DFA's sets
     * 
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     * Returns whether or not two DFAState objects are the same, based on their names.
     * 
     * This is used to ensure that there are never duplicate states in DFA's sets
     * 
     * @param DFAState object
     */
    @Override
    public boolean equals(Object object) {
        NFAState otherState = (NFAState) object;
        return this.hashCode == otherState.hashCode();
    }

}