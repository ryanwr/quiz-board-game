#!/bin/bash
mkdir out
find -name "*.java" > out/sources.txt
javac -d out @out/sources.txt -cp json-simple-1.1.1.jar
