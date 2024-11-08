import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class Algo {

    private final BufferedReader fileToRead;
    private final BufferedWriter fileToWrite;
    private final TreeMap<Character, Integer> frequency = new TreeMap<Character, Integer>(); // чтобы поддерживать отсортированность


    public Algo(String read, String write) throws IOException {
        if (read.equals(write)) {
            throw new IllegalArgumentException("These paths can't be the same.");
        }
        if (read == "" || write == "") {
            throw new IllegalArgumentException("Paths can't be empty.");
        }
        fileToRead = new BufferedReader(new FileReader(read));
        fileToWrite = new BufferedWriter(new FileWriter(write));
        countingSymbols();
        writeToFile();
        closeFiles();
    }

    private void countingSymbols() throws IOException {
        String line = fileToRead.readLine();
        while (line != null) {
            for (int i = 0; i<line.length(); i++) {
                if (this.frequency == null || !frequency.containsKey(line.charAt(i))) {
                    frequency.put(line.charAt(i), 1);
                }
                else {
                    frequency.put(line.charAt(i), frequency.get(line.charAt(i)) + 1);
                }
            }
            line = fileToRead.readLine();
        }
    }

    private void writeToFile() throws IOException {
        String line = "";
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            line = (entry.getKey() + " : " + entry.getValue());
            fileToWrite.write(line);
            fileToWrite.newLine();
        }
    }

    private void closeFiles() throws IOException {
        if (fileToRead != null) {
            fileToRead.close();
        }
        if (fileToWrite != null) {
            fileToWrite.close();
        }
    }
}
