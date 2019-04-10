## Requirements

- Java 8
- Docker (or workbench application) 

## Environment

Launch environment:

Mysql server:

    docker run -d --rm -e MYSQL_ROOT_PASSWORD=secret -p 3306:3306 mysql
        
Mysql client:
    
    pip install --user mycli
    mycli -u root -h localhost -psecret

## Compile exercises

With the ide (skip this step) or with the terminal:

    mkdir build
    
    # Compile the java files and place the class object into a similar structure in the build folder
    javac -d ./build -cp mysql-connector-java-8.0.15.jar src/exercise*/*.java src/AssignmentSolver.java


## Execution

Using an IDE, tool such as maven. 

With the classpath:

    java -cp mysql-connector-java-8.0.15.jar:build/ AssignmentSolver localhost secret cataVino


## Exercises

#### Exercise 1
...
#### Exercise 2
...
#### Exercise 3
...
