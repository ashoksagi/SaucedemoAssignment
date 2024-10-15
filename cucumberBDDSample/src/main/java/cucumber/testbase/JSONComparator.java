/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.praveen;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author pjangala
 */
public class JSONComparator {

    private JSONObject configJSON = null;
    public boolean isSame = true;

    private Stack jsonPath = new Stack();

    public JSONComparator(JSONObject configJSON) {
        this.configJSON = configJSON;
    }

    public String getCurrentJSONPath(String currenyKey) {
        String currentJSONPath = String.join(".", jsonPath);
        if (currenyKey != null) {
            //System.out.println("currentJSONPath:" + currentJSONPath + "." + currenyKey);
            return currentJSONPath + "." + currenyKey;
        } else {
            //System.out.println("currentJSONPath:" + currentJSONPath);
            return currentJSONPath;
        }
    }
    
    private void pushToJSONPath(String key){
       if(key!=null){ 
         jsonPath.push(key);
       }
       //System.out.println("Pushed:"+key);
    }
    
    private void popFromJSONPath(){
       String key=String.valueOf(jsonPath.pop());
       //System.out.println("Popped:"+key);
    }

    // Recursive method to compare two JSONObjects
    public boolean compareJSONObjects(JSONObject json1, JSONObject json2, String parentKey) throws Exception {
        if(parentKey!=null){
          pushToJSONPath(parentKey);
        }
        isSame = json1 == json2;
        Set<String> set1 = json1.keySet();
        Set<String> set2 = json2.keySet();
        Set<String> disjointInSet1 = new HashSet<>(set1);
        disjointInSet1.removeAll(set2);

        Set<String> disjointInSet2 = new HashSet<>(set2);
        disjointInSet2.removeAll(set1);
        String currentJSONPath=getCurrentJSONPath(null);
        if (disjointInSet1.size() > 0) {
            System.out.println("Elements/Props Extra IN JSON1: " + disjointInSet1+" AT PATH"+currentJSONPath);
        }
        if (disjointInSet2.size() > 0) {
            System.out.println("Elements/Props Extra IN JSON2" + disjointInSet2+" AT PATH"+currentJSONPath);
        }

        Set<String> commonKeys = new HashSet<>(set1);
        commonKeys.retainAll(set2);

        for (String key : commonKeys) {
            Object value1 = json1.get(key);
            Object value2 = json2.get(key);

            currentJSONPath=getCurrentJSONPath(key);
            
            if (value1 instanceof JSONObject && value2 instanceof JSONObject) {
                if (!compareJSONObjects((JSONObject) value1, (JSONObject) value2, key)) {
                    isSame = false;
                }
            } else if (value1 instanceof JSONArray && value2 instanceof JSONArray) {
                if (!compareJSONArrays((JSONArray) value1, (JSONArray) value2, key)) {
                    isSame = false;
                }
            } else if (!value1.equals(value2)) {
                System.out.println("Primitive value not matching: " + currentJSONPath +" : "+ String.valueOf(value1) + "!=" + String.valueOf(value2));
                isSame = false;
            }
        }
        if(parentKey!=null){
          popFromJSONPath();
        }
        return false;
    }

    // Recursive method to compare two JSONArrays
    public boolean compareJSONArrays(JSONArray array1, JSONArray array2, String parentKey) throws Exception {
        if (array1.length() == 0 && array2.length() == 0) {
            return true;
        }
        pushToJSONPath(parentKey+"[]");
        if (array1.length() != array2.length()) {
            System.out.println("Lengths of arrays are not matching");
        }
        ExtendedJSONArray eArray1 = new ExtendedJSONArray(array1);
        ExtendedJSONArray eArray2 = new ExtendedJSONArray(array2);

        String currentJSONPath=getCurrentJSONPath(null);

        String idGenJsonExpr = configJSON.optString(currentJSONPath);
        if(idGenJsonExpr==""){
           throw new Exception("KEY_MISSING_CONFIG_JSON:"+currentJSONPath);
        }
        System.out.println("ID gen expression for each element in the array: " + idGenJsonExpr);
        Map<Object, JSONObject> array1Map = eArray1.getIndexedJSONObjectMembers(idGenJsonExpr, 1, parentKey);
        Map<Object, JSONObject> array2Map = eArray2.getIndexedJSONObjectMembers(idGenJsonExpr, 2, parentKey);
        eArray1.comparePrimitives(eArray2);

        Set<Object> commonElementIds = new HashSet<>(array1Map.keySet());
        commonElementIds.retainAll(array2Map.keySet());

        for (Object eleID : commonElementIds) {
            String rootJsonElement=configJSON.optString("ROOT_ARRAY_ELEMENT");
            if(currentJSONPath.equalsIgnoreCase(rootJsonElement)){
                System.out.println("**************Start**************"+parentKey+":"+String.valueOf(eleID));
            }
            compareJSONObjects(array1Map.get(eleID), array2Map.get(eleID),null);
            if(currentJSONPath.equalsIgnoreCase(rootJsonElement)){
                System.out.println("**************End**************"+parentKey+":"+String.valueOf(eleID)+"\n");
            }
        }

        eArray1.compareJSONArrays(eArray2);

        Set<Object> disjointInSet1 = new HashSet<>(array1Map.keySet());
        disjointInSet1.removeAll(array2Map.keySet());

        Set<Object> disjointInSet2 = new HashSet<>(array2Map.keySet());
        disjointInSet2.removeAll(array1Map.keySet());
        if (disjointInSet1.size() > 0) {
            System.out.println("JSON1 extra:" + disjointInSet1+" AT PATH:"+currentJSONPath);
        }
        if (disjointInSet2.size() > 0) {
            System.out.println("JSON2 extra:" + disjointInSet2+" AT PATH:"+currentJSONPath);
        }
        popFromJSONPath();
        return false;
    }
}
