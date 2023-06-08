package io.github.NavjotSRakhra.neuralNetwork;

import io.github.NavjotSRakhra.progressPrinter.ProgressPrinter;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NeuralNetworkTest extends TestCase {

    private static void swap(double[][][] data, int a, int b) {
        double[][] temp = data[a];
        data[a] = data[b];
        data[b] = temp;
    }

    private static void shuffleArray(double[][][] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            swap(ar, index, i);
        }
    }

    public void testTrain() {
        NeuralNetwork neuralNetwork = new NeuralNetwork(2, 2, 28, 14, 7, 3);
        ProgressPrinter progressPrinter = new ProgressPrinter();
        double[][][] data = {{{1, 1}, {1, 0}}, {{0, 0}, {1, 0}}, {{1, 0}, {0, 1}}, {{0, 1}, {0, 1}}};

        for (int i = 0; i < 12500; i++) {
            for (double[][] datum : data) {
                neuralNetwork.train(datum[0], datum[1]);
            }
            shuffleArray(data);
            progressPrinter.update(i / (12500 - 1d));
        }
        for (double[][] datum : data) {
            System.out.println(Arrays.deepToString(datum));
            System.out.println(Arrays.toString(neuralNetwork.feedForward(datum[0])));
        }

        int correct = 0;
        for (double[][] datum : data) {
            var output = neuralNetwork.feedForward(datum[0]);
            int maxInd = 0;
            for (int i = 0; i < output.length; i++) {
                if (output[maxInd] < output[i]) maxInd = i;
            }
            if (datum[1][maxInd] == 1) correct++;
        }
        assertEquals(data.length, correct);
    }
}