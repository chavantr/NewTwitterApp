package com.mywings.newtwitterapp.algorithm;


import android.content.Context;
import android.content.res.AssetManager;

import java.io.*;
import java.util.Scanner;

public class RemoveStopWords {

    public String removeWords(String data, Context context) throws IOException {
        boolean flag = false;
        AssetManager am = context.getAssets();
        InputStream is = am.open("stopwords.txt");
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String[] tokens = data.split("\\P{Alpha}+");// \\p is use for space and
        // {Alpha for (')eg it's}

        for (String s : tokens) {
            for (String line; (line = reader.readLine()) != null; ) {
                if (line.equalsIgnoreCase(s)) {
                    flag = true;
                    break;
                } else {
                    flag = false;
                }
            }
            if (flag == false) {

                sb.append(s + ",");
            }
        }
        return sb.toString();
    }


    public int negativeTopics(String data, Context context) throws IOException {
        int a = 1;
        StringBuilder sb = new StringBuilder();

        String[] tokens = data.split("\\P{Alpha}+");// \\p is use for space and
        // {Alpha for (')eg it's}

        AssetManager am = context.getAssets();
        InputStream is = am.open("WordDicNeg.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        for (String s : tokens) {
            for (String line; (line = reader.readLine()) != null; ) {
                if (line.equalsIgnoreCase(s)) {
                    a++;
                    break;
                }
            }
        }
        return a;
    }

    public int positiveTopics(String data, Context context) throws IOException {
        boolean flag = false;
        Scanner sc;
        int a = 1;
        StringBuilder sb = new StringBuilder();
        AssetManager am = context.getAssets();
        InputStream is = am.open("WordDic.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String[] tokens = data.split("\\P{Alpha}+");// \\p is use for space and
        // {Alpha for (')eg it's}

        for (String s : tokens) {
            for (String line; (line = reader.readLine()) != null; ) {
                if (line.equalsIgnoreCase(s)) {
                    a++;
                    break;
                }
            }

        }
        return a;
    }
}
