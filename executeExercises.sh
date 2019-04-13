#! /bin/bash

DIR_PATH=$(dirname "$(realpath "$0")")
PASSWD=secret
C_NAME=mysql_server
DDBB=cataVino

mkdir -p ${DIR_PATH}/build

javac -d ${DIR_PATH}/build -cp ${DIR_PATH}/mysql-connector-java-8.0.15.jar ${DIR_PATH}/src/exercise*/*.java ${DIR_PATH}/src/AssignmentSolver.java
java -cp ${DIR_PATH}/mysql-connector-java-8.0.15.jar:${DIR_PATH}/build/ AssignmentSolver localhost ${PASSWD} ${DDBB}
