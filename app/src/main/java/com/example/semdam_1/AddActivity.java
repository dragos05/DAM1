package com.example.semdam_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    public static final String ADD_MASINA = "addMasina";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();


        //populare spinner cu vector de stringuri
        String[] culori = {"ALB", "NEGRU", "ROSU", "GRI","ALBASTRU"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                culori);

        Spinner spinerCulori = findViewById(R.id.spinnerCulori);
        spinerCulori.setAdapter(adapter);

        EditText etMarca = findViewById(R.id.editTextMarca);
        EditText etDataFabricatie = findViewById(R.id.editTextDate);
        EditText etPret = findViewById(R.id.editTextPret);
        RadioGroup radioGroup = findViewById(R.id.radiogroup);

        if (intent.hasExtra(MainActivity.EDIT_MASINA))
        {
            Masina masina = (Masina) intent.getSerializableExtra(MainActivity.EDIT_MASINA);

            etMarca.setText(masina.getMarca());
            etDataFabricatie.setText(new SimpleDateFormat("MM/dd/yyyy", Locale.US)
                    .format(masina.getDataFabricatie()));
            etPret.setText(masina.getPret()+"");
            //setarea spinerr-ului cu culoarea specifica
            ArrayAdapter<String> adapter1 = (ArrayAdapter<String>) spinerCulori.getAdapter();
            for(int i=0;i<adapter1.getCount();i++)
                if(adapter1.getItem(i).equals(masina.getCuloare()))
                {
                    spinerCulori.setSelection(i);
                    break;
                }
            if(masina.getMotorizare().equals("BENZINA"))
                radioGroup.check(R.id.radioBenzina);
            else if(masina.getMotorizare().equals("HIBRID"))
                radioGroup.check(R.id.radioHibrid);
            else if(masina.getMotorizare().equals("DIESEL"))
                radioGroup.check(R.id.radioDisel);
            else if(masina.getMotorizare().equals("ELECTRIC"))
                radioGroup.check(R.id.radioElectric);
        }



        Button btnAdauga = findViewById(R.id.btnAdauga);

        btnAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etMarca.getText().toString().isEmpty())
                    etMarca.setError("Introduceti marca!");
                else if(etDataFabricatie.getText().toString().isEmpty())
                    etDataFabricatie.setError("Introduceti data fabricatiei!");
                else if(etPret.getText().toString().isEmpty())
                    etPret.setError("Introduceti petul!");
                else {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    try {
                        sdf.parse(etDataFabricatie.getText().toString());
                        Date dataFabricatiei = new Date(etDataFabricatie.getText().toString());

                        String marca = etMarca.getText().toString();


                        float pret = Float.parseFloat(etPret.getText().toString());

                        String culoare = spinerCulori.getSelectedItem().toString();

                        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

                        String motorizare = radioButton.getText().toString();

                        Masina masina = new Masina(marca, dataFabricatiei,pret, culoare, motorizare);

                        //Toast.makeText(getApplicationContext(),
                         //       "Masina creata!", Toast.LENGTH_LONG).show();

                        //-------------------
                        MasiniDB database = MasiniDB.getInstance(getApplicationContext());
                        database.getMasiniDao().insert(masina);


                        intent.putExtra(ADD_MASINA, masina);
                        setResult(RESULT_OK, intent);
                        finish();


                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    catch (Exception ex)
                    {
                        Log.e("AddActivity", "Eroare introducere date");
                        Toast.makeText(getApplicationContext(),
                                "Eroare introducere date", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });


    }
}