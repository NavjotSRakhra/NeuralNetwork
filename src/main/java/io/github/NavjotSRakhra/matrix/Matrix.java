package io.github.NavjotSRakhra.matrix;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class Matrix implements Cloneable, Serializable {
    private double[][] matrix;

    private Matrix() {
    }

    public Matrix(double[][] matrix, boolean isRandomized) {
        Objects.requireNonNull(matrix);

        this.matrix = new double[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            this.matrix[i] = matrix[i].clone();
        }

        if (isRandomized) randomizeValues();
    }

    public Matrix(int a, int b, boolean isRandomized) {
        this(new double[a][b], isRandomized);
    }

    public Matrix(Matrix matrix) {
        this.matrix = new double[matrix.matrix.length][];
        for (int i = 0; i < matrix.matrix.length; i++) {
            this.matrix[i] = matrix.matrix[i].clone();
        }
    }

    public static Matrix multiply(final Matrix matrixA, final Matrix matrixB) {
        Objects.requireNonNull(matrixA);
        Objects.requireNonNull(matrixB);

        if (matrixA.matrix[0].length != matrixB.matrix.length) throw new IllegalArgumentException();

        int m = matrixA.matrix.length;
        int n = matrixB.matrix[0].length;

        double[][] tempMatData = new double[m][n];

        IntStream.range(0, m).parallel().forEach(i -> IntStream.range(0, n).parallel().forEach(j -> IntStream.range(0, matrixB.matrix.length).parallel().forEach(k -> tempMatData[i][j] += matrixA.matrix[i][k] * matrixB.matrix[k][j])));

        Matrix product = new Matrix();
        product.matrix = tempMatData;
        return product;
    }

    public static Matrix add(Matrix matrixA, Matrix matrixB) {
        Objects.requireNonNull(matrixA);
        Objects.requireNonNull(matrixB);
        if (matrixB.matrix.length != matrixA.matrix.length || matrixB.matrix[0].length != matrixA.matrix[0].length)
            throw new IllegalArgumentException("Dimensions of both matrices need to be same");

        int m = matrixA.matrix.length, n = matrixA.matrix[0].length;
        Matrix sum = new Matrix(matrixA.matrix.length, matrixA.matrix[0].length, false);
        IntStream.range(0, m).parallel().forEach(i -> IntStream.range(0, n).parallel().forEach(j -> sum.matrix[i][j] += matrixB.matrix[i][j]));
        return sum;
    }

    public static void randomize(Matrix matrix) {
        matrix.randomizeValues();
    }

    public void add(final double n) {
        IntStream.range(0, matrix.length).parallel().forEach(i -> IntStream.range(0, matrix[0].length).parallel().forEach(j -> matrix[i][j] += n));
    }

    public void add(Matrix matrix) {
        Objects.requireNonNull(matrix);
        if (matrix.matrix.length != this.matrix.length || matrix.matrix[0].length != this.matrix[0].length)
            throw new IllegalArgumentException("Dimensions of both matrices need to be same");

        int m = this.matrix.length, n = this.matrix[0].length;
        IntStream.range(0, m).parallel().forEach(i -> IntStream.range(0, n).parallel().forEach(j -> Matrix.this.matrix[i][j] += matrix.matrix[i][j]));
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

        IntStream.range(0, m).parallel().forEach(i -> IntStream.range(0, n).parallel().forEach(j -> IntStream.range(0, matrix.matrix.length).parallel().forEach(k -> tempMatData[i][j] += Matrix.this.matrix[i][k] * matrix.matrix[k][j])));
        this.matrix = tempMatData;
    }

    public void commutativeProduct(final Matrix matrix) {
        Objects.requireNonNull(matrix);
        if (matrix.matrix.length != this.matrix.length || matrix.matrix[0].length != this.matrix[0].length)
            throw new IllegalArgumentException();

        IntStream.range(0, this.matrix.length)
                .parallel()
                .forEach(i -> IntStream.range(0, Matrix.this.matrix[0].length)
                        .parallel()
                        .forEach(j -> Matrix.this.matrix[i][j] *= matrix.matrix[i][j]));
    }

    public void transpose() {
        double[][] transposedMatrix = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < transposedMatrix.length; i++) {
            for (int j = 0; j < transposedMatrix[0].length; j++) {
                transposedMatrix[i][j] = matrix[j][i];
            }
        }
        matrix = transposedMatrix;
    }

    public void map(MatrixMapper matrixMapper) {
        Arrays.stream(matrix).parallel().forEach(doubles -> IntStream.range(0, doubles.length).parallel().forEach(i -> doubles[i] = matrixMapper.map(doubles[i])));
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
        str.append('+');
        str.append("-".repeat(Math.max(0, matrix[0].length * 22 - 1)));
        str.append('+');
        str.append('\n');
        str.append("| ");
        for (double[] doubles : matrix) {
            for (double aDouble : doubles) {
                str.append(String.format("%17.16f | ", aDouble));
            }
            str.append("\n");
            str.append('+');
            str.append("-".repeat(matrix[0].length * 22 - 1));
            str.append('+');
            str.append("\n");
            str.append("| ");
        }
        str.replace(str.length() - (matrix[0].length * 22 + 4), str.length(), "");
        str.append('+');
        str.append("-".repeat(Math.max(0, matrix[0].length * 22 - 1)));
        str.append('+');
        str.append('\n');
        return str.toString();
    }

    private void randomizeValues() {
        Random random = new Random();

        IntStream.range(0, matrix.length).parallel().forEach(i -> IntStream.range(0, matrix[0].length).parallel().forEach(j -> matrix[i][j] = (random.nextDouble())));
    }
}
