package com.capgemini.analytics.samples.singleton;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.InputStream;

public class SimpleClassHolder {
   
    private SimpleClass singleton = null;


	public SimpleClass initialize() {
		// Let's simulate a super slow class loading
		singleton = new SimpleClass();
		return singleton;
	}

    public SimpleClass getSingleton() {
		if (singleton == null) {
			initialize();
		}
		return singleton;
    }
    
}
