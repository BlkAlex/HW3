package src;

import java.io.*;
import java.util.ArrayList;

public class FileLoader {
    public ArrayList<String> getListByFileName(String fileName){
        ArrayList<String> linesFromFile = new ArrayList<>();
        FileReader fr;
        //try {
            File myfile = new File(fileName);
        try {
            fr = new FileReader(myfile);
        }
        catch (FileNotFoundException ex){
            return linesFromFile;
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty())
                    linesFromFile.add(line);
            }
        }
        catch (IOException ex){
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
