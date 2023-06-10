package io.github.NavjotSRakhra.neuralNetwork.activation;

import static java.lang.Math.exp;

public class Sigmoid implements Activation {
    @Override
    public double function(double x) {
        return 1 / (1 + exp(-x));
    }

    @Override
    public double derivative(double x) {
        return (x) * (1 - (x));
    }
}
