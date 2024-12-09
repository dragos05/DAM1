package com.example.semdam_1;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD = 100;

    public static final int REQUEST_CODE_EDIT = 200;

    public static final String EDIT_MASINA = "editareMasina";

    public int poz;

    private Intent intent;

    private ListView listView;

    private List<Masina> listaMasini = new ArrayList<>();

    private MasinaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.listViewMasini);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                poz=position;
                intent = new Intent(getApplicationContext(), AddActivity.class);
                intent.putExtra(EDIT_MASINA, listaMasini.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        //stergere element din list view cu long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Masina masina = listaMasini.get(position);

                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirmare stergere!")
                        .setMessage("Sigur doriti stergerea?")
                        .setNegativeButton("NU", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(),
                                        "Nu am sters nimic!", Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listaMasini.remove(masina);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),
                                        "Am sters " + masina.toString(), Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        })
                        .create();

                dialog.show();
                return true;
            }
        });

        //editare element din lista
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Masina masina = (Masina) adapterView.getItemAtPosition(position);
//
//                Intent intent = new Intent(MainActivity.this,AddActivity.class);
//                intent.putExtra("marca")
//            }
//        });


        // Inițializează adapterul personalizat
        MasinaAdapter adapter = new MasinaAdapter(this, listaMasini);
        listView.setAdapter(adapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK && data!=null)
        {
            Masina masina = (Masina) data.getSerializableExtra(AddActivity.ADD_MASINA);
            if(masina!=null) {
                listaMasini.add(masina);
//                ArrayAdapter<Masina> adapter = new ArrayAdapter<>
//                        (this, android.R.layout.simple_list_item_1, listaMasini);

                CustomAdapter adapter  = new CustomAdapter(this,
                        R.layout.elem_list_view,
                        listaMasini,
                        getLayoutInflater());


                listView.setAdapter(adapter);


//                listaMasini.add(masina);
//                // Notifică adapterul că lista de date s-a modificat
                //adapter.notifyDataSetChanged();

//                listaMasini.add(masina);
//                adapter.notifyDataSetChanged(); // Notify the adapter about the data change
//            } else {
//                // Handle the case where masina is null (e.g., show error message)
//                Toast.makeText(this, "Error adding car. Please try again.", Toast.LENGTH_SHORT).show();
//            }

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu_principal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.optiune1)
        {
            Intent intent1 = new Intent(getApplicationContext(), BNRActivity.class);
            startActivity(intent1);
            return true;
        }
        else if(item.getItemId()==R.id.optiune2)
        {
            ExtractXML extractXML = new ExtractXML()
            {
                @Override
                protected void onPostExecute(InputStream inputStream) {
                    listaMasini.addAll(this.masinaList);

                    CustomAdapter adapter  = new CustomAdapter(getApplicationContext(),
                            R.layout.elem_list_view,
                            listaMasini,
                            getLayoutInflater());


                    listView.setAdapter(adapter);

                }
            };
            try {
                extractXML.execute(new URL("https://pastebin.com/raw/Duf43Nyq"));
            } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
            }
            return true;
        }
        else if(item.getItemId()==R.id.optiune3)
        {


            ExtractJSON extractJSON = new ExtractJSON()
            {

                ProgressDialog dialog;

                @Override
                protected void onPreExecute() {
                    dialog =new ProgressDialog(MainActivity.this);
                    dialog.setMessage("Please wait...");
                    dialog.show();
                }

                @Override
                protected void onPostExecute(String s) {
                    dialog.cancel();
                    listaMasini.addAll(masinaListJSON);

                    CustomAdapter adapter  = new CustomAdapter(getApplicationContext(),
                            R.layout.elem_list_view,
                            listaMasini,
                            getLayoutInflater());


                    listView.setAdapter(adapter);
                }
            };

            try {
                extractJSON.execute(new URL("https://pastebin.com/raw/L84K1DxZ"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        return false;
    }
}