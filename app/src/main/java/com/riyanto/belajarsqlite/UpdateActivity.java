package com.riyanto.belajarsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.riyanto.belajarsqlite.helpers.MahasiswaRepository;
import com.riyanto.belajarsqlite.models.Mahasiswa;

public class UpdateActivity extends AppCompatActivity {
    EditText etNim, etNama;
    Spinner spProdi;
    Button btnUpdate, btndelete;
    MahasiswaRepository mahasiswaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        etNim = findViewById(R.id.et_nim);
        etNama = findViewById(R.id.et_nama);
        spProdi = findViewById(R.id.sp_prodi);
        btnUpdate = findViewById(R.id.btn_update);
        btndelete = findViewById(R.id.btn_delete);

        mahasiswaRepository = new MahasiswaRepository(this);

        String[] arrProdi = {"Manajemen Informatika", "Teknik Listrik"};
        spProdi.setAdapter(new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                arrProdi
        ));

        // Ambil data dari Intent
        Intent intent = getIntent();
        String nim = intent.getStringExtra("nim");
        String nama = intent.getStringExtra("nama");
        String prodi = intent.getStringExtra("prodi");

        // Set data ke EditText dan Spinner
        etNim.setText(nim);
        etNama.setText(nama);
        if (prodi != null) {
            int spinnerPosition = ((ArrayAdapter<String>) spProdi.getAdapter()).getPosition(prodi);
            spProdi.setSelection(spinnerPosition);
        }

        // Listener untuk tombol Update
        btnUpdate.setOnClickListener(v -> {
            String updatedNim = etNim.getText().toString();
            String updatedNama = etNama.getText().toString();
            String updatedProdi = spProdi.getSelectedItem().toString();

            // Buat objek Mahasiswa dengan data yang diperbarui
            Mahasiswa updatedMahasiswa = new Mahasiswa(updatedNim, updatedNama, updatedProdi);

            // Update mahasiswa di database
            mahasiswaRepository.updateMahasiswa(nim, updatedMahasiswa); // Ganti dengan nim lama

            Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
            finish(); // Kembali ke MainActivity setelah update
        });

        // Listener untuk tombol Hapus
        btndelete.setOnClickListener(v -> {
            mahasiswaRepository.DeleteMahasiswa(nim); // Hapus berdasarkan NIM

            Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
            finish(); // Kembali ke MainActivity setelah hapus
        });
    }
}
