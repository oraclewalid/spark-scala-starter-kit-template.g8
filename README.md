The project discoveryit-spark-scala-starter-kit-template is a template generator for Scala/Spark project.
The template provides a : 
* Build system compatible with Sbt and Maven
* Multi-environment configuration management based on [PureConfig](https://github.com/pureconfig/pureconfig) and  [Lightbend config](https://github.com/lightbend/config).
* Cli arguments parser via [Scopt](https://github.com/scopt/scopt)
* CI/CD template baszd on Github actions
* Unit tests examples and launcher with a Spark session.
* Packaging in fat jar format.

## Command
Sbt is used to generate the project [Sbt setup](https://www.scala-sbt.org/1.x/docs/Setup.html), for the next step you can choose Sbt or Maven for the project build management.

To create a project :

```
sbt new  oraclewalid/spark-scala-starter-kit-template.g8

```

## Project generation
The compilation, unit tests and packaging are already configured for the project.

**SBT**


```bash
sbt compile     # to compile
sbt test        # to launch tests
sbt assembly    # to package the jar
```
**Maven**

```bash
mvn compile     # to compile
mvn test        # to launch tests
mvn package     # to package the jar
```
## Usage
To launch the job via spark-submit :
```bash
spark-submit  --name "$name$" --class $package$.Main \
    --master local --num-executors 2  --executor-memory 2g\
    --conf spark.eventLog.enabled=false \
    project-jar.jar --env dev
```

## Configuration
The project uses [PureConfig](https://github.com/pureconfig/pureconfig), PureConfig is based on [Lightbend config](https://github.com/lightbend/config).
The lib support environment variables and files in HOCON format
The files are located in src/resources:
* application.conf      : common the config for all the environment.
* application-dev.conf  : contain the config specific to dev environment.
* application-prod.conf : contain the config specific to prod environment.

## Command line arguments
The project support command line arguments, the project use [Scopt](https://github.com/scopt/scopt), by default the project take one argument: the environment (dev or prod) via -e or --env to select right application.conf file
```bash
spark-submit  project-jar.jar --env dev
```
or 
```bash
spark-submit  project-jar.jar -e prod
```

## CI/CD
//TO DO
