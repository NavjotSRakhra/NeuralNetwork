package io.github.NavjotSRakhra.matrix;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.AverageTime, Mode.Throughput})
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
public class MatrixBenchmark {
    private static final int size = 100;

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public void addBenchMark() {
        Matrix a = new Matrix(size, size, true);
        Matrix b = new Matrix(size, size, true);
        a.add(b);
    }

    @Benchmark
    public void multiplyBenchMark() {
        Matrix a = new Matrix(size, size, true);
        Matrix b = new Matrix(size, size, true);
        a.multiply(b);
    }
}
