public class complex {
    private double real;
    private double imaginary;

    complex() {
        this.real = 0;
        this.imaginary = 0;
    }

    complex(double n) {
        this.real = n;
        this.imaginary = 0;
    }

    complex(double n, double m) {
        this.real = n;
        this.imaginary = m;
    }

    public complex add(complex second) {
        return new complex(this.real + second.real, this.imaginary + second.imaginary);
    }

    public complex subtract(complex second) {
        return new complex(this.real - second.real, this.imaginary - second.imaginary);
    }

    public complex multiplication(complex second) {
        // newcompex = real1*real2 + i1*real2 + real1*i2 + i1*i2;
        double tempreal = this.real * second.real - this.imaginary * second.imaginary;
        double tempimaginary = this.imaginary * second.real + second.imaginary * this.real;
        return new complex(tempreal, tempimaginary);
    }

    public complex division(complex second) {
        double tempreal = (this.real * second.real + this.imaginary * second.imaginary) /
                ((second.real)*(second.real) + (second.imaginary)*(second.imaginary));
        double tempimaginary = (this.imaginary * second.real - this.real * second.imaginary) /
                ((second.real)*(second.real) + (second.imaginary)*(second.imaginary));
        return new complex(tempreal, tempimaginary);
    }

    public double getReal() {
        return real;
    }


    public double getImaginary() {
        return imaginary;
    }

    public void printComplex()
    {
        if (this.getImaginary() == 0)
            System.out.print(this.getReal());
        else if (this.getImaginary() > 0)
            System.out.print(this.getReal() + "+" +
                    this.getImaginary() + "i" );
        else System.out.print(this.getReal() + "-" +
                    (-1)*this.getImaginary() + "i" );
    }
}
