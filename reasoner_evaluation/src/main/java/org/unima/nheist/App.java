package org.unima.nheist;

import au.csiro.snorocket.owlapi.SnorocketReasonerFactory;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.elk.owlapi.ElkReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.ConsoleProgressMonitor;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class App
{

    public static void main( String[] args ) throws OWLOntologyCreationException, OWLOntologyStorageException {
        Logger logger = LoggerFactory.getLogger(App.class);

        String inputOntologyFile = args[0];
        OWLOntology inputOntology = loadOntology(inputOntologyFile);
        logger.info("Loaded ontology from " + inputOntologyFile);

        for(String reasoner : getReasoners()) {
            logger.info("Starting reasoner " + reasoner);
            String outputFileName = inputOntologyFile.substring(0, inputOntologyFile.lastIndexOf('.'))
                    + "_" + reasoner + ".owl";
            runReasoner(reasoner, inputOntology, outputFileName);
            logger.info("Finished reasoner " + reasoner);
        }
    }

    private static OWLOntology loadOntology(String inputOntologyFile) throws OWLOntologyCreationException {
        OWLOntologyManager om = OWLManager.createOWLOntologyManager();
        return om.loadOntologyFromOntologyDocument(new File(inputOntologyFile));
    }

    private static List<String> getReasoners() {
        List<String> reasoners = new ArrayList<>();
        reasoners.add("ELK");
        reasoners.add("HermiT");
        //reasoners.add("Pellet");
        return reasoners;
    }

    private static void runReasoner(String reasonerName, OWLOntology ontology, String outputFileName) throws OWLOntologyStorageException, OWLOntologyCreationException {
        OWLReasoner reasoner = null;
        switch(reasonerName) {
            case "Pellet":
                reasoner = new PelletReasonerFactory().createReasoner(ontology);
                break;
            case "ELK":
                reasoner = new ElkReasonerFactory().createReasoner(ontology);
                break;
            case "HermiT":
                Configuration hermitConf = new Configuration();
                hermitConf.reasonerProgressMonitor = new ConsoleProgressMonitor();
                reasoner = new ReasonerFactory().createReasoner(ontology, hermitConf);
        }

        // Define inference types.
        reasoner.precomputeInferences(
                InferenceType.CLASS_HIERARCHY,
                InferenceType.CLASS_ASSERTIONS,
                InferenceType.DISJOINT_CLASSES,
                InferenceType.OBJECT_PROPERTY_ASSERTIONS,
                InferenceType.DATA_PROPERTY_ASSERTIONS
        );

        // To generate an inferred ontology we use implementations of inferred axiom generators
        List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<>();
        gens.add(new InferredClassAssertionAxiomGenerator());
        gens.add(new InferredSubClassAxiomGenerator());
        gens.add(new InferredDisjointClassesAxiomGenerator());
        gens.add(new InferredPropertyAssertionGenerator());

        // Put the inferred axioms into a fresh empty ontology.
        OWLOntologyManager outputOntologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology infOnt = outputOntologyManager.createOntology();
        InferredOntologyGenerator iog = new InferredOntologyGenerator(reasoner, gens);
        if (reasonerName === "Pellet") {
            // owlapi version <= 3
            iog.fillOntology(outputOntologyManager, infOnt);
        } else {
            // owlapi version > 3
            iog.fillOntology(outputOntologyManager.getOWLDataFactory(), infOnt);
        }

        // Save the inferred ontology.
        outputOntologyManager.saveOntology(infOnt, IRI.create((new File(outputFileName).toURI())));

        // Terminate the worker threads used by the reasoner.
        reasoner.dispose();
    }
}
