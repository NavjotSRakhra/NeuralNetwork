package io.github.NavjotSRakhra.matrix;

import junit.framework.TestCase;

import java.util.Arrays;

public class MatrixTest extends TestCase {

    public void testAdd() {
        int a = 200, b = 500;
        Matrix mA = new Matrix(a, b, true);
        Matrix mB = new Matrix(a, b, true);

        double x[][] = mA.getMatrix();
        double y[][] = mB.getMatrix();

        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                x[i][j] += y[i][j];
            }
        }
        mA.add(mB);
        assertTrue(Arrays.deepEquals(x, mA.getMatrix()));

        Matrix d = new Matrix(1, 2, true);
        try {
            d.add(null);
        } catch (Exception e) {
            assertTrue(e instanceof NullPointerException);
        }

    }

    public void testMultiply() {
        Matrix a = new Matrix(4, 2, true);
        Matrix b = new Matrix(2, 1, true);
        System.out.println(Matrix.multiply(a, b));
    }
}