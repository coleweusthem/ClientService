package com.techme.mainscreenactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendActivity extends AppCompatActivity {

    private EditText editTo, editSubject, editMessage;
    private Button btnCancel, btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        editTo = findViewById(R.id.edit_to);
        editMessage = findViewById(R.id.edit_message);
        editSubject = findViewById(R.id.edit_subject);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSend = findViewById(R.id.btn_send);
        buttons();
    }

    private void buttons(){
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    private void sendMail(){
        String to = editTo.getText().toString();
        String subject = editSubject.getText().toString();
        String message = editMessage.getText().toString();
        SendingMail sender = new SendingMail(this,to,subject,message);
        sender.execute();

    }
}
