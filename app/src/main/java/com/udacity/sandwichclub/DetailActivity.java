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

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Declare Widgets
    TextView mOriginTextView;
    TextView mAlsoKnowAsTextView;
    TextView mIngredientsTextView;
    TextView mDescriptionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        // TODO - Replace findViewById methods for ButterKnife BindView methods.
        // TODO -  Show a message on TextViews when there is no data to show.
        //Populate Origin Text View
        mOriginTextView = findViewById(R.id.origin_tv);
        mOriginTextView.append(sandwich.getPlaceOfOrigin() +".");

        //Populate Also Know As Text View
        mAlsoKnowAsTextView = findViewById(R.id.also_known_tv);

        // TODO -  Use Android TextUtils.join() method to get Strings.

       List<String> list  = sandwich.getAlsoKnownAs();
       for(int i = 0; i < list.size(); i++ )
       {
           mAlsoKnowAsTextView.append(i < list.size() - 1 ? list.get(i) + " ," : list.get(i) + ".");
       }

        //Populate Ingredients Text View
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        for(String value:sandwich.getIngredients())
        {
            mIngredientsTextView.append(" - " + value + ".\n");
        }

        //Populate Description Text View
        mDescriptionTextView = findViewById(R.id.description_tv);
        mDescriptionTextView.append(sandwich.getDescription());


    }
}
