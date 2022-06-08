# CaLiGraph for Semantic Reasoning Evaluation Challenge

## SemREC'21

This repository contains code and data to use [CaLiGraph](http://caligraph.org) as
a benchmark dataset in the [Semantic Reasoning Evaluation Challange](https://semrec.github.io)
at the [International Semantic Web Conference 2021 (ISWC'21)](https://iswc2021.semanticweb.org).

The paper describing the dataset characteristics and results for well-known
reasoners can be found [here](https://arxiv.org/pdf/2110.05028.pdf).

### Datasets
We use [CaLiGraph version 2.1.0](https://zenodo.org/record/5509912) as foundation for the challenge dataset.
In particular, we use the files `caligraph-ontology.nt.bz2` and `caligraph-instances_types.nt.bz2`
to generate our sample data.

We provide sample datasets having roughly 10<sup>n</sup> classes with n in [1,6].
The datasets and all potentially inferrable assertions can be found [here](http://data.dws.informatik.uni-mannheim.de/CaLiGraph/CaLiGraph-for-SemREC/).
Please refer to our paper if you are interested in how the sample datasets were constructed.

### Evaluated Reasoners
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


## SemREC'22

For [SemREC'22](https://semrec.github.io) at the [International Semantic Web Conference 2022 (ISWC'22)](https://iswc2022.semanticweb.org) we provide [CaLiGraph](http://caligraph.org) as an additional challenge dataset for the participants of Task 2 (Systems). For this purpose, we use the subsets `clg_10e4`, `clg_10e5`, and `clg_full` and split their inferrable assertions into training, validation, and test files. These files are used to train and evaluate neuro-symbolic reasoners.

The split datasets can be found [here](http://data.dws.informatik.uni-mannheim.de/CaLiGraph/CaLiGraph-for-SemREC/SemREC-2022-Datasets/).

Below you find the statistics of the training, validation, and test splits of the datasets with respect to assertion types. For statistics about the original datasets, refer to [our publication](https://arxiv.org/pdf/2110.05028.pdf) from SemREC'21.
<table>
  <thead>
    <tr>
      <td>Dataset</td>
      <td align='center' colspan="3">Superclass Assertions</td>
      <td align='center' colspan="3">Type Assertions</td>
      <td align='center' colspan="3">Relation Assertions</td>
    </tr>
    <tr>
      <td></td>
      <td align='center'>train</td>
      <td align='center'>val</td>
      <td align='center'>test</td>
      <td align='center'>train</td>
      <td align='center'>val</td>
      <td align='center'>test</td>
      <td align='center'>train</td>
      <td align='center'>val</td>
      <td align='center'>test</td>
  </thead>
  <tbody>
    <tr>
      <td>clg_10e4</td>
      <td align='right'>59,957</td>
      <td align='right'>8,565</td>
      <td align='right'>17,132</td>
      <td align='right'>51,578</td>
      <td align='right'>7,368</td>
      <td align='right'>14,738</td>
      <td align='right'>16,269</td>
      <td align='right'>2,324</td>
      <td align='right'>4,649</td>
    </tr>
    <tr>
      <td>clg_10e5</td>
      <td align='right'>96,274</td>
      <td align='right'>13,753</td>
      <td align='right'>27,508</td>
      <td align='right'>29,974</td>
      <td align='right'>4,282</td>
      <td align='right'>8,565</td>
      <td align='right'>138,894</td>
      <td align='right'>19,842</td>
      <td align='right'>39,684</td>
    </tr>
    <tr>
      <td>clg_full</td>
      <td align='right'>8,518,816</td>
      <td align='right'>1,216,973</td>
      <td align='right'>2,433,949</td>
      <td align='right'>97,099,450</td>
      <td align='right'>13,871,350</td>
      <td align='right'>27,742,700</td>
      <td align='right'>7,281,297</td>
      <td align='right'>1,040,185</td>
      <td align='right'>2,080,371</td>
    </tr>
  </tbody>
</table>

The code that is used to create the splits and the statistics is located in [this notebook](CaLiGraph-for-SEMREC-2022-train-val-test-split.ipynb).
