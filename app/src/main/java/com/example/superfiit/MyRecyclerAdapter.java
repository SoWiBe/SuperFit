package com.example.superfiit;

import android.content.Context;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

public class MyRecyclerAdapter  extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> implements Filterable {

    private ArrayList<RecipesElement> recipesElements;

    private ArrayList<RecipesElement> recipesElementsFull;
    private Context context;
    public MyRecyclerAdapter(Context context, ArrayList<RecipesElement> recipesElements){
        this.context = context;
        this.recipesElements = recipesElements;
        recipesElementsFull = new ArrayList<>(recipesElements );
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_recipes_element, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RecipesElement recipesElement = recipesElements.get(position);
        holder.txtNameElement.setText(recipesElement.getName());
        holder.txtProteinElement.setText(recipesElement.getProtein());
        holder.txtKcalElement.setText(recipesElement.getKcal());
        holder.txtCardsElement.setText(recipesElement.getCards());
        holder.txtFatElement.setText(recipesElement.getFat());
        holder.mainLLRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeScreen.class);
                intent.putExtra("recipe", recipesElements.get(position));
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(recipesElement.getImage()).into(holder.imgRecipeElement);
    }

    @Override
    public int getItemCount() {
        return recipesElements.size();
    }

    public void filteredList(ArrayList<RecipesElement> filteredList) {
        recipesElements = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public Filter getFilter() {
        return recipeFilter;
    }
    private Filter recipeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           ArrayList<RecipesElement> filteredList = new ArrayList<>();

           if(constraint == null || constraint.length() == 0){
               filteredList.addAll(recipesElementsFull);
           } else {
               String filterPattern = constraint.toString().toLowerCase().trim();

               for(RecipesElement item : recipesElementsFull){
                   if(item.getName().toLowerCase().contains(filterPattern)){
                       filteredList.add(item);
                   }
               }
           }

           FilterResults results = new FilterResults();
           results.values = filteredList;

           return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            recipesElements.clear();
            recipesElements.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameElement, txtKcalElement, txtProteinElement, txtFatElement, txtCardsElement;
        ImageView imgRecipeElement;
        LinearLayout mainLLRecipe;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCardsElement = itemView.findViewById(R.id.txtCardsElement);
            txtFatElement = itemView.findViewById(R.id.txtFatElement);
            txtKcalElement = itemView.findViewById(R.id.txtKcallElement);
            txtNameElement = itemView.findViewById(R.id.txtNameElement);
            txtProteinElement = itemView.findViewById(R.id.txtProteinElement);

            imgRecipeElement = itemView.findViewById(R.id.imgRecipeElement);

            mainLLRecipe = itemView.findViewById(R.id.mainLLRecipe);
        }
    }
}
