package io.github.NavjotSRakhra.neuralNetwork.activation;

import java.io.Serializable;

import static java.lang.Math.exp;

public class Sigmoid implements Activation, Serializable {
    @Override
    public double function(double x) {
        return 1 / (1 + exp(-x));
    }

    @Override
    public double derivative(double x) {
        return (x) * (1 - (x));
    }
}
