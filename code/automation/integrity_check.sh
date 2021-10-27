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
./gradlew compileJava
cd ..

cd 04*
./gradlew runJava
cd ..

cd 05*
./gradlew clean runJava
cd ..

cd 06*
./gradlew clean compileJava
cd ..

cd 07*
./gradlew clean runJava
cd ..

cd 08*
./gradlew clean compileJava
cd ..

cd 09*
./gradlew build
cd ..

cd 10*
./gradlew build
cd ..

cd 11*
./gradlew build
cd ..

cd 12*
./gradlew build
cd ..

cd 13*
./gradlew build
cd ..
