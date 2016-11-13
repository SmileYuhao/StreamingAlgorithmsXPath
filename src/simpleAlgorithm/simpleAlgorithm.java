package simpleAlgorithm;

import StreamingAlgorithmXPath.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class simpleAlgorithm implements StreamingAlgorithm {
    private String[] queryParameters;
    private LinkedList<XMLElement> seq;
    private List<Integer> nodeNumberList;
    private int nodeNumber;

    public simpleAlgorithm(String query) {
        nodeNumber = 0;
        this.queryParameters = getQueryParameters(query);
        seq = new LinkedList<>();
        nodeNumberList = new ArrayList<>();
    }

    private String[] getQueryParameters(String query) {
        return query.substring(2).split("/");
    }

    public void processXmlLine(String line) {
        // Create new xml element
        String[] lineParameters = line.split("\\s");
        int startEnd = Integer.parseInt(lineParameters[0]);
        String name = lineParameters[1];
        XMLElement xmlElement = new XMLElement(startEnd, name);

        // Get the top xml element
        XMLElement xmlElementPrev = seq.peek();

        // Check
        if (xmlElement.getStartEnd() == 1) {
            if (xmlElementPrev.getStartEnd() == 0 && xmlElementPrev.getName().equals(xmlElement.getName())) {
                seq.pop();
            }
        } else {
            seq.push(xmlElement);
            if (seq.size() >= queryParameters.length &&
                    xmlElement.getName().equals(queryParameters[queryParameters.length - 1])) {
                if (matchCheck()) {
                    nodeNumberList.add(nodeNumber);
                }
            }
            nodeNumber++;
        }
    }

    private boolean matchCheck() {
        boolean match = true;
        for (int i = 0; i < queryParameters.length; i++) {
            if (!queryParameters[queryParameters.length - 1 - i]
                    .equals(seq.get(i).getName())) {
                match = false;
                break;
            }
        }
        return match;
    }

    public String getNodeNumber() {
        return nodeNumberList.toString();
    }
}