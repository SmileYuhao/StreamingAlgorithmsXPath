package DFA;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DFANode {
    private int stateNumber;
    // If one of the NFA states is endState
    private boolean endState;
    // A corresponding set of states from the NFA
    private Set<Integer> nfaStates;
    // key: Input symbol, value: Next DFA number
    private Map<String, Integer> transitions;

    public DFANode(int stateNumber, boolean endState, Set<Integer> nfaNodes) {
        this.stateNumber = stateNumber;
        this.endState = endState;
        this.nfaStates = nfaNodes;
        this.transitions = new HashMap<>();
    }

    public int getStateNumber() {
        return stateNumber;
    }

    public boolean isEndState() {
        return endState;
    }

    public void setEndState(boolean endState) {
        this.endState = endState;
    }

    public Set<Integer> getNfaStates() {
        return nfaStates;
    }

    public void addTran(String transSymbol, Integer nextState) {
        this.transitions.put(transSymbol, nextState);
    }

    public Integer getNextState(String qName) {
        if (transitions.containsKey(qName))
            return transitions.get(qName);
        else
            return null;
    }
}