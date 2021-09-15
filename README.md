# CaLiGraph for Semantic Reasoning Evaluation Challenge
This repository contains code and data to use CaLiGraph as
a benchmark dataset in the [Semantic Reasoning Evaluation Challange](https://semrec.github.io)
at the [International Semantic Web Conference 2021 (ISWC'21)](https://iswc2021.semanticweb.org).

The paper describing the dataset characteristics and results for well-known
reasoners will be linked here as soon as a preprint is available.

## Datasets
We use [CaLiGraph version 2.1.0](https://zenodo.org/record/5509912) as foundation for the challenge dataset.
In particular, we use the files `caligraph-ontology.nt.bz2` and `caligraph-instances_types.nt.bz2`
to generate our sample data.

We provide sample datasets having roughly 10<sup>n</sup> classes with n in [1,6].
The datasets and all potentially inferrable assertions can be found [here](http://data.dws.informatik.uni-mannheim.de/CaLiGraph/CaLiGraph-for-SemREC/).
Please refer to our paper if you are interested in how the sample datasets were constructed.

## Evaluated Reasoners
We evaluated the following reasoners with our sample datasets:
* [ELK version 0.4.3](https://github.com/liveontologies/elk-reasoner)
* [HermiT version 1.4.5.456](https://github.com/phillord/hermit-reasoner)
* [Pellet version 2.3.6](https://github.com/stardog-union/pellet)

To evaluate the reasoners, we used their connectors in [OWL API](https://github.com/owlcs/owlapi).
The source code for the evaluation of the reasoners can be found in the folder `reasoner_evaluation`.
As Pellet needs an OWL API version lower than 4 (while the others need a version higher than 4)
we provide two `pom.xml` files. Depending on which reasoner you want to run, you have to use the correct one.
Further, you have to uncomment the respective reasoners in the `getReasoners()` function
of the java file `org.unima.nheist.App`.

Alternatively, you can use the two provided jar files to run the reasoners with the datasets.
First download the sample dataset you want to run the reasoners on, then run the jar file
and provide the location of the dataset as first argument like this:

```java -jar semrec-caligraph-elk-hermit.jar <PATH-TO-DATASET-FILE> &> log_elk-hermit.txt```

The result is a realization of the input dataset through the selected reasoners.
Have a look at the log file (in the case of the example: `log_elk-hermit.txt`)
for additional information about the reasoning process.

The precomputed results for all the sample datasets can be found [here](http://data.dws.informatik.uni-mannheim.de/CaLiGraph/CaLiGraph-for-SemREC/).