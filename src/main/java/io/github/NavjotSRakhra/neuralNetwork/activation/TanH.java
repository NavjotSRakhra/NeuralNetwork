package io.github.NavjotSRakhra.neuralNetwork.activation;

import static java.lang.Math.exp;

public class TanH implements Activation {
    @Override
    public double function(double x) {
        return (exp(x) - exp(-x)) / (exp(x) + exp(-x));
    }

    @Override
    public double derivative(double x) {
        return 1 - (x * x);
    }
}
