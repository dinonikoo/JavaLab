import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the path to the file: ");
        String pathFile = scanner.nextLine();
        System.out.print("Please enter the path to the file with results. If it not exists, the file will be created in project root: ");
        String pathResult = scanner.nextLine();
        try {

            Algo test = new Algo(pathFile, pathResult);
        }
        catch (IOException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        finally {
            scanner.close();
        }
    }
}