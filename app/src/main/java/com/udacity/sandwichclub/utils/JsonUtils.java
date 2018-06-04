package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject mainObject = new JSONObject(json);
            JSONObject nameObject = mainObject.getJSONObject("name");
            String placeOfOrigin = mainObject.getString("placeOfOrigin");
            String description = mainObject.getString("description");
            String imageUrl = mainObject.getString("image");
            JSONArray ingredients = mainObject.getJSONArray("ingredients");
            String mainName = nameObject.getString("mainName");
            JSONArray alsoKnownAsArray = nameObject.getJSONArray("alsoKnownAs");


            List<String> ingredientsList = new ArrayList<String>();
            if (ingredients != null) {
                for (int i = 0; i < ingredients.length(); i++) {
                    ingredientsList.add(ingredients.getString(i));
                }
            }

            List<String> alsoKnownAsList = new ArrayList<String>();
            if (alsoKnownAsArray != null) {
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    alsoKnownAsList.add(alsoKnownAsArray.getString(i));
                }
            }

            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, imageUrl, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
