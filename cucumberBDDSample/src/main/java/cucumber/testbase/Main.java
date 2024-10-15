package com.praveen;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class Main 
{

    private static String getResourceFileAsString(String fileName) {
        // Get the ClassLoader
        ClassLoader classLoader = Main.class.getClassLoader();

        // Read the file as InputStream
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            if (inputStream == null) {
                throw new IllegalArgumentException("File not found! " + fileName);
            }

            // Convert BufferedReader to a String using stream operations
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String jsonStr1 =getResourceFileAsString("json1.json");
        String jsonStr2 = getResourceFileAsString("json2.json");
        String configJSON = getResourceFileAsString("config.json");
        JSONObject config=new JSONObject(configJSON);

        // Parse JSON strings into JSONObject
        JSONObject json1 = new JSONObject(jsonStr1);
        JSONObject json2 = new JSONObject(jsonStr2);

        // Compare the two JSONObjects
        JSONComparator comparator=new JSONComparator(config);
        boolean areEqual = comparator.compareJSONObjects(json1, json2,"$");
        System.out.println("Are the two JSONs equal? " + areEqual);
    }

    
}