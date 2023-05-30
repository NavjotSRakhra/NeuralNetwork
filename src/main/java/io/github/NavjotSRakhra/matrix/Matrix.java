package io.github.NavjotSRakhra.matrix;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class Matrix implements Cloneable {
    private double[][] matrix;

    public Matrix(double[][] matrix, boolean isRandomized) {
        Objects.requireNonNull(matrix);

        this.matrix = matrix.clone();
        if (isRandomized) randomizeValues();
    }

    public Matrix(int a, int b, boolean isRandomized) {
        this(new double[a][b], isRandomized);
    }


    public void add(final double n) {
        IntStream.range(0, matrix.length).parallel().forEach(i -> IntStream.range(0, matrix[0].length).parallel().forEach(j -> matrix[i][j] += n));
    }

    public void add(Matrix matrix) {
        Objects.requireNonNull(matrix);
        if (matrix.matrix.length != this.matrix.length || matrix.matrix[0].length != this.matrix[0].length)
            throw new IllegalArgumentException("Dimensions of both matrices need to be same");

        int m = this.matrix.length, n = this.matrix[0].length;
        IntStream.range(0, m).parallel().forEach(i -> IntStream.range(0, n).parallel().forEach(j -> Matrix.this.matrix[i][j] = matrix.matrix[i][j]));
    }

    public void multiply(final double n) {
        IntStream.range(0, matrix.length).parallel().forEach(i -> IntStream.range(0, matrix[0].length).parallel().forEach(j -> matrix[i][j] *= n));
    }

    public void multiply(final Matrix matrix) {
        Objects.requireNonNull(matrix);
        if (this.matrix[0].length != matrix.matrix.length) throw new IllegalArgumentException();

        int m = this.matrix.length;
        int n = matrix.matrix[0].length;

        double[][] tempMatData = new double[m][n];

        IntStream.range(0, m).parallel().forEach(i -> IntStream.range(0, n).parallel().forEach(j -> IntStream.range(0, Matrix.this.matrix.length).parallel().forEach(k -> tempMatData[i][j] += Matrix.this.matrix[i][k] * matrix.matrix[k][j])));
        this.matrix = tempMatData;
    }

    public double[][] getMatrix() {
        return matrix.clone();
    }

    @Override
    public Matrix clone() {
        try {
            Matrix clone = (Matrix) super.clone();
            clone.matrix = matrix.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Matrix)) return false;
        return Arrays.deepEquals(matrix, ((Matrix) obj).matrix);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[[ ");
        for (double[] doubles : matrix) {
            for (int i = 0; i < doubles.length - 1; i++) {
                str.append(doubles[i]);
                str.append(", ");
            }
            str.append(doubles[doubles.length - 1]);
            str.append("], ");
        }
        str.replace(str.length() - 2, str.length(), "]");
        return str.toString();
    }

    private void randomizeValues() {
        Random random = new Random();

        IntStream.range(0, matrix.length).parallel().forEach(i -> IntStream.range(0, matrix[0].length).parallel().forEach(j -> matrix[i][j] = random.nextDouble()));
    }
}
