package com.nlp;

import edu.stanford.nlp.coref.CorefCoreAnnotations;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class NlpDemo {
    public static void main(String[] args) {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // read some text in the text variable
        String text = "Panchatantra stories are the oldest surviving fables from Ancient India. These colourful stories from the Panchatantra have spread all over the world for centuries, especially as bedtime stories for kids. We present here, a list of our versions of 60 such popular short stories from the Panchatantra, in English. Although dividing the Panchatantra into individual stories takes off the essence of its nested interludes, the following short stories include pictures and moral messages, especially for kids. ";
        System.out.println("Enter a string");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        // these are all the sentences in this document
// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            // traversing the words in the current sentence
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                // this is the text of the token
                String word = token.get(TextAnnotation.class);
                System.out.print("Token: "+word);
                // this is the POS tag of the token
                String pos = token.get(PartOfSpeechAnnotation.class);
                System.out.print("  ||  POS: "+pos);
                // this is the NER label of the token
                String ne = token.get(NamedEntityTagAnnotation.class);
                System.out.println("  || NE: "+ne);
            }

            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);

            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
        }

/*// This is the coreference link graph
// Each chain stores a set of mentions that link to each other,
// along with a method for getting the most representative mention
// Both sentence and token offsets start at 1!
        Map<Integer, CorefChain> graph =
                document.get(CorefCoreAnnotations.CorefChainAnnotation.class);*/

    }
}
