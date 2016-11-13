package StreamingAlgorithmXPath;

public class XMLElement {
    private int startEnd;
    private String name;

    /**
     * @param startEnd 0:start, 1:end
     * @param name     element's name
     */
    public XMLElement(int startEnd, String name) {
        this.startEnd = startEnd;
        this.name = name;
    }

    public int getStartEnd() {
        return this.startEnd;
    }

    public String getName() {
        return this.name;
    }
}
