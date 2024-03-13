import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.*;

public class FileSaver { // BACKEND FOR SAVE SCREEN
    private static String filename; // receive filename from user and validate

    public static boolean validateInput(String fileName) {
        if (!checkNameValid(filename)) return false;
        return true;
    }
    
    private static boolean checkNameValid(String filename) {
        try {
            Path path = Paths.get("./" + filename + ".gol"); // try to create a path object (from Java.nio API)
        } catch (InvalidPathException e) {
            return false; // if path object not created (invalid path)
        } 
        return true;
    }

    public static void saveConfiguration(String filename, int size, Cell[][] cells) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("./"+ filename + ".gol");
               
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (cells[i][j].isAlive()) {
                        writer.print("o");
                    } else {
                        writer.print(".");
                    }
        
                    if (j % (size-1) == 0 && j != 0) {
                        writer.print("\n");
                    }
                }
            }

        writer.close();
        System.exit(0);
    }

    public void saveConfiguration2(String filename, int size, Cell[][] cells, int x, int y, int z, String description) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("./"+ filename + ".gol2");

        // Save metadata
        writer.println("x=" + x);
        writer.println("y=" + y);
        writer.println("z=" + z);
        writer.println("size=" + size);
        writer.println("description=" + description);

        // Save grid layout
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cells[i][j].isAlive()) {
                   writer.print("o");
                } else {
                   writer.print(".");
                }
                if (j % (size-1) == 0 && j != 0) {
                   writer.print("\n");
                }
            }
        }
        writer.close();
        System.exit(0);
    }
}
