package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ActivityDetailBinding mActivityDetailBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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

        String description = sandwich.getDescription();
        if (!TextUtils.isEmpty(description)) {
            mActivityDetailBinding.descriptionTv.setText(description);
        } else {
            mActivityDetailBinding.descriptionTv.setText(getString(R.string.placeHolder));
        }

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (!TextUtils.isEmpty(placeOfOrigin)) {
            mActivityDetailBinding.originTv.setText(placeOfOrigin);
        } else {
            mActivityDetailBinding.originTv.setText(getString(R.string.placeHolder));
        }

        List<String> ingredients = sandwich.getIngredients();
        if(ingredients.size()>0) {
            for (int i = 0; i < ingredients.size(); i++) {
                mActivityDetailBinding.ingredientsTv.append(ingredients.get(i));
                if (i != (ingredients.size() - 1)) {
                    mActivityDetailBinding.ingredientsTv.append("\n");
                }
            }
        }else{
            mActivityDetailBinding.ingredientsTv.append(getString(R.string.placeHolder));
        }

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if(alsoKnownAsList.size()>0) {
            for (int i = 0; i < alsoKnownAsList.size(); i++) {
                mActivityDetailBinding.alsoKnownTv.append(alsoKnownAsList.get(i));
                if (i != (alsoKnownAsList.size() - 1)) {
                    mActivityDetailBinding.alsoKnownTv.append("\n");
                }
            }
        }else{
            mActivityDetailBinding.alsoKnownTv.append(getString(R.string.placeHolder));
        }

        // {"name":{"mainName":"Ham and cheese sandwich","alsoKnownAs":[]},"placeOfOrigin":"","description":"A ham and cheese sandwich is a common type of sandwich. It is made by putting cheese and sliced ham between two slices of bread. The bread is sometimes buttered and/or toasted. Vegetables like lettuce, tomato, onion or pickle slices can also be included. Various kinds of mustard and mayonnaise are also common.","image":"https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Grilled_ham_and_cheese_014.JPG/800px-Grilled_ham_and_cheese_014.JPG","ingredients":["Sliced bread","Cheese","Ham"]}

    }
}
