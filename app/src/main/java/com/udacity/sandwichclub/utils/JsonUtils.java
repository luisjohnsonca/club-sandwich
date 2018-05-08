package com.udacity.sandwichclub.utils;

import android.util.Log;
import android.widget.Toast;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JsonUtils {

    public static final String TAG = JsonUtils.class.getName();

    public static Sandwich parseSandwichJson(String json) {


        try {

            //Create JSONObject from json string
            JSONObject sandwichJson = new JSONObject(json);

            //Create a Sandwich Object
            Sandwich sandwich = new Sandwich();

            //Get nested JSONObject
            JSONObject names = new JSONObject(sandwichJson.getString("name"));

            //Get maiName attribute
            sandwich.setMainName(names.getString("mainName"));

            //Get alsoKnowAs attribute
            JSONArray nameArray = names.getJSONArray("alsoKnownAs");
            sandwich.setAlsoKnownAs(getValuesFromJsonArray(nameArray));

            //Get placeOfOrigin attribute
            sandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));

            //Get description attribute
            sandwich.setDescription(sandwichJson.getString("description"));

            //Get image attribute
            sandwich.setImage(sandwichJson.getString("image"));

            //Get ingredients attribute
            JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
            sandwich.setIngredients(getValuesFromJsonArray(ingredientsArray));

            //Return Sandwich object
            return sandwich;

        } catch (JSONException e) {
            //Catch JSON Exception
            Log.e(TAG,e.getMessage());
            return null;
        }

    }


    private static List<String> getValuesFromJsonArray(JSONArray jArray) throws JSONException {
        //This function iterates over a JSONArray and add every item to a list,
        int length = jArray.length();
        List<String> list = new ArrayList<String>();
        if(jArray != null || length == 0)
        {
            for(int i = 0;i < length;i++)
            {
                list.add(jArray.get(i).toString());
            }

        } else {
            return null;
        }
        return list;

    }
}
