#!/bin/sh
set -e

cd 00*
gradle helloWorld
cd ..

cd 01*
gradle printJavaVersion
cd ..

cd 02*
gradle compileJava
cd ..

cd 03*
gradle compileJava
cd ..

cd 04*
gradle runJava
cd ..

cd 05*
gradle clean runJava
cd ..

cd 06*
gradle clean compileJava
cd ..

cd 07*
gradle clean compileJava
cd ..

cd 08*
gradle clean compileJava
cd ..

cd 09*
gradle build
cd ..

cd 10*
gradle build
cd ..

cd 11*
gradle build
cd ..

cd 12*
gradle build
cd ..

cd 13*
gradle build
cd ..
