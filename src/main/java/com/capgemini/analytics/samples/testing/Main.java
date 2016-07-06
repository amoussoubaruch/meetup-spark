package com.capgemini.analytics.samples.testing;

/**
 * Created by jeanbmar on 05/07/2016.
 */

import org.apache.spark.*;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Main {

    public static void main(String[] args) {

        // Initialize context
        JavaSparkContext sc;
        SparkConf conf = new SparkConf();
        sc = new JavaSparkContext(conf);

        JavaRDD<String> rdd = sc.textFile("file_location");
        JavaRDD<String[]> tokenizedRdd = new ApplyNlp().run(rdd);
        for(String[] tokens : tokenizedRdd.collect()){
            //Do stuff
        }

        // Stop context
        sc.stop();
    }
}
