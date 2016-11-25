package StreamingAlgorithmXPath;

public class parserCommand {
    public static int queryType(String query) {
        return query.substring(2).contains("//") ? 0 : 1;
    }
}