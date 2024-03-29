package com.github.gbaso.newtonfractals;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.FieldElement;

public record Complex(double real, double imaginary) implements FieldElement<Complex> {

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex I = new Complex(0, 1);

    private double abs2() {
        return real * real + imaginary * imaginary;
    }

    public double abs() {
        return Math.sqrt(abs2());
    }

    @Override
    public Complex negate() {
        return new Complex(-real, -imaginary);
    }

    public Complex conjugate() {
        return new Complex(real, -imaginary);
    }

    @Override
    public Complex reciprocal() {
        return conjugate().divide(abs2());
    }

    @Override
    public Complex add(Complex other) {
        return new Complex(this.real + other.real, this.imaginary + other.imaginary);
    }

    @Override
    public Complex subtract(Complex other) {
        return this.add(other.negate());
    }

    public Complex multiply(double factor) {
        return new Complex(real * factor, imaginary * factor);
    }

    public Complex divide(double divisor) {
        return new Complex(real / divisor, imaginary / divisor);
    }

    @Override
    public Complex multiply(Complex other) {
        return new Complex(this.real * other.real - this.imaginary * other.imaginary, this.real * other.imaginary + other.real * this.imaginary);
    }

    @Override
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

    @Override
    public Complex multiply(int n) {
        return multiply((double) n);
    }

    @Override
    public Field<Complex> getField() {
        return ComplexField.INSTANCE;
    }

    private static class ComplexField implements Field<Complex> {

        private static final ComplexField INSTANCE = new ComplexField();

        @Override
        public Complex getZero() {
            return Complex.ZERO;
        }

        @Override
        public Complex getOne() {
            return Complex.ONE;
        }

        @Override
        public Class<? extends FieldElement<Complex>> getRuntimeClass() {
            return Complex.class;
        }

    }

}
