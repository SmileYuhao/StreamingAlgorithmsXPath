package LazyDFA;

import DFA.DFANode;
import NFA.NFANode;
import NFA.NFATran;
import NFA.XpathToNFA;
import StreamingAlgorithmXPath.StreamingAlgorithm;

import java.util.*;


public class LazyDFAAlgorithm implements StreamingAlgorithm {
    /**
     * dfaNodeNumber: DFA node number
     * nodeNumber: XML element number
     * nfaNodeMap: key: nfa node number, value: nfa node
     * dfaNodeMap: key: dfa node number, value: dfa node
     * dfaNodeStack: Ancestors of the current node
     * nodeNumberList: Number of nodes which are matched
     */
    private int dfaNodeNumber;
    private int nodeNumber;
    private Map<Integer, NFANode> nfaNodeMap;
    private Stack<Integer> dfaNodeStack;
    private Map<Integer, DFANode> dfaNodeMap;
    private List<Integer> nodeNumberList;


    public LazyDFAAlgorithm() {
        this.dfaNodeNumber = 0;
        this.nodeNumber = 0;
        this.dfaNodeStack = new Stack<>();
        this.dfaNodeMap = new HashMap<>();
        this.nodeNumberList = new ArrayList<>();
    }

    public void processXmlLine(String line) {
        String[] lineParameters = line.split("\\s");
        int startEnd = Integer.parseInt(lineParameters[0]);
        String qName = lineParameters[1];
        if (startEnd == 0) {
            if (qName.equals("c"))
                startElement(qName);
            else
                startElement(qName);
        } else {
            endElement();
        }
    }

    /**
     * Initialises nfa states and dfa state.
     *
     * @param query XPath query
     */
    public void startDocument(String query) {
        nfaNodeMap = XpathToNFA.processXPath(query);
        // Create the start state of the DFA by taking the E-closure of the start state of the NFA
        Set<Integer> nfaStarts = new HashSet<>();
        eClosure(0, nfaStarts);
        DFANode dfaStart = new DFANode(dfaNodeNumber++, false, nfaStarts);
        dfaNodeStack.push(dfaStart.getStateNumber());
        dfaNodeMap.put(dfaStart.getStateNumber(), dfaStart);
    }

    private void startElement(String qName) {
        DFANode currentDfaNode = dfaNodeMap.get(dfaNodeStack.peek());
        Integer nextState = currentDfaNode.getNextState(qName);
        if (nextState != null) {
            // Have already processed this dfa node with this input symbol
            dfaNodeStack.push(nextState);
            // Check match
            matchCheck(dfaNodeMap.get(nextState));
        } else {
            Set<Integer> nfaStates = currentDfaNode.getNfaStates();
            Set<Integer> nextNfaStates = new HashSet<>();
            // Get states's number which are reachable by one transition
            for (Integer nfaState : nfaStates) {
                nextNfaStates.addAll(move(nfaState, qName));
            }
            if (nextNfaStates.size() == 0) {
                // Current dfa node can't move to another node with this element.
                dfaNodeStack.push(currentDfaNode.getStateNumber());
            } else {
                // Get E-transition states
                Set<Integer> eTransNfaStates = new HashSet<>();
                for (Integer state : nextNfaStates) {
                    eClosure(state, eTransNfaStates);
                }
                nextNfaStates.addAll(eTransNfaStates);
                // Create next dfa node
                DFANode nextDfaNode = new DFANode(dfaNodeNumber++, false, nextNfaStates);
                // Put next dfa state into map and stack
                dfaNodeMap.put(nextDfaNode.getStateNumber(), nextDfaNode);

                dfaNodeStack.push(nextDfaNode.getStateNumber());
                // Update current dfa node transition
                currentDfaNode.addTran(qName, nextDfaNode.getStateNumber());
                // Check match
                matchCheck(nextDfaNode);
            }
        }
        nodeNumber++;
    }

    private void endElement() {
        dfaNodeStack.pop();
    }

    /**
     * Finish states of the DFA are those which contain any of the finish states of the NFA.
     */
    private void matchCheck(DFANode dfaNode) {
        if (dfaNode.isEndState()) {
            nodeNumberList.add(nodeNumber);
        } else {
            Set<Integer> nfaStates = dfaNode.getNfaStates();
            for (Integer nfaState : nfaStates) {
                if (nfaNodeMap.get(nfaState).isEndState()) {
                    dfaNode.setEndState(true);
                    nodeNumberList.add(nodeNumber);
                    break;
                }
            }
        }
    }

    /**
     * Takes a state and returns the set of states reachable from it based on (one or more) E-transitions
     *
     * @param nfaState    A nfa node number
     * @param nfaStateSet A nfa node number set
     * @return The set of nfa node number
     */
    private Set<Integer> eClosure(Integer nfaState, Set<Integer> nfaStateSet) {
        NFANode nfaNode = nfaNodeMap.get(nfaState);
        List<NFATran> nfaTrans = nfaNode.getTransitions();
        nfaStateSet.add(nfaNode.getStateNumber());
        for (NFATran nfaTran : nfaTrans) {
            if (nfaTran.getTransSymbol().equals("E")) {
                eClosure(nfaTran.getNextStateNumber(), nfaStateSet);
            }
        }
        return nfaStateSet;
    }

    /**
     * Takes a state and a character, and returns the set of states reachable by <b>one</b> transition on this character.
     *
     * @param nfaState A nfa state's number
     * @param qName    XML input element's name
     * @return The set of nfa node number
     */
    private Set<Integer> move(Integer nfaState, String qName) {
        NFANode nfaNode = nfaNodeMap.get(nfaState);
        Set<Integer> nfaNextStateSet = new HashSet<>();

        if (nfaNode.isSelfTran()) {
            nfaNextStateSet.add(nfaNode.getStateNumber());
        }
        List<NFATran> nfaTrans = nfaNode.getTransitions();
        for (NFATran nfaTran : nfaTrans) {
            if (nfaTran.getTransSymbol().equals(qName)) {
                nfaNextStateSet.add(nfaTran.getNextStateNumber());
            }
        }
        return nfaNextStateSet;
    }

    public String getNodeNumber() {
        StringBuilder result = new StringBuilder();
        for (Integer nodeNumber : nodeNumberList)
            result.append(nodeNumber).append("\n");
        return result.toString();
    }
}
