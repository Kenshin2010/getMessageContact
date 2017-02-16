package com.example.manroid.system;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReadSms extends AppCompatActivity {

    ListView lvTinNhan;
    ArrayList<String> dsTinNhan;
    ArrayAdapter<String> adapterTinNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_sms);
        addControls();
        addEvents();
        Toast.makeText(getApplicationContext(),"Read sms",Toast.LENGTH_SHORT).show();
        docToanBoTinNhan();

    }
    private void docToanBoTinNhan() {
        Uri uri=Uri.parse("content://sms/inbox");
        Cursor cursor=getContentResolver().query(uri,null,null,null,null);
        dsTinNhan.clear();
        while (cursor.moveToNext())
        {
            int indexPhoneNumber = cursor.getColumnIndex("address");
            int indexTimeStamp = cursor.getColumnIndex("date");
            int indexBody = cursor.getColumnIndex("body");

            String phonenumber=cursor.getString( indexPhoneNumber );
            String timeStamp=cursor.getString(indexTimeStamp);
            String body= cursor.getString( indexBody );

            dsTinNhan.add(phonenumber+"\n"+timeStamp+"\n"+body);

        }
        cursor.close();
        adapterTinNhan.notifyDataSetChanged();
    }

    private void addEvents() {

    }

    private void addControls() {
        lvTinNhan= (ListView) findViewById(R.id.lvSms);
        dsTinNhan=new ArrayList<>();
        adapterTinNhan=new ArrayAdapter<String>
                (getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        dsTinNhan);
        lvTinNhan.setAdapter(adapterTinNhan);
    }
}
