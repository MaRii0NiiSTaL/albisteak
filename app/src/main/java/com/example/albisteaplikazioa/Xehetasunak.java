package com.example.albisteaplikazioa;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Xehetasunak extends AppCompatActivity {

    TextView tvTitularra, tvAzpitituloa, tvAzalpena;
    ImageView ivArgazkia;
    Button btAtzera;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xehetasunak);

        // Hautatutako titularra hartu intent-etik
        String titularSeleccionado = getIntent().getStringExtra("titular");

        // Ikuspegi guztiak hasieratu
        tvTitularra = findViewById(R.id.tvTitularra);
        tvAzpitituloa = findViewById(R.id.tvAzpitituloa);
        tvAzalpena = findViewById(R.id.tvAzalpena);
        ivArgazkia = findViewById(R.id.ivArgazkia);
        btAtzera = findViewById(R.id.btAtzera);

        // Datu basetik titularraren xehetasunak eskuratzeko kontsulta egin
        AlbisteakDBHelper dbHelper = new AlbisteakDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM albisteak WHERE titularra = ?", new String[]{titularSeleccionado});

        if (cursor.moveToFirst()) {
            // Datu basetik xehetasunak eskuratzen
            String azpitituloa = cursor.getString(cursor.getColumnIndex("azpitituloa"));
            String azalpena = cursor.getString(cursor.getColumnIndex("azalpena"));
            String argazkiaResourceName = cursor.getString(cursor.getColumnIndex("argazkia"));

            // Xehetasunak bistaratzen ikuspegietan
            tvTitularra.setText(titularSeleccionado);
            tvAzpitituloa.setText(azpitituloa);
            tvAzalpena.setText(azalpena);

            // Irudia Kargatu ImageView-etik drawable bidez
            @SuppressLint("DiscouragedApi") int resourceId = getResources().getIdentifier(argazkiaResourceName, "drawable", getPackageName());
            ivArgazkia.setImageResource(resourceId);

            // Itxi botoia konfiguratu
            btAtzera.setOnClickListener(v -> finish());
        }

        cursor.close();
        dbHelper.close();
    }
}
