package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.Constants.JSONUtilConstants;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        JSONObject sandwichJSON;
        Sandwich sandwich = null;
        try {
            sandwichJSON = new JSONObject(json);
            if (sandwichJSON.has(JSONUtilConstants.KEY_NAME)) {
                JSONObject name = sandwichJSON.getJSONObject(JSONUtilConstants.KEY_NAME);

                String mainName = JSONUtilConstants.NOT_AVAILABLE;
                if(name.has(JSONUtilConstants.KEY_MAIN_NAME))
                    mainName = name.getString(JSONUtilConstants.KEY_MAIN_NAME);

                String aliases = JSONUtilConstants.NOT_AVAILABLE;
                List<String> alsoKnownAs = null;
                if(name.has(JSONUtilConstants.KEY_ALSO_KNOWN_AS))
                {
                    aliases = name.getString(JSONUtilConstants.KEY_ALSO_KNOWN_AS);
                    aliases = aliases.replace("[", "");
                    aliases = aliases.replace("]", "");
                    aliases = aliases.replace("\"", "");
                    if (aliases.length() != 0)
                        alsoKnownAs = new ArrayList<>(Arrays.asList(aliases.split(",")));
                }


                String placeOfOrigin = JSONUtilConstants.NOT_AVAILABLE;
                if(name.has(JSONUtilConstants.KEY_PLACE_OF_ORIGIN))
                    placeOfOrigin = name.getString(JSONUtilConstants.KEY_PLACE_OF_ORIGIN);

                String description = JSONUtilConstants.NOT_AVAILABLE;
                if(sandwichJSON.has(JSONUtilConstants.KEY_DESCRIPTION))
                    description = sandwichJSON.getString(JSONUtilConstants.KEY_DESCRIPTION);

                String image = null;
                if(sandwichJSON.has(JSONUtilConstants.KEY_IMAGE))
                    image = sandwichJSON.getString(JSONUtilConstants.KEY_IMAGE);

                String ingredientsList = null;
                List<String> ingredients = null;
                if(sandwichJSON.has(JSONUtilConstants.KEY_INGREDIENTS))
                {
                    ingredientsList = sandwichJSON.getString(JSONUtilConstants.KEY_INGREDIENTS);
                    ingredientsList = ingredientsList.replace("[", "");
                    ingredientsList = ingredientsList.replace("]", "");
                    ingredientsList = ingredientsList.replace("]", "");
                    ingredientsList = ingredientsList.replace("\"", "");
                    if (ingredientsList.length() != 0)
                        ingredients = new ArrayList<>(Arrays.asList(ingredientsList.split(",")));
                }


                sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
