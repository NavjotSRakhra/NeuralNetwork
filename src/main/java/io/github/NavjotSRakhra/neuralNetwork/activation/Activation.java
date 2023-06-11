package io.github.NavjotSRakhra.neuralNetwork.activation;

/**
 * Activation function used in {@link io.github.NavjotSRakhra.neuralNetwork.NeuralNetwork} after every step
 */
public interface Activation {
    public double function(double x);

    public double derivative(double x);
}
