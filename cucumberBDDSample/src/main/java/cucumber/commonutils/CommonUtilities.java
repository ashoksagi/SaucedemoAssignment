package com.praveen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        // Sample JSON strings for comparison
        String jsonStr1 ="{\"test1\":[{\"companyName\":\"test1\"},{\"companyName\":\"test\"}]}";
        String jsonStr2 = "{\"test2\":[{\"companyName\":\"test\"},{\"companyName\":\"test1\"}]}";

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
            System.out.println("Elements not in 2nd JSON: " + disjointInSet1);
        }
        if(disjointInSet2.size()>0){
            System.out.println("Elements not in 1st JSON: " + disjointInSet2);
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
                if (!compareJSONArrays((JSONArray) value1, (JSONArray) value2)) {
                    isSame=false;
                }
            } else if (!value1.equals(value2)) {
                System.out.println("Primitive value in both JSON not equal for key: " + key);
                isSame=false;
            }
        }
        return isSame;
    }

    private static Map<Object, JSONObject> indexJSONArray(JSONArray array1,String property){
        JSONArray jsonArray = new JSONArray(array1);
        Map<Object, JSONObject> indexedObjects = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Object id = jsonObject.opt(property);
            indexedObjects.put(id, jsonObject);
        }
        return indexedObjects;
    }

    // Recursive method to compare two JSONArrays
    public static boolean compareJSONArrays(JSONArray array1, JSONArray array2) {
        boolean isSame=true;
        if(array1.length()==0 && array2.length()==0){
            return isSame;
        }
        if (array1.length() != array2.length()) {
            System.out.println("Lengths of arrays are not matching");
        }

        Map<Object, JSONObject> array1Map=indexJSONArray(array1, "companyName");
        Map<Object, JSONObject> array2Map=indexJSONArray(array2, "companyName");

        Set<Object> commonKeys= new HashSet<>(array1Map.keySet());
        commonKeys.retainAll(array2Map.keySet());

        for(Object key: commonKeys){
            if (!compareJSONObjects(array1Map.get(key), array2Map.get(key))) {
                isSame=false;
            }
        }

        Set<Object> disjointInSet1 = new HashSet<>(array1Map.keySet());
        disjointInSet1.removeAll(array2Map.keySet());

        Set<Object> disjointInSet2 = new HashSet<>(array2Map.keySet());
        disjointInSet2.removeAll(array1Map.keySet());
        if(disjointInSet1.size()>0){
            System.out.println("Elements not in 2nd JSON: " + disjointInSet1);
        }
        if(disjointInSet2.size()>0){
            System.out.println("Elements not in 1st JSON: " + disjointInSet2);
        }


        /* for (int i = 0; i < array1.length(); i++) {
            Object value1 = array1.get(i);
            Object value2 = array2.get(i);

            if (value1 instanceof JSONObject && value2 instanceof JSONObject) {
                if (!compareJSONObjects((JSONObject) value1, (JSONObject) value2)) {
                    isSame=false;
                }
            } else if (value1 instanceof JSONArray && value2 instanceof JSONArray) {
                if (!compareJSONArrays((JSONArray) value1, (JSONArray) value2)) {
                    isSame=false;
                }
            } else if (!value1.equals(value2)) {
                isSame=false;
            }
        }  */
        return isSame;
    }
}
