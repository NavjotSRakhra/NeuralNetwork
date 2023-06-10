package io.github.NavjotSRakhra.neuralNetwork.activation;

public class ReLU implements Activation {
    @Override
    public double function(double x) {
        return Math.max(0, x);
    }

    @Override
    public double derivative(double x) {
        return x >= 0 ? 1 : 0;
    }
}
