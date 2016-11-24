package StreamingAlgorithmXPath;

public interface StreamingAlgorithm {
    void startDocument(String query);

    void processXmlLine(String line);

    String getNodeNumber();
}
