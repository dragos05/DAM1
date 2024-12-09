package com.example.semdam_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class BNRActivity extends AppCompatActivity {

    public static final String ADD_CURS_VALUTAR = "addCursValutar";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bnr);
        //seteaza continutul vizual pe baza lui activity_main
        //R.layout.activity_main - adresa in format hexa carea realizeaza legatura intre codul Java si codul XML

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

//        Button btn1 = new Button(this);
        //this -  referinta la contextul curent al activitatii


        Log.e("lifecycle","Am apelat OnCreate");

        TextView tvData = findViewById(R.id.tvDataCurs);
        EditText etEUR = findViewById(R.id.editTextEUR);
        EditText etUSD = findViewById(R.id.editTextUSD);
        EditText etGBP = findViewById(R.id.editTextGBP);
        EditText etXAU = findViewById(R.id.editTextXAU);

        Intent intent = getIntent();

        //        Button btn1 = new Button(this); - buton nou creat in cod java
        //this -  referinta la contextul curent al activitatii
        Button btn1 = findViewById(R.id.btnAfisare);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etEUR.setText("4.967");
                etUSD.setText("4.789");
                etGBP.setText("6.567");
                etXAU.setText("320.789");

                Toast.makeText(getApplicationContext(),
                        "Am afisat cursul valutar!",
                        Toast.LENGTH_LONG).show(); // sunt acele notifcari mici din android din partea de jos a aplicatiei


                CursValutar cursValutar = new CursValutar("12/11/2024",
                        etEUR.getText().toString(),
                        etUSD.getText().toString(),
                        etGBP.getText().toString(),
                        etXAU.getText().toString());

                intent.putExtra(ADD_CURS_VALUTAR,cursValutar);
                setResult(RESULT_OK, intent);
                finish();


            }
        });

        Button btn2 = findViewById(R.id.btnSalvare);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CursValutar cursValutar = new CursValutar("12/11/2024",
//                        etEUR.getText().toString(),
//                        etUSD.getText().toString(),
//                        etGBP.getText().toString(),
//                        etXAU.getText().toString());
//
//                Intent intent2 = new Intent(getApplicationContext(), AddActivityBNR.class);
//                intent2.putExtra("curs",cursValutar);
//                setResult(RESULT_OK, intent);

                Network network = new Network()
                {
                    @Override
                    protected void onPostExecute(InputStream inputStream) {
//                        Toast.makeText(getApplicationContext(),
//                                Network.rezultat, Toast.LENGTH_LONG).show();
                        tvData.setText(cv.getDataCurs());
                        etEUR.setText(cv.getCursEUR());
                        etUSD.setText(cv.getCursUSD());
                        etGBP.setText(cv.getCursGBP());
                        etXAU.setText(cv.getCursXAU());

                    }
                };

                try {
                    network.execute(new URL("https://bnr.ro/nbrfxrates.xml"));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("lifecycle","Am apelat OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("lifecycle","Am apelat OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("lifecycle","Am apelat OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("lifecycle","Am apelat OnStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("lifecycle","Am apelat OnRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("lifecycle","Am apelat OnDestroy");
    }
}