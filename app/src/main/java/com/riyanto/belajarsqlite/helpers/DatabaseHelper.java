package com.riyanto.belajarsqlite.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DB_NAME = "kampus.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.i(TAG, "DatabaseHelper: diinstansiasi");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE mahasiswa (nim TEXT, nama TEXT, prodi TEXT)";
        db.execSQL(sql);

        Log.i(TAG, "onCreate: dieksekusi");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS mahasiswa";
        db.execSQL(sql);

        onCreate(db);
        Log.i(TAG, "onUpgrade: dieksekusi");
    }
    public boolean updateMahasiswa(String nim, String nama, String prodi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama",nama);
        values.put("prodi",prodi);

        int rows = db.update("mahasiswa", values, "nim = ?", new String[]{nim});
        db.close();

        if (rows > 0) {
            Log.i(TAG, "updateMahasiswa: berhasil memperbarui data mahasiswa");
            return true;
        } else {
            Log.i(TAG, "updateMahasiswa: gagal memperbarui data mahasiswa");
            return false;
        }
    }
}


