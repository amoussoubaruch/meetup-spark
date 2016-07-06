package com.capgemini.analytics.samples.replace;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Created by jeanbmar on 04/07/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        // Dataset
        String text = "Cap Gemini consultant and Big Data developer with strong xp on Hadoop, mostly Hadoop FS. BI background (DataStage, Cognos, Oracle, DB2). Worked on multiple Watson technologies, including Watson API and WEX.";
        Map<String,String> synonyms = Tools.loadDictionary("src/main/resources/dictionary");
        if(args.length > 0 && args[0].length() > 0) text = args[0];

        // Test
        Tools.info(text, synonyms);

        // Artificially increase CPU requirements
        int sizeCoeff = 100;
        int iterations = 500;

        StringBuilder textBuilder = new StringBuilder();
        for(int i = 0; i < sizeCoeff; i++) {
            textBuilder.append(text);
        }
        text = textBuilder.toString();

        System.out.println("Number of documents: " + iterations);
        System.out.println("Number of characters per document: " + text.length());
        System.out.println();

        // textReplace function
        System.out.println("textReplace: ");
        System.out.println("    Started: " + new Timestamp((new Date()).getTime()));
        Map<String, String> expandedSynonyms = Tools.expandMap(synonyms);
        for (int i = 0; i < iterations; i++) {
            Tools.textReplace(text, expandedSynonyms);
        }
        System.out.println("    Finished: " + new Timestamp((new Date()).getTime()));

        // replaceAll function
        System.out.println("replaceAll: ");
        System.out.println("    Started: " + new Timestamp((new Date()).getTime()));
        for (int i = 0; i < iterations; i++) {
            for(Map.Entry<String, String> entry : synonyms.entrySet()) {
                text.replaceAll(entry.getKey(), entry.getValue());
            }
        }
        System.out.println("    Finished: " + new Timestamp((new Date()).getTime()));
    }
}
