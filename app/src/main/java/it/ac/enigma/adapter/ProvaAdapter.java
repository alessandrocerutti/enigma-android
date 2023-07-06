package it.ac.enigma.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import it.ac.enigma.model.ProvaDto;

public class ProvaAdapter extends ArrayAdapter<ProvaDto> {
    private LayoutInflater inflater;

    public ProvaAdapter(Context context, List<ProvaDto> objects) {
        super(context, android.R.layout.simple_spinner_item, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = view.findViewById(android.R.id.text1);
        ProvaDto item = getItem(position);
        textView.setText(item != null ? item.getDescrizione() : "");
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        TextView textView = view.findViewById(android.R.id.text1);
        ProvaDto item = getItem(position);
        textView.setText(item != null ? item.getDescrizione() : "");
        return view;
    }
}
