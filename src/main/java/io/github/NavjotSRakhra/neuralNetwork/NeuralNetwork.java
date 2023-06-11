package io.github.NavjotSRakhra.neuralNetwork;

import io.github.NavjotSRakhra.neuralNetwork.activation.Activation;
import io.github.NavjotSRakhra.neuralNetwork.activation.TanH;
import io.github.NavjotSRakhra.neuralNetwork.matrix.Matrix;

import java.io.*;
import java.util.Arrays;

/**
 * A simple implementation of Neural network that can be used for basic AI.
 * It can have n hidden layers and custom activation functions.
 */
public class NeuralNetwork implements Serializable {
    private final int inputSize, outputSize, hiddenLayersSize[];
    private final Matrix[] layers, biases;
    private final Activation activation;
    private double learningRate;
    private boolean softmax;

    /**
     * Creates a NeuralNetwork object with default learning rate 0.1.
     *
     * @param activationFunction the function that is used on output after each feedforward step
     * @param inputSize          the size of array that will be input
     * @param outputSize         the size of array that should be output, and trained upon
     * @param hiddenLayersSize   the number and size of hidden layers, as provided by the user.
     */
    public NeuralNetwork(Activation activationFunction, int inputSize, int outputSize, int... hiddenLayersSize) {
        this.learningRate = 0.1d;
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        this.hiddenLayersSize = new int[hiddenLayersSize.length];
        this.softmax = true;
        this.activation = activationFunction;
        System.arraycopy(hiddenLayersSize, 0, this.hiddenLayersSize, 0, hiddenLayersSize.length);

        layers = new Matrix[hiddenLayersSize.length + 1];
        biases = new Matrix[hiddenLayersSize.length + 1];

        //Add Input to first hidden layer OR Input to Output layer matrix
        layers[0] = new Matrix(hiddenLayersSize.length > 0 ? hiddenLayersSize[0] : outputSize, inputSize, true);
        biases[0] = new Matrix(hiddenLayersSize.length > 0 ? hiddenLayersSize[0] : outputSize, 1, false);

        //Initializing hidden layers and output layer
        for (int i = 0; i < hiddenLayersSize.length; i++) {
            int next = outputSize;
            int current = hiddenLayersSize[i];

            if (i < hiddenLayersSize.length - 1) next = hiddenLayersSize[i + 1];

            layers[i + 1] = new Matrix(next, current, true);
            biases[i + 1] = new Matrix(next, 1, false);
        }
    }

    /**
     * Creates a NeuralNetwork object with default activation function: {@link TanH} and default learning rate 0.1.
     *
     * @param inputSize        the size of array that will be input
     * @param outputSize       the size of array that should be output, and trained upon
     * @param hiddenLayersSize the number and size of hidden layers, as provided by the user.
     */
    public NeuralNetwork(int inputSize, int outputSize, int... hiddenLayersSize) {
        this(new TanH(), inputSize, outputSize, hiddenLayersSize);
    }

    private static double softMax(double cur, double sum) {
        return cur / sum;
    }

    private static Matrix matrixFromArray(double[] input) {
        Matrix matrix = new Matrix(new double[][]{input}, false);
        matrix.transpose();
        return matrix;
    }

