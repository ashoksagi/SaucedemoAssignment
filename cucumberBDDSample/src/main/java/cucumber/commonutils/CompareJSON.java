package com.praveen;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class CompareJSON 
{

    private static String getResourceFileAsString(String fileName) {
        // Get the ClassLoader
        ClassLoader classLoader = CompareJSON.class.getClassLoader();

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

    public static JSONObject config;

    public static void main(String[] args) {
        String jsonStr1 =getResourceFileAsString("json1.json");
        String jsonStr2 = getResourceFileAsString("json2.json");
        String configJSON = getResourceFileAsString("config.json");
        config=new JSONObject(configJSON);

        // Parse JSON strings into JSONObject
        JSONObject json1 = new JSONObject(jsonStr1);
        JSONObject json2 = new JSONObject(jsonStr2);

        // Compare the two JSONObjects
        boolean areEqual = compareJSONObjects(json1, json2);
        System.out.println("Are the two JSONs equal? " + areEqual);
    }

    // Recursive method to compare two JSONObjects
    public static boolean compareJSONObjects(JSONObject json1, JSONObject json2) {
        boolean isSame= json1 == json2;

        Set<String> set1=json1.keySet();
        Set<String> set2=json2.keySet();
        Set<String> disjointInSet1 = new HashSet<>(set1);
        disjointInSet1.removeAll(set2);

        Set<String> disjointInSet2 = new HashSet<>(set2);
        disjointInSet2.removeAll(set1);
        if(disjointInSet1.size()>0){
            System.out.println("Elements/Props Extra IN JSON1: " + disjointInSet1);
        }
        if(disjointInSet2.size()>0){
            System.out.println("Elements/Props Extra IN JSON2" + disjointInSet2);
        }

        Set<String> commonKeys = new HashSet<>(set1);
        commonKeys.retainAll(set2);

        for (String key : commonKeys) {
            Object value1 = json1.get(key);
            Object value2 = json2.get(key);

            if (value1 instanceof JSONObject && value2 instanceof JSONObject) {
                if (!compareJSONObjects((JSONObject) value1, (JSONObject) value2)) {
                    isSame=false;
                }
            } else if (value1 instanceof JSONArray && value2 instanceof JSONArray) {
                if (!compareJSONArrays((JSONArray) value1, (JSONArray) value2,key)) {
                    isSame=false;
                }
            } else if (!value1.equals(value2)) {
                System.out.println("Primitive value in both JSON not equal for key: " + key);
                isSame=false;
            }
        }
        return isSame;
    }

    // Recursive method to compare two JSONArrays
    public static boolean compareJSONArrays(JSONArray array1, JSONArray array2,String parentKey) {
        boolean isSame=true;
        if(array1.length()==0 && array2.length()==0){
            return isSame;
        }
        if (array1.length() != array2.length()) {
            System.out.println("Lengths of arrays are not matching");
        }
        ExtendedJSONArray eArray1=new ExtendedJSONArray(array1);
        ExtendedJSONArray eArray2=new ExtendedJSONArray(array2);
        String idGenJsonExpr= config.optString(parentKey);
        Map<Object, JSONObject> array1Map=eArray1.getIndexedJSONObjectMembers(idGenJsonExpr,1,parentKey);
        Map<Object, JSONObject> array2Map=eArray2.getIndexedJSONObjectMembers(idGenJsonExpr,2,parentKey);
        eArray1.comparePrimitives(eArray2);
        
        Set<Object> commonElementIds = new HashSet<>(array1Map.keySet());
        commonElementIds.retainAll(array2Map.keySet());
        
        for(Object eleID:commonElementIds){
            compareJSONObjects(array1Map.get(eleID), array2Map.get(eleID));
        }
        

        eArray1.compareJSONArrays(eArray2);

        Set<Object> disjointInSet1 = new HashSet<>(array1Map.keySet());
        disjointInSet1.removeAll(array2Map.keySet());

        Set<Object> disjointInSet2 = new HashSet<>(array2Map.keySet());
        disjointInSet2.removeAll(array1Map.keySet());
        if(disjointInSet1.size()>0){
            System.out.println("Generated Id property: "+ idGenJsonExpr +" JSON1 extra:" + disjointInSet1);
        }
        if(disjointInSet2.size()>0){
            System.out.println("Generated Id property: "+ idGenJsonExpr +" JSON2 extra:" + disjointInSet2);
        }
        return isSame;
    }
}