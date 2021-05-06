package com.example.superfiit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class IngredientAdapter extends BaseAdapter {
    private ArrayList<String> ingredients;
    private Context context;
    private LayoutInflater layoutInflater;

    public IngredientAdapter(ArrayList<String> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.ingredient_element, parent, false);
        if(convertView == null){
            view = layoutInflater.inflate(R.layout.ingredient_element, parent, false);
        }
        TextView txtIngredient = view.findViewById(R.id.txtIngredient);
        txtIngredient.setText(ingredients.get(position));
        return view;
    }
}
