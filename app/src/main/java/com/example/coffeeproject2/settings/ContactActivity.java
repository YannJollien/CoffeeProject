package com.example.coffeeproject2.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.coffeeproject2.R;

public class Contact extends AppCompatActivity {

    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        send = findViewById(R.id.btnOK);

        String to = ((EditText)findViewById(R.id.txtTo)).getText().toString();
        String sub = ((EditText)findViewById(R.id.txtSubject)).getText().toString();
        String mess = ((EditText)findViewById(R.id.txtMessage)).getText().toString();
        Intent mail = new Intent(Intent.ACTION_SEND);
        mail.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
        mail.putExtra(Intent.EXTRA_SUBJECT, sub);
        mail.putExtra(Intent.EXTRA_TEXT, mess);
        mail.setType("message/rfc822");
        startActivity(Intent.createChooser(mail, "Send email via:"));
    }
}
