package com.example.semdam_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class AddActivityBNR extends AppCompatActivity {

    public static final int REQUES_CODE_ADD =100;

    private Intent intent;

    private ListView listView;

    private List<CursValutar> listaCursValutar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_bnr);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //adaugare in listview fara custom adapter

        listView = findViewById(R.id.listviewcursvalutar);

        Intent intent = getIntent();
        if(intent!=null) {
            CursValutar cursValutar = (CursValutar) intent.getSerializableExtra("curs");
            listaCursValutar.add(cursValutar);

            ArrayAdapter<CursValutar> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    listaCursValutar
            );

            listView.setAdapter(adapter);
        }


    }
}