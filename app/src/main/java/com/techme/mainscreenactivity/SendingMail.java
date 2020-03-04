package com.techme.mainscreenactivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendingMail extends AsyncTask<Void,Void,Void> {


    private Context context;
    private Session emailSession;

    private String email;
    private String subject;
    private String message;

    private ProgressDialog progressDialog;


    public SendingMail(Context context, String email, String subject, String message) {
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Show progress dialog while sending email
        progressDialog = ProgressDialog.show(context, "Sending message", "Please wait...", false, false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismiss progress dialog when message successfully send
        progressDialog.dismiss();

        //Show success toast
        Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for outlook
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.auth", "true");

        //Creating a new session
        emailSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(UserDetails.EMAIL, UserDetails.PASSWORD);
                    }
                });

        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(emailSession);

            //Setting sender address
            mm.setFrom(new InternetAddress(UserDetails.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject(subject);
            //Adding message
            mm.setText(message);
            //Sending email
            Transport.send(mm);


        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
