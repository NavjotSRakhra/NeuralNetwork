package io.github.NavjotSRakhra.neuralNetwork.activation;

import java.io.Serializable;

import static java.lang.Math.exp;

/**
 * tanh activation function
 */
public class TanH implements Activation, Serializable {
    @Override
    public double function(double x) {
        return (exp(x) - exp(-x)) / (exp(x) + exp(-x));
    }

    @Override
    public double derivative(double x) {
        return 1 - (x * x);
    }
}
