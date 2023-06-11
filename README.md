# Neural Network

[![Maven Central](https://img.shields.io/maven-central/v/io.github.NavjotSRakhra/NeuralNetwork.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.NavjotSRakhra%22%20AND%20a:%22NeuralNetwork%22)

* [Introduction](#introduction)
* [Project setup](#project-setup)
* [UML](#uml)
* [LICENSE](#license)

## Introduction

I made my own version of neural network based on the videos of "The Coding Train."
This neural network has 1 input layer, 1 output layer and can have n hidden layers.
This neural network can use either a user provided cctivation function, the default
activation function (tanh) or the user can select from the list of activation functions
provided in io.github.NavjotSRakhra.neuralnetwork.activation package.

## Project setup

This project is made using OpenJDK 11. It should work in Java 11+.
To install the project into your maven repository,

- Execute `mvn clean install` in `.\NeuralNetwork\`.
- Or copy the following dependency into `pom.xml`

```xml

<dependency>
    <groupId>io.github.NavjotSRakhra</groupId>
    <artifactId>NeuralNetwork</artifactId>
    <version>1.0.2</version>
</dependency>
```

## UML

<img height="2114" src=".\resources\UML.png" width="2002"/>

## LICENSE

MIT License

Copyright (c) 2023 Navjot Singh Rakhra

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
