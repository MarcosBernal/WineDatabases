# Overview

This project has been done to fulfill the requirements specified in the file [enunciado_practica_bbdd_1819.pdf](/enunciado_practica_bbdd_1819.pdf). 

## Requirements

- Java 8
- Docker and mysql client (*or* workbench application) 
- bash (required for the scripts otherwise IDE should be used)

## Environment

The following steps are performed in a UNIX-based system.

#### Mysql server:

    bash deployment.sh

Deploys a dockerized mysql server with the password **secret** and load some sql to create tables and insert data (check src/exercise1 and src/exercise2).
        
#### Mysql client:

mycli [recommended]

    pip install --user mycli
    mycli -u root -h localhost -psecret

mysql standard client

    mysql -u root --protocol=tcp -h localhost -psecret

## Execution

The following steps are performed in a UNIX-based system.

#### Compiling & execution

    bash executeExercises.sh
    
It compiled the java code and launch the application to interact with the console in order to
execute any of the exercises required.