package com.capgemini.analytics.samples.testing;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by jeanbmar on 06/07/2016.
 */
public final class ApplyNlp implements Serializable {

    public ApplyNlp(){

    }

    public JavaRDD<String[]> run(final JavaRDD<String> documents){
        return documents.map(new Function<String, String[]>() {
            public String[] call(String arg0) throws Exception {
                return new Tokenizer().tokenize(arg0);
            }
        });
    }
}
