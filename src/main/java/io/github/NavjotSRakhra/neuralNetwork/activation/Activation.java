package io.github.NavjotSRakhra.neuralNetwork.activation;

public interface Activation {
    public double function(double x);

    public double derivative(double x);
}
