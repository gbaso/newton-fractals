package com.github.gbaso.newtonfractals;

public record Complex(double real, double imaginary) {

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex I = new Complex(0, 1);

    private double abs2() {
        return real * real + imaginary * imaginary;
    }

    public double abs() {
        return Math.sqrt(abs2());
    }

    public Complex negate() {
        return new Complex(-real, -imaginary);
    }

    public Complex conjugate() {
        return new Complex(real, -imaginary);
    }

    public Complex reciprocal() {
        return conjugate().divide(abs2());
    }

    public Complex add(Complex other) {
        return new Complex(this.real + other.real, this.imaginary + other.imaginary);
    }

    public Complex subtract(Complex other) {
        return this.add(other.negate());
    }

    public Complex multiply(double factor) {
        return new Complex(real * factor, imaginary * factor);
    }

    public Complex divide(double divisor) {
        return new Complex(real / divisor, imaginary / divisor);
    }

    public Complex multiply(Complex other) {
        return new Complex(this.real * other.real - this.imaginary * other.imaginary, this.real * other.imaginary + other.real * this.imaginary);
    }

    public Complex divide(Complex other) {
        return this.multiply(other.reciprocal());
    }

    @Override
    public String toString() {
        return "(" + real + ", " + imaginary + ")";
    }

    public static boolean equals(Complex z1, Complex z2, double eps) {
        return Math.abs(z1.real - z2.real) <= eps && Math.abs(z1.imaginary - z2.imaginary) <= eps;
    }

}
