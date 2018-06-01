# ASER_Recommender
[![Build Status](https://travis-ci.org/sflin/ASER_Recommender.svg?branch=master)](https://travis-ci.org/sflin/ASER_Recommender)

# Overview

ASER -- short for "Advanced Software Engineering Recommender" -- is a collaborative project for the Advanced Software Engineering course of the University of Zurich in Spring 2018 of Dominik Bünzli and Selin Fabel. The recommender has been modeled after [Calcite](http://edelstein.pebbles.cs.cmu.edu/calcite/), a work of Mooty et al. (2010)  et al. It consists of two parts:
- the parser (where the source code is stored [here](https://github.com/sflin/ASER_Parser))
- the recommender (the source code of this repository)

ASER is licensed under the Apache License 2.0.

# Installation
ASER is built with Travis-ci. Its version are deployed to a packagecloud-repository. To re-use the ASER-Recommender, developers can simply add the following code-snippets to the dependencyManagment section of the pom.xml:

```xml
<repositories>
  <repository>
    <id>sflin-ASER_Recommender</id>
    <url>https://packagecloud.io/sflin/ASER_Recommender/maven2</url>
    <releases>
      <enabled>true</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>
</repositories>
```
```xml
<dependency>
  <groupId>ASER_Recommender</groupId>
  <artifactId>ASER_Recommender</artifactId>
  <version>1.4.0</version>
</dependency>
```

For re-usage of the ASER-Parser, add the following: 
```xml
<repositories>
  <repository>
    <id>sflin-ASER_Parser</id>
    <url>https://packagecloud.io/sflin/ASER_Parser/maven2</url>
    <releases>
      <enabled>true</enabled>
    </releases>
    <snapshots>
      <enabled>true</enabled>
    </snapshots>
  </repository>
</repositories>
```

and this dependency:
```xml
<dependency>
  <groupId>ASER_Paser</groupId>
  <artifactId>ASER_Parser</artifactId>
  <version>1.0</version>
</dependency>
```

# Execution
For execution, ASER needs a set of so-called class-collections which can be obtained by running the ASER-parser over one of the publicly availalbe context-data-sets provided by the [KaVe-project](http://www.kave.cc/datasets). Additionally, ASER requires a set of annotated User-event data. In our implementation, we built the class-collections with the data-set [Contexts (May 3, 2017)](http://www.st.informatik.tu-darmstadt.de/artifacts/kave/Contexts-170503.zip), the recommender was tested with the interaction data [Events (Jan 18, 2018)](http://www.st.informatik.tu-darmstadt.de/artifacts/kave/Events-170301-2.zip). 
In order to run the parser, the context-files to parse must be placed in a zipped folder. The application can then be started by executing the script "Parser.java" with the absolute path of the zipped folder as a command line argument and as a second argument the absolute path of where to store the parsed files. These arguments can also be ommitted, but then the context-files must be placed in /home/<user>/Contexts or C:\Users\<Name>\Contexts respectively. The class-collections are written to a folder called "archive" on the user's home directory.
As the parsing process takes some time, a finished version can be downloaded from the following link [here](http://aser.dominikbuenzli.ch/MethodCollectionsProd.zip). Please note that you have to unzip the ClassCollections before using them.

The recommender requires four input parameters. First, the recommender expects the path to the events archives. Second, the path to the class collections must be specified. Third, the output path must be specified. Last but not least, the flag "-e" can be used to indicate whether an evaluation of the results should take place. If no evaluation is desired, an "empty" string can be passed. On Windows, call the executable of the class "Client.java" in the Recommender-Project for example with these arguments:  

    C:\Users\<Name>\Events C:\Users\<Name>\ClassCollections C:\Users\<Name>\Output -e 
  
On Linux, use: 

    /home/<user>/Events /home/<user>/ClassCollections /home/<user>/Output -e

# Evaluation:

If the ASER-recommender is started by passing the flag "-e" as an argument, in addition to a ranked list of recommendations, it will also show how well the recommender performed, i.e. measurements for precision, recall, number of successful and unsuccessful recommendations and the average rank (in case of success). Precision is calculated by TruePositive / (TruePositive + FalsePositive). In this context, TruePositive refers to the amount of queries where the actual selection was successfully found in the recommended hit-list. FalsePositive stands for the amount of queries where a hit-list exists but the actual selection is not part of it. Recall is calculated by TruePositive / (FalseNegative + TruePositive) where FalseNegative denotes the amount of queries where no hit-list exists at all. The average rank corresponds to the number of hits which have to be examined on average until a hit is found.

