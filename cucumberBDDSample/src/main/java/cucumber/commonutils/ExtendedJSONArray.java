package com.praveen;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ExtendedJSONArray {

    public Set<Object> primitiveMembers=new HashSet<>();
    public Set<JSONObject> jsonObjects=new HashSet<>();
    public Set<JSONArray> jsonArrays=new HashSet<>();

    public ExtendedJSONArray(JSONArray array){
        processArray(array); 
    }

    public void processArray(JSONArray array){
         for(Object value: array){
            if (value instanceof JSONObject) {
                jsonObjects.add((JSONObject)value);
            } else if (value instanceof JSONArray ) {
                jsonArrays.add((JSONArray)value);
            } else{
                primitiveMembers.add(value);
            }
         }   
    }

    public boolean comparePrimitives(ExtendedJSONArray array2){
        boolean isSame=true;
        Set<Object> disjointInSet1 = new HashSet<>(this.primitiveMembers);
        disjointInSet1.removeAll(array2.primitiveMembers);

        Set<Object> disjointInSet2 = new HashSet<>(array2.primitiveMembers);
        disjointInSet2.removeAll(this.primitiveMembers);

        if(disjointInSet1.size()>0){
            System.out.println("Elements not in 2nd JSON: " + disjointInSet1);
            isSame=false;
        }
        if(disjointInSet2.size()>0){
            System.out.println("Elements not in 1st JSON: " + disjointInSet2);
            isSame=false;
        }
        //common values need not to be checked
        return isSame;
    }

    public Map<Object,JSONObject> getIndexedJSONObjectMembers(String jsonPathExpr,int number,String parentTag){
        Map<Object, JSONObject> indexedObjects = new HashMap<>();
        for (JSONObject jsonObject:this.jsonObjects) {
            try{
                Object id = JsonPath.parse(jsonObject.toString()).read(jsonPathExpr);
                indexedObjects.put(id, jsonObject);
            }catch(Exception e){
                System.out.println("Parent Tag:"+parentTag+" document:"+number+" JSON PATH Failed:"+jsonPathExpr+" in:"+jsonObject.toString());
            }
        }
        return indexedObjects;
    }

    public boolean compareJSONArrays(ExtendedJSONArray array2){
        boolean isSame=true;
        if(this.jsonArrays.size()>0 || array2.jsonArrays.size()>0){
            System.out.println("Comparing Arrays Direct Descendent Arrays ignored");
        }
        return isSame;
    }
}
