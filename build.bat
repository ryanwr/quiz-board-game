@ECHO off
mkdir out
dir /s /B *.java > out/sources.txt
javac -d out @out/sources.txt -cp json-simple-1.1.1.jar
