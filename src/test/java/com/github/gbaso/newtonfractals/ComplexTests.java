package com.github.gbaso.newtonfractals;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractDoubleAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ComplexTests {

    @Test
    void string() {
        assertThat(Complex.ZERO).hasToString("(0.0, 0.0)");
        assertThat(Complex.ONE).hasToString("(1.0, 0.0)");
        assertThat(Complex.I).hasToString("(0.0, 1.0)");
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0,  0
            1,  0
            0,  1
            1,  1
            """)
    void components(double x, double y) {
        var z = new Complex(x, y);
        assertThat(z.real()).isEqualTo(x);
        assertThat(z.imaginary()).isEqualTo(y);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                    0,          0,          0
                    1,          0,          1
                    0,          1,          1
                    0.7071068,  0.7071068,  1
                    3,          4,          5
                    -2,         -7,         7.2801099
            """)
    void abs(double x, double y, double expected) {
        var z = new Complex(x, y);
        assertThat(z.abs() - expected).isLessThan(0.00001);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0,  0
            1,  0
            0,  1
            1,  1
            """)
    void negate(double x, double y) {
        var z = new Complex(x, y);
        var w = z.negate();
        assertThat(w.real()).isEqualTo(-x);
        assertThat(w.imaginary()).isEqualTo(-y);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0,  0,  1,  0,  1,  0
            1,  0,  1,  1,  2,  1
            0,  1,  1,  0,  1,  1
            1,  1,  1,  1,  2,  2
            """)
    void add(double x1, double y1, double x2, double y2, double rx, double ry) {
        var z1 = new Complex(x1, y1);
        var z2 = new Complex(x2, y2);
        var expected = new Complex(rx, ry);
        assertThat(z1.add(z2)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0,  0,  1,  0,  1,  0
            1,  0,  1,  1,  2,  1
            0,  1,  1,  0,  1,  1
            1,  1,  1,  1,  2,  2
            """)
    void aboutEquals(double x1, double y1, double x2, double y2, double rx, double ry) {
        var z1 = new Complex(x1, y1);
        var z2 = new Complex(x2, y2);
        var expected = new Complex(rx, ry);
        assertAboutEquals(z1.add(z2), expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                    0,          0,          0,          0
                    1,          0,          1,          0
                    0,          1,          -1,         0
                    0.7071068,  0.7071068,  0,          1
                    3,          4,          -7,         24
                    -2,         -7,         -45,        28
            """)
    void square(double x, double y, double rx, double ry) {
        var z = new Complex(x, y);
        var expected = new Complex(rx, ry);
        assertAboutEquals(z.multiply(z), expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                    1,          0,          1,          0
                    0,          1,          0,          -1
                    0.7071068,  0.7071068,  0.7071068,  -0.7071068
                    3,          4,          0.12,        -0.16
                    -2,         -7,         -0.037736,  0.13207548
            """)
    void reciprocal(double x, double y, double rx, double ry) {
        var z = new Complex(x, y);
        var expected = new Complex(rx, ry);
        assertAboutEquals(z.reciprocal(), expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
                    0,          0,          0,          0,          0
                    0,          0,          1,          0,          0
                    1,          0,          0,          0,          0
                    1,          0,          1,          1,          0
                    0,          1,          0,          0,          0
                    0,          1,          1,          0,          1
                    0.7071068,  0.7071068,  1.4142135,  1,          1
                    3,          4,          -2,         -6,         -8
                    -2,         -7,         0.2,        -0.4,       -1.4
            """)
    void multiply(double x, double y, double factor, double rx, double ry) {
        var z = new Complex(x, y);
        var expected = new Complex(rx, ry);
        assertAboutEquals(z.multiply(factor), expected);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            0,  0,  1,  0,  0,          0
            1,  0,  1,  1,  0.5,        -0.5
            0,  1,  1,  0,  0,          1
            1,  1,  1,  1,  1,          0
            3,  4,  -2, -7, -0.6415094, 0.2452830
            """)
    void divide(double x1, double y1, double x2, double y2, double rx, double ry) {
        var z1 = new Complex(x1, y1);
        var z2 = new Complex(x2, y2);
        var expected = new Complex(rx, ry);
        assertAboutEquals(z1.divide(z2), expected);
    }

    AbstractDoubleAssert<?> assertAboutEquals(Complex actual, Complex expected) {
        return assertThat(diff(actual, expected)).isLessThan(0.0001);
    }

    double diff(Complex z1, Complex z2) {
        return z1.subtract(z2).abs();
    }

}
