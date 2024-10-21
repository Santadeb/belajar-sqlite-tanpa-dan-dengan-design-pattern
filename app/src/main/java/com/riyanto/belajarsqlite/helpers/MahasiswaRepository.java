package com.riyanto.belajarsqlite.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.riyanto.belajarsqlite.models.Mahasiswa;

import java.util.ArrayList;

public class MahasiswaRepository {

    private static DatabaseHelper databaseHelper;

    public MahasiswaRepository(Context context) {
        databaseHelper = Singleton.getInstance(context);
    }

    public ArrayList<Mahasiswa> tampilMahasiswa() {
        ArrayList<Mahasiswa> mahasiswaList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM mahasiswa", null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                mahasiswaList.add(
                        new Mahasiswa(
                                cursor.getString(cursor.getColumnIndexOrThrow("nim")),
                                cursor.getString(cursor.getColumnIndexOrThrow("nama")),
                                cursor.getString(cursor.getColumnIndexOrThrow("prodi"))
                        )
                );
            }
            cursor.close(); // Pastikan cursor ditutup setelah selesai digunakan
        }
        return mahasiswaList;
    }

    public void tambahMahasiswa(Mahasiswa m) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nim", m.getNim());
        values.put("nama", m.getNama());
        values.put("prodi", m.getProdi());
        db.insert("mahasiswa", null, values);
    }

    public void updateMahasiswa(String oldNim, Mahasiswa m) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nim", m.getNim());
        values.put("nama", m.getNama());
        values.put("prodi", m.getProdi());

        db.update("mahasiswa", values, "nim = ?", new String[]{oldNim});
    }

    public void DeleteMahasiswa(String nim) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete("mahasiswa", "nim = ?", new String[]{nim});
    }
}
