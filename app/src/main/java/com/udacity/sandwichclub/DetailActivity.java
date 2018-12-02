package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mSandwichImageIv;
    private TextView mMainNameTv;
    private TextView mAlsoKnownAsTv;
    private TextView mPlaceOfOriginTv;
    private TextView mDescriptionTv;
    private TextView mIngredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        prepareUI();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage()).placeholder(R.drawable.baseline_image_white_48dp)
                .error(R.drawable.baseline_broken_image_white_48dp)
                .into(mSandwichImageIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void prepareUI() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSandwichImageIv = findViewById(R.id.iv_image);
        mMainNameTv = findViewById(R.id.tv_main_name);
        mAlsoKnownAsTv = findViewById(R.id.tv_also_known_as);
        mPlaceOfOriginTv = findViewById(R.id.tv_place_of_origin);
        mDescriptionTv = findViewById(R.id.tv_description);
        mIngredientsTv = findViewById(R.id.tv_ingredients);
    }

    private void populateUI(Sandwich sandwich) {
        mMainNameTv.setText(sandwich.getMainName());

        String alsoKnownAs = null;
        if (sandwich.getAlsoKnownAs() == null)
            alsoKnownAs = getString(R.string.detail_also_known_as_empty);
        else
            alsoKnownAs = android.text.TextUtils.join(", ", sandwich.getAlsoKnownAs());
        mAlsoKnownAsTv.setText(alsoKnownAs);

        if(sandwich.getPlaceOfOrigin().length() == 0)
            mPlaceOfOriginTv.setText(getString(R.string.detail_not_known));
        else
            mPlaceOfOriginTv.setText(sandwich.getPlaceOfOrigin());

        mDescriptionTv.setText(sandwich.getDescription());

        String ingredients = null;
        if (sandwich.getIngredients() == null)
            ingredients = getString(R.string.detail_not_known);
        else
            ingredients = android.text.TextUtils.join(", ", sandwich.getIngredients());
        mIngredientsTv.setText(ingredients);
    }
}
