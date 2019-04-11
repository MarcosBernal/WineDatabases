#! /bin/bash

DIR_PATH=$(dirname "$(realpath "$0")")
PASSWD=secret
C_NAME=mysql_server
DDBB=cataVino

javac -d ./build -cp mysql-connector-java-8.0.15.jar src/exercise*/*.java src/AssignmentSolver.java
java -cp mysql-connector-java-8.0.15.jar:build/ AssignmentSolver localhost ${PASSWD} ${DDBB}
