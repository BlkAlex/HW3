package src;

import java.io.*;
import java.util.ArrayList;

class FileLoader {
    ArrayList<String> getListByFileName(String fileName) {
        ArrayList<String> linesFromFile = new ArrayList<>();
        FileReader fileReader;
        File myFile = new File(fileName);
        try {
            fileReader = new FileReader(myFile);
        } catch (FileNotFoundException ex) {
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
