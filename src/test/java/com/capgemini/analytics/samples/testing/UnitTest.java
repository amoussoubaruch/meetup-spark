package com.capgemini.analytics.samples.testing;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.BeforeClass;
import org.junit.AfterClass;

/**
 * Created by jeanbmar on 05/07/2016.
 */
public class UnitTest {

    protected static JavaSparkContext sc;

    @BeforeClass
    public static void setUpBeforeClassHelper() throws Exception {
        SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("junit")
                .set("spark.driver.allowMultipleContexts", "true");
        sc = new JavaSparkContext(conf);
        sc.setLogLevel("ERROR");
    }

    @AfterClass
    public static void tearDownAfterClassHelper() throws Exception {
        if (sc != null) {
            sc.stop();
            sc = null;
        }
    }

}
