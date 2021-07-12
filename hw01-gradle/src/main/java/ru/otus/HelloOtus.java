package ru.otus;

import com.google.common.collect.ComparisonChain;

public class HelloOtus {
    public static void main(String[] args) {
        int result =  ComparisonChain.start().compare("sameWord", "sameWord").compare(1,1).compare(1,134).result();
        System.out.println(result);
        System.out.println("Done");
    }
}
