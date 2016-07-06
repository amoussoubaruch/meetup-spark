package com.capgemini.analytics.samples.testing;

/**
 * Created by jeanbmar on 05/07/2016.
 */

import org.apache.spark.api.java.JavaRDD;
import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

public class ApplyNlpTest extends UnitTest {

    @Test
    public void applyNlpTest() {

        List<String> docs = Arrays.asList(
                "This is a simple example.",
                "We want to tokenize documents.",
                "Each line of this object is a document!"
        );

        JavaRDD<String> rdd = sc.parallelize(docs);
        JavaRDD<String[]> tokenizedRdd = new ApplyNlp().run(rdd);

        int tokenCount = 0;
        for(String[] tokens : tokenizedRdd.collect()){
            System.out.println(Arrays.toString(tokens));
            tokenCount += tokens.length;
        }

        Assert.assertEquals(tokenCount, 21);

    }

}
