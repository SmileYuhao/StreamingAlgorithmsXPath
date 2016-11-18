package NFA;

import java.util.HashMap;
import java.util.Map;

public class XpathToNFA {
    public static Map<Integer, NFANode> processXPath(String query) {
        Map<Integer, NFANode> nfaMap = new HashMap<>();
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
                    NFATran startCurrent = new NFATran(current.getStateNumber(), "E");
                    NFATran currentNext = new NFATran(next.getStateNumber(), childParameters[j]);
                    // Consider reflexive relation
                    NFATran nextCurrent = new NFATran(current.getStateNumber(), "E");
                    // Add transaction
                    start.addTransitions(startCurrent);
                    current.addTransitions(currentNext);
                    next.addTransitions(nextCurrent);
                    // Add nfa node into list
                    nfaMap.put(start.getStateNumber(), start);
                    nfaMap.put(current.getStateNumber(), current);
                    temp = next;
                } else {
                    NFANode current = temp;
                    NFANode next = new NFANode(numberState++, false);
                    NFATran currentNext = new NFATran(next.getStateNumber(), childParameters[j]);
                    current.addTransitions(currentNext);
                    nfaMap.put(current.getStateNumber(), current);
                    temp = next;
                }
            }
        }
        NFANode endState = temp;
        endState.setEndState(true);
        nfaMap.put(endState.getStateNumber(), endState);
        return nfaMap;
    }
}
