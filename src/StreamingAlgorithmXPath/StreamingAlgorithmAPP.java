package StreamingAlgorithmXPath;

import LazyDFA.LazyDFAAlgorithm;
import SimpleQuery.SimpleQueryAlgorithm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class StreamingAlgorithmAPP {
    public static void main(String[] args) throws IOException {
        // Measure execution time
        // long startTime = System.nanoTime();

        // Read args
        String filePath = args[0];
        String query = args[1];
        // Determine query type
        StreamingAlgorithm streamingAlgorithm;
        if (parserCommand.queryType(query) == 0)
            streamingAlgorithm = new LazyDFAAlgorithm();
        else
            streamingAlgorithm = new SimpleQueryAlgorithm();
        // Start document
        streamingAlgorithm.startDocument(query);
        // Read file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                streamingAlgorithm.processXmlLine(line);
            }
        }
        System.out.println(streamingAlgorithm.getNodeNumber());
        /*
        // Measure execution time
        long stopTime = System.nanoTime();

        System.out.println("Execution time(S): " + (stopTime - startTime) / 1000000000.0);
        // Measure memory usage
        Runtime rt = Runtime.getRuntime();
        rt.gc();
        long usedBytes = (rt.totalMemory() - rt.freeMemory());
        System.out.println("Memory Usage(MB): " + (double) usedBytes / (1024 * 1024));
        */
    }
}