    /**
     * This function is used to read the saved NeuralNetwork object from a file.
     *
     * @param pathToFile the path to along with the filename in which the NeuralNetworkObject was saved.
     * @return NeuralNetwork object read from the file
     */
    public static NeuralNetwork readFrom(String pathToFile) {
        try (FileInputStream inputStream = new FileInputStream(pathToFile)) {
            ObjectInputStream in = new ObjectInputStream(inputStream);

            Object inputObject = in.readObject();

            in.close();
            return (NeuralNetwork) inputObject;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void softMax(Matrix inputMatrix) {
        double sum = Arrays.stream(inputMatrix.getMatrix()[0]).map(Math::exp).sum();
        inputMatrix.map(i -> softMax(Math.exp(i), sum));
    }

    /**
     * Can be used to tun on or of mapping of softmax function at the end of output layers
     *
     * @param flag True or False based on if you want the output to be softmax-ed or not.
     */
    public void isOutputSoftmax(boolean flag) {
        softmax = flag;
    }

    /**
     * Getter for learning rate
     *
     * @return learning rate of the neural network
     */
    public double getLearningRate() {
        return learningRate;
    }

    /**
     * Setter for learning rate
     *
     * @param learningRate the learning rate to be set to the neural network
     */
    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    /**
     * This method takes in input and gives the output after applying feedforward from one layer to another upto the output layer
     *
     * @param input the input array of size as specified in the constructor
     * @return out put array of size as specified in the constructor
     */
    public double[] feedForward(double[] input) {
        if (input.length != inputSize)
            throw new IllegalArgumentException("Input size " + input.length + " not equal to the input size defined: " + inputSize);

        Matrix inputMatrix = matrixFromArray(input);
        for (int i = 0; i < layers.length; i++) {
            inputMatrix = Matrix.multiply(layers[i], inputMatrix);
            inputMatrix.add(biases[i]);
            inputMatrix.map(activation::function);
        }
        inputMatrix.transpose();

        if (softmax) softMax(inputMatrix);

        return inputMatrix.getMatrix()[0];
    }

    private Matrix[] getFeedForwardOutputs(double[] input) {
        if (input.length != inputSize)
            throw new IllegalArgumentException("Input size " + input.length + " not equal to the input size defined: " + inputSize);

        Matrix[] outputs = new Matrix[layers.length];

        Matrix inputMatrix = matrixFromArray(input);
        for (int i = 0; i < layers.length; i++) {
            inputMatrix = Matrix.multiply(layers[i], inputMatrix);
            inputMatrix.add(biases[i]);
            inputMatrix.map(activation::function);

            outputs[i] = inputMatrix;
        }
        return outputs;
    }

    /**
     * This function is used to train the neural network based on the given inputs and given target.
     *
     * @param input  the input array (Size same as the one given in constructor)
     * @param target the expected output array (Size same as the one given in constructor)
     */
    public void train(double[] input, double[] target) {
        if (input.length != inputSize)
            throw new IllegalArgumentException("Input size " + input.length + " not equal to the input size defined: " + inputSize);
        if (outputSize != target.length)
            throw new IllegalArgumentException("Output size " + target.length + " not equal to the input size defined: " + outputSize);

        Matrix[] outputs = getFeedForwardOutputs(input);

        Matrix temp = outputs[outputs.length - 1].clone();
        temp.transpose();

        double[] output = temp.getMatrix()[0];
        double[] error = new double[output.length];

        for (int i = 0; i < target.length; i++) {
            error[i] = target[i] - output[i];
        }

        Matrix errorMatrix = matrixFromArray(error);
        for (int i = layers.length - 1; i >= 0; i--) {
            Matrix newError = layers[i].clone();
            newError.transpose();
            newError.multiply(errorMatrix);

            Matrix weightDelta = outputs[i].clone();
            weightDelta.map(activation::derivative);
            weightDelta.commutativeProduct(errorMatrix);
            weightDelta.multiply(learningRate);

            biases[i].add(weightDelta);

            Matrix inputToLayer = i - 1 >= 0 ? outputs[i - 1].clone() : matrixFromArray(input);
            inputToLayer.transpose();
            weightDelta.multiply(inputToLayer);

            layers[i].add(weightDelta);

            errorMatrix = newError;
        }
    }

    /**
     * Write the object into file at the given file path. Preferred file extension is .nnn
     *
     * @param pathToFile The path along with the name of the file in which the object is to be saved
     */
    public void writeTo(String pathToFile) {
        try (FileOutputStream outputStream = new FileOutputStream(pathToFile)) {
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(this);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
