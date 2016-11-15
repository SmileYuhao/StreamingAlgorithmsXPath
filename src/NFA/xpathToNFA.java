package NFA;

import java.util.HashMap;

public class XpathToNFA {
    private HashMap<Integer, NFANode> nfaMap;

    public XpathToNFA() {
        this.nfaMap = new HashMap<>();
    }

    public void processXPath(String query) {
        int numberState = 0;
        // Descendant parameters
        String[] descendantParameters = query.substring(2).split("//");
        NFANode temp = new NFANode();
        for (int i = 0; i < descendantParameters.length; i++) {
            String[] childParameters = descendantParameters[i].split("/");
            for (int j = 0; j < childParameters.length; j++) {
                // First element of child parameter
                if (j == 0) {
                    NFANode start;
                    if (i == 0) {
                        // First element of query
                        start = new NFANode(numberState++, false);
                    } else {
                        start = temp;
                    }
                    NFANode current = new NFANode(numberState++, false);
                    NFANode next = new NFANode(numberState++, false);
                    // Set transaction
                    NFATrans startCurrent = new NFATrans(current.getStateNumber(), "E");
                    NFATrans currentNext = new NFATrans(next.getStateNumber(), childParameters[j]);
                    // Add transaction
                    start.addTransitions(startCurrent);
                    current.addTransitions(currentNext);
                    // Add nfa node into list
                    this.nfaMap.put(start.getStateNumber(), start);
                    this.nfaMap.put(current.getStateNumber(), current);
                    temp = next;
                } else {
                    NFANode current = temp;
                    NFANode next = new NFANode(numberState++, false);
                    NFATrans currentNext = new NFATrans(next.getStateNumber(), childParameters[j]);
                    current.addTransitions(currentNext);
                    this.nfaMap.put(current.getStateNumber(), current);
                }
            }
        }
        NFANode endState = temp;
        endState.setEndState(true);
        this.nfaMap.put(endState.getStateNumber(), endState);
    }

    public String getNfaMap() {
        return nfaMap.toString();
    }
}
