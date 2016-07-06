package com.capgemini.analytics.samples.singleton;

import com.capgemini.analytics.samples.ConfigHelperJava;
import opennlp.tools.tokenize.TokenizerME;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import opennlp.tools.tokenize.TokenizerModel;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

/**
 * Created by jeanbmar on 05/07/2016.
 */
public class Main {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("Non-serializable Job")
                .set("spark.driver.allowMultipleContexts", "true");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("ERROR");

        // Do like we loaded files from Hadoop in a RDD
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < 40; i++) {
            list.add("Here's some text");
        }

        JavaRDD<String> rdd = sc.parallelize(list);

        System.out.println("Standard Approach:");
        System.out.println("    Started: " + new Timestamp((new Date()).getTime()));
        rdd.map(
            new Function<String, String>() {
                public String call(String arg0) throws Exception {
                    SimpleClass hello = new SimpleClass();
                    hello.hi(arg0);
                    return "That was slow!";
                }
            }
        ).collect();
        System.out.println("    Finished: " + new Timestamp((new Date()).getTime()));

        System.out.println("Singleton Approach:");
        System.out.println("    Started: " + new Timestamp((new Date()).getTime()));
        rdd.map(
                new Function<String, String>() {
                    public String call(String arg0) throws Exception {
                        SimpleClass hello = ThreadLocalSimpleClassHolder.get().getSingleton();
                        hello.hi(arg0);
                        return "That was fast!";
                    }
                }
        ).collect();
        System.out.println("    Finished: " + new Timestamp((new Date()).getTime()));

        sc.stop();
    }

}
