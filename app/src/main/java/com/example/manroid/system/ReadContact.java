package com.example.manroid.system;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.manroid.model.Contact;

import java.util.ArrayList;

public class ReadContact extends AppCompatActivity {

    ListView lvDanhBa;
    ArrayList<Contact> dsDanhBa;
    ArrayAdapter<Contact> adapterDanhBa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_contact);

        widget();
        showAllContactFromDevice();
        Toast.makeText(getApplicationContext(),"Read Contact",Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), ReadSms.class);
                        startActivity(intent);
                    }
                }, 5000);
            }
        });
        //finish();
    }

    private void showAllContactFromDevice() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        dsDanhBa.clear();
        while (cursor.moveToNext()) {
            String tenCotName = ContactsContract.Contacts.DISPLAY_NAME;
            String tenCotPhone = ContactsContract.CommonDataKinds.Phone.NUMBER;
            int vtTenCotName = cursor.getColumnIndex(tenCotName);
            int vtTenCotPhone = cursor.getColumnIndex(tenCotPhone);
            String name = cursor.getString(vtTenCotName);
            String phone = cursor.getString(vtTenCotPhone);
            Contact contact = new Contact(name, phone);
            dsDanhBa.add(contact);
        }
        adapterDanhBa.notifyDataSetChanged();
    }

    private void widget() {
        lvDanhBa = (ListView) findViewById(R.id.lvdanhba);
        dsDanhBa = new ArrayList<>();
        adapterDanhBa = new ArrayAdapter<Contact>(
                ReadContact.this,
                android.R.layout.simple_list_item_1,
                dsDanhBa);
        lvDanhBa.setAdapter(adapterDanhBa);
    }
}
