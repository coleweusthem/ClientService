package com.techme.mainscreenactivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;


public class InboxFragment extends Fragment {

    private Message[] messages = new Message[100000];
    private RecyclerView recyclerView;
    private MainRecycleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        recyclerView = view.findViewById(R.id.inbox_recycle_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        //displayMails();
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new MainRecycleAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void displayMails() {
        ReceivingMail receivingMail = new ReceivingMail(getContext(),UserDetails.EMAIL, UserDetails.PASSWORD);
        receivingMail.execute();
        messages = receivingMail.getMessages();
        if(messages != null) {
            System.out.println("in here, size of: " + messages.length );
            for (Message message : messages) {
                try {
                    String senderName = message.getFrom()[0].toString();
                    String subject = message.getSubject();
                    String text = message.getContent().toString();
                    MailProps mail = new MailProps(senderName, subject, text);
                    adapter.setMail(mail);
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
