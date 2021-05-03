package com.example.superfiit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class CustomAdapter extends BaseAdapter {
    private ArrayList<String> numbers;
    private LayoutInflater layoutInflater;
    private Context context;

    private String code, createCode = "";

    private int countClicks = 4;

    public CustomAdapter(ArrayList<String> numbers, Context context, String code) {
        this.numbers = numbers;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.code = code;
    }

    @Override
    public int getCount() {
        return numbers.size();
    }

    @Override
    public Object getItem(int position) {
        return numbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null){
            view = layoutInflater.inflate(R.layout.element_number, parent, false);
        }
        TextView txtNumber = view.findViewById(R.id.txtNumber);
        LinearLayout llNumber = view.findViewById(R.id.llNumber);
        txtNumber.setText(numbers.get(position));
        llNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCode += numbers.get(position);
                countClicks--;
                Log.d("CreatedCode", "Code: " + createCode + "||Code: " + code);
                if(countClicks == 0){
                    if(code.toLowerCase().equals(createCode)){
                        Intent intent = new Intent(context, HomeActivity.class);
                        context.startActivity(intent);
                    } else{
                        createCode = "";
                        Toast.makeText(context, "Try more!", Toast.LENGTH_SHORT).show();
                    }
                    countClicks = 4;

                }
                Collections.shuffle(numbers);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
