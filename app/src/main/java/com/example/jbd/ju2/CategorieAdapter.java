package com.example.jbd.ju2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jbd.ju2.models.Categorie;

import java.util.List;

/**
 * Created by jenniferbrody on 5/03/17.
 */

public class CategorieAdapter extends ArrayAdapter<Categorie> {

    Context context;

    public CategorieAdapter(Context context, List<Categorie> listCategorie){
        super(context, -1, listCategorie);
        this.context = context;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        View view;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listitemevent, parent);
        } else {
            view = convertView;
        }

        Categorie categorie = getItem(pos);

        TextView name = (TextView)view.findViewById(R.id.e0);

        name.setText(categorie.getName());
        return view;
    }
}

