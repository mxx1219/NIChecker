# NIChecker (Necessary Ingredients Checker)


I. Requirements
--------------------
 - [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)


II. Declaration
--------------------
 - The checker for determining whether a statement contains the necessary ingredients for the 11 fix templates used in TRANSFER (ICSE'22).


III. Preparation
--------------------
 - Place the source code of the target project in `./projects/`.
 - Place the fault localization results in `./SuspiciousCodePositions/`.
 - Fill the information of the source code directory of the target project in `./src_dir.txt`.
 - The parsed results are generated in `./output/`


IV. Usage
--------------------
```

cd ./target/ && java -jar NIChecker-1.0-SNAPSHOT-jar-with-dependencies.jar <project_name>

```
```
e.g. cd ./target/ && java -jar NIChecker-1.0-SNAPSHOT-jar-with-dependencies.jar Chart_1
```
