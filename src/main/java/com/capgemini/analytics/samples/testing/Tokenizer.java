package com.capgemini.analytics.samples.testing;

import com.capgemini.analytics.samples.ConfigHelperJava;
import org.apache.spark.api.java.*;
import java.io.InputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * Created by jeanbmar on 04/07/2016.
 */
public class Tokenizer {

    protected JavaSparkContext sc;

    public Tokenizer() {
    }

    public String[] tokenize(String text) {

        // Get configuration from job.properties
        String nameNode = ConfigHelperJava.getString("nameNode");
        String tokenizerModelFile = ConfigHelperJava.getString("tokenizer");

        // Declare and load tokenizer in memory
        TokenizerME tokenizerME;
        try {
            InputStream modelIn;
            Configuration hadoopConf = new Configuration();
            FileSystem hdfs = FileSystem.get(new java.net.URI(nameNode), hadoopConf);
            modelIn = hdfs.open(new Path(tokenizerModelFile));

            TokenizerModel tokenizerModel = new TokenizerModel(modelIn);
            tokenizerME = new TokenizerME(tokenizerModel);

            modelIn.close();

        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

        return tokenizerME.tokenize(text);
    }
}
