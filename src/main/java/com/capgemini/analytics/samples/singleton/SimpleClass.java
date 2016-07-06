package com.capgemini.analytics.samples.singleton;

/**
 * Created by jeanbmar on 05/07/2016.
 */
public class SimpleClass {

    public SimpleClass() {
        // This class is super slow to load!
        try{Thread.sleep(1000);}catch(Exception e){};
    }

    public String hi(String text) {
        return text;
    }

}
