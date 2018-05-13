# ASER_Recommender
[![Build Status](https://travis-ci.org/sflin/ASER_Recommender.svg?branch=master)](https://travis-ci.org/sflin/ASER_Recommender)

# Overview

ASER -- short for "Advanced Software Engineering Recommender" -- is a collaborative project for the Advanced Software Engineering course of the University of Zurich in Spring 2018 of Dominik Bünzli and Selin Fabel. The recommender has been modeled after [Calcite](http://edelstein.pebbles.cs.cmu.edu/calcite/), a work of Mooty et al. (2010)  et al. It consists of two parts:
- the parser (where the source code is stored [here](https://github.com/sflin/ASER_Parser))
- the recommender (the source code of this repository)

# Installation

# Execution
For execution, ASER needs a set of so-called class-collections which can be obtained by running the ASER-parser over one of the publicly availalbe context-data-sets provided by the [KaVe-project](http://www.kave.cc/datasets). Additionally, ASER requires a set of annotated User-event data. In our implementation, we built the class-collections with the data-set [Contexts (May 3, 2017)](http://www.st.informatik.tu-darmstadt.de/artifacts/kave/Contexts-170503.zip), the recommender was tested with the interaction data [Events (Jan 18, 2018)](http://www.st.informatik.tu-darmstadt.de/artifacts/kave/Events-170301-2.zip). For demonstration purpose, a subset of the used sets is included.
In order to run the parser, the context-files to parse must be placed in a zipped folder. The application can then be started by passing the absolute path of the zipped folder as a command line argument. The class-collections are written to a folder called "archive" on the user's home directory (such as /user/home on linux or C:\Users on windows). 
The recommender requires ...

# Evaluation:

If the ASER-recommender is started by passing the flag "-e" as an argument, in addition to a randed list of recommendations, it will also show how well the recommender performed, i.e. some statistics of successful recommendations.
