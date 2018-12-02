package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mIngredientsIv;
    private TextView mMainNameTv;
    private TextView mAlsoKnownAs;

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
                .into(mIngredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void prepareUI()
    {
        mIngredientsIv = findViewById(R.id.iv_image);
        mMainNameTv = findViewById(R.id.tv_main_name);
        mAlsoKnownAs = findViewById(R.id.tv_also_known_as);
    }
    private void populateUI(Sandwich sandwich) {
        mMainNameTv.setText(sandwich.getMainName());

        String alsoKnownAs = null;
        //Log.d("sandwich","count: " + sandwich.getAlsoKnownAs().size());
        if(sandwich.getAlsoKnownAs() == null)
            alsoKnownAs = getString(R.string.detail_also_known_as_empty);
        else
            alsoKnownAs = getString(R.string.detail_also_known_as_label) + android.text.TextUtils.join(", ", sandwich.getAlsoKnownAs());
        mAlsoKnownAs.setText(alsoKnownAs);
    }
}
