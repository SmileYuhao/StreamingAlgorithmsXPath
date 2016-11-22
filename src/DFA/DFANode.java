package DFA;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DFANode {
    private int stateNumber;
    private boolean endState;
    private Set<Integer> nfaStates;
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
