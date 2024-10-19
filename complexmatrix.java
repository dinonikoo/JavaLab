import java.util.Scanner;
import java.util.InputMismatchException;

public class complexmatrix {
    private complex[][] matrix;
    final private int rows;
    final private int columns;

    complexmatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new complex[rows][columns];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.setElement(i, j, 0, 0);
            }
        }
    }

    complexmatrix(int n) {
        this.rows = n;
        this.columns = n;
        this.matrix = new complex[rows][columns];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.setElement(i, j, 0, 0);
            }
        }
    }

    public complexmatrix add(complexmatrix second) {

        if ((this.columns != second.columns)&&(this.rows != second.rows)) {
            throw new IllegalArgumentException("Cannot add: different matrix sizes.");
        }

        complexmatrix temp = new complexmatrix(this.rows, this.columns);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                temp.matrix[i][j] = (this.matrix[i][j]).add(second.matrix[i][j]);
            }
        }
        return temp;
    }

    public complexmatrix subtract(complexmatrix second) {

        if ((this.columns != second.columns)&&(this.rows != second.rows)) {
            throw new IllegalArgumentException("Cannot subtract: different matrix sizes.");
        }

        complexmatrix temp = new complexmatrix(this.rows, this.columns);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                temp.matrix[i][j] = (this.matrix[i][j]).subtract(second.matrix[i][j]);
            }
        }
        return temp;
    }

    public complexmatrix multiplication(complexmatrix second) {

        if (this.columns != second.rows) {
            throw new IllegalArgumentException("Cannot multiply: wrong matrix sizes.");
        }

        complexmatrix temp = new complexmatrix(this.rows, second.columns);

        for (int i = 0; i < temp.rows; i++) {
            for (int j = 0; j < temp.columns; j++)
            {
                temp.matrix[i][j] = new complex(0);
            }
        }

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < second.columns; j++) {
                for (int k = 0; k < this.columns; k++) {
                    temp.matrix[i][j] = (temp.matrix[i][j]).add((this.matrix[i][k]).
                            multiplication(second.matrix[k][j]));
                }
            }
        }

        return temp;
    }

    private void setElement(int i, int j, double real, double imaginary) {
        this.matrix[i][j] = new complex(real, imaginary);
    }

    public void fillMatrix() {
        boolean validElements = true;
        Scanner scanner = new Scanner(System.in);
        while (validElements) {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.columns; j++) {
                    try {
                        System.out.print("Enter [" + i + "][" + j + "] element's real and imaginary parts: ");
                        double real = scanner.nextDouble();
                        double imaginary = scanner.nextDouble();
                        this.setElement(i, j, real, imaginary);
                    } catch (InputMismatchException e) {
                        System.out.println("Wrong input. Only numbers are allowed.");
                        validElements = false;
                    }
                }
            }
        }
    }

    //

    public void printMatrix() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.matrix[i][j].printComplex();
                System.out.print("    ");
            }
            System.out.println("");
        }
    }

    public complex determinant() {

        if (this.rows != this.columns) {
            throw new IllegalArgumentException("Cannot calculate the determinant for a non-square matrix.");
        }

        int n = this.rows;

        if (n == 1) {
            return this.matrix[0][0];
        }

        if (n == 2) {
            return ((this.matrix[0][0]).multiplication(this.matrix[1][1])).
                    subtract((this.matrix[0][1]).multiplication(this.matrix[1][0]));
        }

        complex det = new complex(0);

        for (int i = 0; i < n; i++) {

            complexmatrix subMatrix = getSubMatrix(0, i);

            int sign_int = (i % 2 == 0) ? 1 : -1;
            complex sign = new complex(sign_int);

            det = (det).add((sign.multiplication(this.matrix[0][i])).
                    multiplication(subMatrix.determinant()));
        }

        return det;
    }

    private complexmatrix getSubMatrix(int excludingRow, int excludingCol) {
        int n = this.rows;
        complexmatrix subMatrix = new complexmatrix(n-1);
        int r = -1;

        for (int i = 0; i < n; i++) {
            if (i == excludingRow) {
                continue;
            }
            r++;
            int c = -1;
            for (int j = 0; j < n; j++) {
                if (j == excludingCol) {
                    continue;
                }
                subMatrix.matrix[r][++c] = this.matrix[i][j];
            }
        }

        return subMatrix;
    }


    public complexmatrix inverse() {

        if (this.rows != this.columns) {
            throw new IllegalArgumentException("Cannot find an inversed matrix for non-square matrix.");
        }

        int n = this.rows;
        complexmatrix augmentedMatrix = new complexmatrix(n, n*2);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix.matrix[i][j] = this.matrix[i][j];
            }
            augmentedMatrix.matrix[i][i + n] = new complex(1);
        }

        for (int i = 0; i < n; i++) {
            if ( (augmentedMatrix.matrix[i][i].getReal() == 0)
             && (augmentedMatrix.matrix[i][i].getImaginary() == 0) ){
                boolean swapped = false;
                for (int j = i + 1; j < n; j++) {
                    if ( (augmentedMatrix.matrix[j][j].getReal() != 0)
                            && (augmentedMatrix.matrix[j][j].getImaginary() != 0) ) {
                        swapRows(augmentedMatrix, i, j);
                        swapped = true;
                        break;
                    }
                }
                if (!swapped) {
                    throw new ArithmeticException("There is no inversed matrix.");
                }
            }

            complex diagonalElement = augmentedMatrix.matrix[i][i];
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix.matrix[i][j] = (augmentedMatrix.matrix[i][j]).
                        division(diagonalElement);
            }

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    complex factor = augmentedMatrix.matrix[j][i];
                    for (int k = 0; k < 2 * n; k++) {
                        augmentedMatrix.matrix[j][k] = (augmentedMatrix.matrix[j][k]).
                                subtract(factor.multiplication(augmentedMatrix.matrix[i][k]));
                    }
                }
            }
        }

        complexmatrix inverseMatrix = new complexmatrix(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverseMatrix.matrix[i][j] = augmentedMatrix.matrix[i][j + n];
            }
        }

        return inverseMatrix;
    }

    private void swapRows(complexmatrix m, int row1, int row2) {
        complex[] temp = m.matrix[row1];
        m.matrix[row1] = m.matrix[row2];
        m.matrix[row2] = temp;
    }

}
