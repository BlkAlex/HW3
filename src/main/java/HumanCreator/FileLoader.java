package HumanCreator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileLoader {
    public static ArrayList<String> getListByFileName(String fileName) {
        ArrayList<String> linesFromFile = new ArrayList<>();
        InputStreamReader fileReader;
        try {
            fileReader = new InputStreamReader(new FileInputStream(fileName),
                    StandardCharsets.UTF_8);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return linesFromFile;
        }
        BufferedReader reader = new BufferedReader(fileReader);
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty())
                    linesFromFile.add(line);
            }
        } catch (IOException ex) {
            return linesFromFile;
        }

        try {
            reader.close();
        } catch (IOException e) {
            return linesFromFile;
        }
        return linesFromFile;
    }
}
