import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class  FileHandling {
    // read lines from a file

    public static ArrayList<String> wholeFileRead(String filename) {
        ArrayList<String> fileOutput = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();

            while (line != null) {
                fileOutput.add(line);
                line = br.readLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return fileOutput;
    }
}
