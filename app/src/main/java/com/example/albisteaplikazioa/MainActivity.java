package com.example.albisteaplikazioa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Berriak bistaratzeko testu-etiketa lortu
        TextView berriakTextView = findViewById(R.id.Berriak);
        // ListView lortu
        ListView listView = findViewById(R.id.listView);

        // Datu basera atzipen egiteko laguntzailea sortu
        AlbisteakDBHelper dbHelper = new AlbisteakDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Titularrak lortu datu basetik
        ArrayList<String> titularesList = obtenerTitulares(db);

        // ListView-erako adapter-a konfiguratu
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titularesList);
        listView.setAdapter(adapter);

        // ListView-erako OnItemClickListener-a gehitu
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Hautatutako titularra lortu
                String titularSeleccionado = (String) parent.getItemAtPosition(position);

                // Xehetasunen (Xehetasunak) aktibitatea ireki eta hautatutako titularra pasa
                Intent intent = new Intent(MainActivity.this, Xehetasunak.class);
                intent.putExtra("titular", titularSeleccionado);
                startActivity(intent);
            }
        });

        dbHelper.close();
    }

    // Datu basetik titularrak lortu
    private ArrayList<String> obtenerTitulares(SQLiteDatabase db) {
        ArrayList<String> titularesList = new ArrayList<>();

        // Datu basetik kontsulta egin
        Cursor cursor = db.rawQuery("SELECT titularra FROM albisteak", null);

        // Titularrak lortu eta listan sartu
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String titular = cursor.getString(cursor.getColumnIndex("titularra"));
                titularesList.add(titular);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return titularesList;
    }
}
