package com.example.albisteaplikazioa;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlbisteakDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "albisteak.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ALBISTEAK =
            "CREATE TABLE IF NOT EXISTS albisteak (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "titularra TEXT NOT NULL," +
                    "azpitituloa TEXT NOT NULL," +
                    "azalpena TEXT NOT NULL," +
                    "argazkia TEXT NOT NULL);";

    public AlbisteakDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALBISTEAK);

        // Datubasean insertak egin
        insertaAlbisteak(db, "Granada-Athletic partiduan afizionatua hil da", "Abenduak 10ean jokatutako partidoan granadako afizionatu bat hil zen", "Granada Athletic partidoa jokatzen zegoen bitartean minutu 18an partidua gelditu zuten gradako pertsona bati laguntzeko, azkenean pertsona hori hil egin zen", "@drawable/granada_atlhletic");
        insertaAlbisteak(db, "Beste albiste bat", "Beste azpitituloa", "Beste azalpena", "@drawable/argazkia2");
        insertaAlbisteak(db, "Hirugarren albiste bat", "Hirugarren azpitituloa", "Hirugarren azalpena", "@drawable/argazkia3");
        insertaAlbisteak(db, "Laugarren albiste bat", "Laugarren azpitituloa", "Laugarren azalpena", "@drawable/argazkia4");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void insertaAlbisteak(SQLiteDatabase db, String titularra, String azpitituloa, String azalpena, String argazkia) {
        ContentValues values = new ContentValues();
        values.put("titularra", titularra);
        values.put("azpitituloa", azpitituloa);
        values.put("azalpena", azalpena);
        values.put("argazkia", argazkia);

        // Inserta albiste taulan"
        db.insert("albisteak", null, values);
    }
}
