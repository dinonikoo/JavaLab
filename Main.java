import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // ввод размера матрицы А
            System.out.print("Please enter rows and columns of matrix A: ");
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();

            if (rows*columns == 0)
            {
                throw new IllegalArgumentException("Rows or columns cannot be zero.");
            }

            complexmatrix A = new complexmatrix(rows, columns);

            // заполнение и печать матрицы А
            A.fillMatrix();
            A.printMatrix();

            // ввод размера матрицы В
            System.out.print("Please enter rows and columns of matrix B: ");
            rows = scanner.nextInt();
            columns = scanner.nextInt();

            if (rows*columns == 0)
            {
                throw new IllegalArgumentException("Rows or columns cannot be zero.");
            }

            complexmatrix B = new complexmatrix(rows,columns);

            // заполнение и печать матрицы В
            B.fillMatrix();
            B.printMatrix();

            // сумма
            System.out.println("A+B:");
            (A.add(B)).printMatrix();

            // разность
            System.out.println("A-B:");
            (A.subtract(B)).printMatrix();

            // произведение
            System.out.println("A*B:");
            (A.multiplication(B)).printMatrix();

            // определитель А
            System.out.println("A's determinant:");
            A.determinant().printComplex();
            System.out.println("");

            // определитель В
            System.out.println("B's determinant:");
            B.determinant().printComplex();
            System.out.println("");

            // обратная матрица А
            System.out.println("A^-1:");
            A.inverse().printMatrix();

            // обратная матрица В
            System.out.println("B^-1:");
            B.inverse().printMatrix();
        }
        catch (IllegalArgumentException | ArithmeticException e) {
        System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Wrong input. Only numbers are allowed.");
        }
        finally {
            scanner.close();
        }
    }
}