package step.learning.android_spu121;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import step.learning.android_spu121.orm.ChatMessage;
import step.learning.android_spu121.orm.ChatResponse;

public class ChatActivity extends AppCompatActivity {
    private final  static  String chatHost="https://chat.momentfor.fun/";
    private  final byte[] buffer= new byte[8192];
    private final Gson gson=new Gson();
    private List<ChatMessage> chatMessages = new ArrayList();
    private EditText etNic;
    private EditText etMessage;
    private ScrollView svContainer;
    private LinearLayout llContainer;

    private TextView tvtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tvtitle=findViewById(R.id.chat_tv_title);
        etNic=findViewById(R.id.chat_et_nik);
        etMessage=findViewById(R.id.chat_et_message);
        svContainer = findViewById(R.id.chat_sv_container);
        llContainer = findViewById(R.id.chat_ll_container);
        loadChatMessages();
        new Thread((this::loadChatMessages)).start();
    }
    private void  loadChatMessages(){

        try {
            URL chatUrl=new URL(chatHost);
            InputStream chatStream= chatUrl.openStream();

            ByteArrayOutputStream builder = new ByteArrayOutputStream();
            int bytesRead;
            while ((bytesRead=chatStream.read(buffer))>0){
                builder.write(buffer,0,bytesRead);
            }
            String data = builder.toString(StandardCharsets.UTF_8.name());
            ChatResponse chatResponse = gson.fromJson(data, ChatResponse.class);
            boolean wasNewMessage=false;
            for(ChatMessage chatMessage:chatResponse.getData()){
                if(chatMessages.stream().noneMatch(m ->m.getId().equals(chatMessage.getId()))){
                    chatMessages.add(chatMessage);
                    wasNewMessage =true;
                }
            }
            if(wasNewMessage) {
                runOnUiThread(this::showChatMessages);
            }
            builder.close();
            chatStream.close();
        }
        catch (MalformedURLException ex){
            Log.d("loadChatMessages",ex.getMessage() + "");
        }
        catch (IOException ex){
            Log.d("loadChatMessages","IOExeption" + ex.getMessage() + "");
        }
        catch (android.os.NetworkOnMainThreadException ex){
            Log.d("loadChatMessages","NetworkOnMainThreadException" + ex.getMessage() + "");

        }
        catch (java.lang.SecurityException ex){
            Log.d("loadChatMessages","SecurityException" + ex.getMessage() + "");
        }

    }

    private  void showChatMessages(){
        String str="";
        for(ChatMessage chatMessage:this.chatMessages){
        llContainer.addView((createChatMessageView((chatMessage))));
//            str+=chatMessage.getAythor() + ": " + chatMessage.getText() + "\n";
//            tvtitle.setText(str);
        }
    }
    private View createChatMessageView(ChatMessage chatMessage){
        LinearLayout.LayoutParams layoutParams=new  LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(5,5,5,5);
        layoutParams.gravity=Gravity.END;
        LinearLayout messagelayout=new LinearLayout(this);
        messagelayout.setLayoutParams(layoutParams);
        messagelayout.setGravity(Gravity.END);
        messagelayout.setPadding(30,20,30,20);
        messagelayout.setOrientation(LinearLayout.VERTICAL);

        messagelayout.setBackground(AppCompatResources.getDrawable(
                ChatActivity.this,R.drawable.chat_message_other));
        //messagelayout.setOrientation(LinearLayout.VERTICAL);

        messagelayout.setBackground(AppCompatResources.getDrawable(
                ChatActivity.this,R.drawable.chat_message_mine));


        TextView textView= new TextView((this));
        textView.setText((chatMessage.getAythor() ));
        messagelayout.addView(textView);
            textView= new TextView(this);
            textView.setText(chatMessage.getText());
            messagelayout.addView(textView);


        return  messagelayout;
    }
}