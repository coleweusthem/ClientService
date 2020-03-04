package com.techme.mainscreenactivity;

public class MailProps {
    private String senderName;
    private String subject;
    private String text;

    public MailProps(String senderName, String subject, String text) {
        this.senderName = senderName;
        this.subject = subject;
        this.text = text;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSubject() {
        return subject;
    }

    public String getText(){
        return text;
    }


}
