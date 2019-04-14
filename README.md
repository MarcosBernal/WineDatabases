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

Deploys a dockerized mysql server with the password '**secret**' and load some sql to create tables and insert data (check src/exercise1 and src/exercise2).
        
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
    
It compiles the java code and launches the application to interact through the console in order to
execute any of the exercises.

It uses the mysql-connector located in the root of the repo.


## Additional queries

To practice SQL the following queries can be developed:

- How many users have given a review (or more)? How many have given only two reviews?

- Which is the wine with best guide score? And the one with more user reviews?

- Which are the name of the wineries that have the highest user score? And the ones with worse guide score?

- How many wines have the same guide score and review score?

- Which is the avg. score of Spain regarding the wine scoring guide? And the guide score of each region?
