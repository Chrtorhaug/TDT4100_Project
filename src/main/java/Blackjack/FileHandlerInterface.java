package Blackjack;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileHandlerInterface {

    public List<List<String>> readFile(String filename, String splitter) throws FileNotFoundException;
    public void writeToFile(String filename, String write, boolean append) throws FileNotFoundException;
}
