package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        JSONObject sandwichJSON = null;
        Sandwich sandwich = null;
        try {
            sandwichJSON = new JSONObject(json);
            JSONObject name = sandwichJSON.getJSONObject("name");
            String mainName = name.getString("mainName");

            String aliases = name.getString("alsoKnownAs");
            aliases = aliases.replace("[","");
            aliases = aliases.replace("]","");
            List<String> alsoKnownAs = new ArrayList<String>(Arrays.asList(aliases.split(",")));

            String placeOfOrigin = sandwichJSON.getString("placeOfOrigin");

            String description = sandwichJSON.getString("description");

            String image = sandwichJSON.getString("image");

            String ingredientsList = sandwichJSON.getString("ingredients");
            ingredientsList = ingredientsList.replace("[","");
            ingredientsList = ingredientsList.replace("]","");
            List<String> ingredients = new ArrayList<String>(Arrays.asList(ingredientsList.split(",")));

            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
