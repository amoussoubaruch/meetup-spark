package com.capgemini.analytics.samples.replace;

import java.util.*;
import java.io.*;

/**
 * Created by jeanbmar on 04/07/2016.
 */
public class Tools {

    public static Map<String, String> loadDictionary(String path) throws Exception {
        Map<String, String> result = new HashMap<String, String>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            String[] arr = line.split(";");
            result.put(arr[1], arr[0]);
        }
        return result;
    }

    public static Map<String, String> expandMap(Map<String, String> map) {
        Map <String, String> expandedMap = new HashMap<String, String>();
        for(Map.Entry<String, String> entry : map.entrySet()) {
            for (int i = 0; i < entry.getKey().length() - 1; i++) {
                if (expandedMap.get(entry.getKey().substring(0, i + 1)) == null)
                    expandedMap.put(entry.getKey().substring(0, i + 1), "X"); // partial word
            }
            expandedMap.put(entry.getKey(), entry.getValue());
        }
        return expandedMap;
    }

    public static void info(String text, Map<String, String> synonyms) {
        String replaceAllText = text;
        for(Map.Entry<String, String> entry : synonyms.entrySet()) {
            replaceAllText = replaceAllText.replaceAll(entry.getKey(), entry.getValue());
        }

        Map<String, String> expandedSynonyms = expandMap(synonyms);
        String textReplaceText = textReplace(text, expandedSynonyms);

        System.out.println("---- Input ---- \n" + text + "\n");
        System.out.println("---- Output textReplace ---- \n" + textReplaceText + "\n");
        System.out.println("---- Output replaceAll ---- \n" + replaceAllText + "\n");
    }

    public static String textReplace (String text, Map<String, String> dictionary) {
        StringBuilder result = new StringBuilder();
        String currentString = null;
        int lastNonLetterIndex = -1;
        int lastIndexWritten = 0;
        int lastConceptIndex = -1;
        int i = 0;
        while (lastIndexWritten < text.length()) {
            char currentChar;
            String concept;
            if (i == text.length()) {
                currentChar = '*';
                concept = null;
            } else {
                currentChar = text.charAt(i);
                currentString = text.substring(lastIndexWritten, i + 1);
                concept = dictionary.get(currentString);
            }
            if (concept == null) {
                if (lastConceptIndex != -1) {
                    //replacement and recording block
                    String originalTerm = text.substring(lastIndexWritten, lastConceptIndex);
                    String newTerm = dictionary.get(originalTerm);
                    result.append(newTerm);
                    i = lastConceptIndex;
                    lastIndexWritten = lastConceptIndex;
                    lastConceptIndex = -1;
                    lastNonLetterIndex = -1;
                } else {
                    if (lastNonLetterIndex != -1) {
                        // write and backtrack
                        result.append(text.substring(lastIndexWritten, lastNonLetterIndex));
                        lastIndexWritten = lastNonLetterIndex;
                        i = lastIndexWritten;
                        lastNonLetterIndex = -1;
                    } else {
                        if (Character.isLetter(currentChar)) {
                            // move to the next one, and write
                            while ((i + 1) < text.length()) {
                                if (Character.isLetter(text.charAt(i + 1))) {
                                    i++;
                                } else {
                                    result.append(text.substring(lastIndexWritten, i + 1));
                                    lastIndexWritten = i + 1;
                                    i++;
                                    break;
                                    // start a new one
                                }
                            }
                            // test if this is the end of the text, if so write
                            if (i == text.length() - 1) {
                                result.append(text.substring(lastIndexWritten, text.length()));
                                lastIndexWritten = text.length();
                            }
                        } else {
                            if (lastIndexWritten == i) {
                                // single char
                                result.append(text.substring(lastIndexWritten, i + 1));
                                lastIndexWritten = i + 1;
                                i++;
                            } else {
                                // start a new one from there
                                result.append(text.substring(lastIndexWritten, i));
                                lastIndexWritten = i;
                            }
                        }
                    }
                }
            } else if (concept.equals("X")) {
                // part of something bigger?
                if (!Character.isLetter(currentChar)) {
                    if (i > lastIndexWritten)// Don't take if first char
                        lastNonLetterIndex = i;
                }
                i++;
            } else {
                // did we find a concept?
                if (i + 1 == text.length()) {
                    lastConceptIndex = i + 1;
                } else if (!(Character.isLetter(text.charAt(i + 1)) || text.charAt(i + 1) == '\'')) {
                    lastConceptIndex = i + 1;
                }
                i++;
            }
        }
        return result.toString();
    }

}
