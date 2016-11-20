package NFA;

import java.util.ArrayList;
import java.util.List;

public class NFANode {
    private int stateNumber;
    private boolean endState;
    private boolean selfTran;
    private List<NFATran> transitions;

    /**
     * @param stateNumber State's number
     * @param endState    Whether is end state
     * @param selfTran    Whether it can go to itself
     */
    public NFANode(int stateNumber, boolean endState, boolean selfTran) {
        this.stateNumber = stateNumber;
        this.endState = endState;
        this.selfTran = selfTran;
        this.transitions = new ArrayList<>();
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

    public boolean isSelfTran() {
        return selfTran;
    }

    public List<NFATran> getTransitions() {
        return transitions;
    }

    public void addTransitions(NFATran nfaTran) {
        this.transitions.add(nfaTran);
    }
}