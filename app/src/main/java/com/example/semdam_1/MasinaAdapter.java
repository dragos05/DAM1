package com.example.semdam_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;


public class MasinaAdapter extends ArrayAdapter<Masina>{
    private Context mContext;
    private List<Masina> listaMasini;

    public MasinaAdapter(@NonNull Context context, @NonNull List<Masina> listaMasini) {
        super(context, 0, listaMasini);
        this.mContext = context;
        this.listaMasini = listaMasini;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            // Inflating the custom layout
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_masina, parent, false);
        }

        // Get the current Masina object
        Masina currentMasina = listaMasini.get(position);

        // Find the TextViews in the custom layout and set their values
        TextView tvMarcaPret = convertView.findViewById(R.id.editTextPret);
        TextView tvDetails = convertView.findViewById(R.id.editTextDate);

        // Set the values to display
        tvMarcaPret.setText(currentMasina.getMarca() + " - " + currentMasina.getPret() + " EUR");
        tvDetails.setText("Data fabricatie: " + currentMasina.getDataFabricatie().toString() +
                ", Motorizare: " + currentMasina.getMotorizare());

        return convertView;
    }
}
