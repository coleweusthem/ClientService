package com.techme.mainscreenactivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sun.mail.pop3.POP3Store;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class ReceivingMail extends AsyncTask<Void,Void,Message[]> {

    private String email,password;
    private Context context;
    private Message[] messages;
    private ProgressDialog progressDialog;
    public static final String TAG = "MAIL";
    public  ReceivingMail(Context context,String email, String password) {
        this.email = email;
        this.password = password;
        this.context = context;
    }

    public Message[] getMessages(){
        return messages;
    }

    @Override
    protected Message[] doInBackground(Void... voids) {
        try {
            // get the session object
            Properties props = new Properties();
            props.put("mail.pop3.host", "pop-mail.outlook.com");
            props.put("mail.pop3.port", 995);
            props.put("mail.pop3.auth",true);

            Session emailSession = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        //Authenticating the password
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(UserDetails.EMAIL, UserDetails.PASSWORD);
                        }
                    });


            // create the POP3 store object and connect with the pop server
            POP3Store emailStore = (POP3Store) emailSession.getStore("pop3");
            emailStore.connect(email, password);
            if(!emailStore.isConnected()){
                Log.d(TAG,"could not connect");
            }

            // create the folder object and open it
            Folder emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            messages = emailFolder.getMessages();

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                Log.d(TAG,"---------------------------------");
                Log.d(TAG,"Email Number " + (i + 1));
                Log.d(TAG,"Subject: " + message.getSubject());
                Log.d(TAG,"From: " + message.getFrom()[0]);
                Log.d(TAG,"Text: " + message.getContent().toString());
            }

            // close the store and folder objects
            emailFolder.close(false);
            emailStore.close();

        } catch (NoSuchProviderException e) {e.printStackTrace();}
        catch (MessagingException e) {e.printStackTrace();} catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Show progress dialog while sending email
        progressDialog = ProgressDialog.show(context, "Getting Inbox", "Please wait...", false, false);

    }

    @Override
    protected void onPostExecute(Message[] messages) {
        super.onPostExecute(messages);
        //Dismiss progress dialog when message successfully send
        progressDialog.dismiss();
        this.messages = messages;
        //Show success toast
        Toast.makeText(context, "Inbox List", Toast.LENGTH_SHORT).show();
    }
}
