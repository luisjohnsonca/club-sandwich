package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // COMPLETED - Replace findViewById methods for ButterKnife BindView methods.
    //Declare Widgets
    @BindView(R.id.origin_tv) TextView mOriginTextView;
    @BindView(R.id.also_known_tv)TextView mAlsoKnowAsTextView;
    @BindView(R.id.ingredients_tv)TextView mIngredientsTextView;
    @BindView(R.id.description_tv)TextView mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //ButterKnife Binding.
        ButterKnife.bind(this);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {


        // COMPLETED -  Show a message on TextViews when there is no data to show.
        // COMPLETED -  Use Android TextUtils.join() method to join Strings.

        //Populate Origin Text View
        if(!TextUtils.isEmpty(sandwich.getPlaceOfOrigin())){
            mOriginTextView.append(sandwich.getPlaceOfOrigin() +".");
        } else {
            mOriginTextView.append(getString(R.string.no_data_available_msg));
        }

        //Populate Also Know As Text View
        if(!sandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnowAsTextView.append(TextUtils.join(" ,",sandwich.getAlsoKnownAs()));
            mAlsoKnowAsTextView.append(".");
        } else {
            mAlsoKnowAsTextView.append(getString(R.string.no_data_available_msg));
        }

        //Populate Ingredients Text View
        if(!sandwich.getIngredients().isEmpty()){
            mIngredientsTextView.append(TextUtils.join(" ,", sandwich.getIngredients()));
            mIngredientsTextView.append(".");
        } else {
            mIngredientsTextView.append(getString(R.string.no_data_available_msg));
        }

        //Populate Description Text View
        if(!TextUtils.isEmpty(sandwich.getDescription())) {
            mDescriptionTextView.append(sandwich.getDescription());
        } else {
            mDescriptionTextView.append(getString(R.string.no_data_available_msg));
        }
    }
}
