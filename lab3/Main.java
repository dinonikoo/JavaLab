import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {

            System.out.print("Введите ФИО через пробел: ");
            String name = scanner.nextLine();
            System.out.print("Введите дату рождения, разделяя числа точкой: ");
            String date = scanner.nextLine();
            Algo test = new Algo(name,date);

        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        finally {
            scanner.close();
        }
    }
}