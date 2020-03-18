#!/bin/bash

gradle build
cat example.txt | java -jar comp2020-5f.jar