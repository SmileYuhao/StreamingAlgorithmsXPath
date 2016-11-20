package NFA;

import java.util.HashMap;
import java.util.Map;

public class XpathToNFA {
    public static Map<Integer, NFANode> processXPath(String query) {
        Map<Integer, NFANode> nfaMap = new HashMap<>();
        int numberState = 0;
        // Descendant parameters
        String[] descendantParameters = query.substring(2).split("//");
        // Put state 0 into map
        NFANode firstNode = new NFANode(numberState++, false, false);
        nfaMap.put(firstNode.getStateNumber(), firstNode);
        for (String descendantParameter : descendantParameters) {
            String[] childParameters = descendantParameter.split("/");
            for (int i = 0; i < childParameters.length; i++) {
                // First element of child parameter
                if (i == 0) {
                    NFANode start = nfaMap.get(numberState - 1);
                    NFANode current = new NFANode(numberState++, false, true);
                    NFANode next = new NFANode(numberState++, false, false);
                    // Set transaction
                    NFATran startCurrent = new NFATran(current.getStateNumber(), "E");
                    NFATran currentNext = new NFATran(next.getStateNumber(), childParameters[i]);
                    // Add transaction
                    start.addTransitions(startCurrent);
                    current.addTransitions(currentNext);
                    // Add nfa node into list
                    nfaMap.put(start.getStateNumber(), start);
                    nfaMap.put(current.getStateNumber(), current);
                    nfaMap.put(next.getStateNumber(), next);
                } else {
                    NFANode current = nfaMap.get(numberState - 1);
                    NFANode next = new NFANode(numberState++, false, false);
                    NFATran currentNext = new NFATran(next.getStateNumber(), childParameters[i]);
                    current.addTransitions(currentNext);
                    nfaMap.put(next.getStateNumber(), next);
                }
            }
        }
        NFANode endState = nfaMap.get(numberState - 1);
        endState.setEndState(true);
        return nfaMap;
    }
}
