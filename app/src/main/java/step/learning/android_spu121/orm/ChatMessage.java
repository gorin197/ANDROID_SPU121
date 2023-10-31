package step.learning.android_spu121.orm;

import static java.util.Locale.UK;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
    private String id;
    private String author;
    private String text;
    private String moment;
    private static final SimpleDateFormat momentFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss",UK );
    public Date getMomentAsDate(){
        try {

                return momentFormat.parse(this.getMoment());

            }
            catch (ParseException ex){
                Log.d("ChatMessage::getMomentAsDate",ex.getMessage() + ""+ this.getMoment());
        }
        return getMomentAsDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAythor() {
        return author;
    }

    public void setAythor(String aythor) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMoment() {
        return moment;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }
}


/*
 {
      "id": "2143",
      "author": "Max",
      "text": "324",
      "moment": "2023-10-27 10:46:46"
    },
 */