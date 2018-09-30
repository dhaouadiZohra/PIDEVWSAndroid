package com.example.zahra.pidevwsandroid;

/**
 * Created by Dhaouadi Zohra on 14/04/2018.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendMailActivity extends AppCompatActivity {

    EditText et_email,et_subject,et_text;
    Button btn_send,btnqr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        et_email = (EditText) findViewById(R.id.etDestionataire);
        et_subject = (EditText) findViewById(R.id.etSubject);
        et_text = (EditText) findViewById(R.id.ettext);
        btn_send = (Button) findViewById(R.id.btnSend);
        btnqr = (Button) findViewById(R.id.btnqr);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = et_email.getText().toString();
                String subject = et_subject.getText().toString();
                String message = et_text.getText().toString();

                Intent inetnt = new Intent(Intent.ACTION_SEND);
                inetnt.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
                inetnt.putExtra(Intent.EXTRA_SUBJECT,subject);
                inetnt.putExtra(Intent.EXTRA_TEXT,message);

                inetnt.setType("message/rfc822");

                startActivity(Intent.createChooser(inetnt,"select email app"));



            }
        });
        btnqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SendMailActivity.this,Create.class));


            }
        });
    }
}
