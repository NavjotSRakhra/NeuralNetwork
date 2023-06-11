package io.github.NavjotSRakhra.neuralNetwork.activation;

import java.io.Serializable;

/**
 * Rectified Linear Unit activation function
 */
public class ReLU implements Activation, Serializable {
    @Override
    public double function(double x) {
        return Math.max(0, x);
    }

    @Override
    public double derivative(double x) {
        return x >= 0 ? 1 : 0;
    }
}
