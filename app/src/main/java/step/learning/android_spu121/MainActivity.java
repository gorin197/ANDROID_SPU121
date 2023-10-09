package step.learning.android_spu121;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Подключение разметки - работа с UI только после этой команды
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.main_tv_hello);
        textView.setText(R.string.maim_tv_hello_text);

        Button button= findViewById(R.id.main_button_hello);
        button.setOnClickListener(this::helloClick);
    }
    private void helloClick(View view){
    //textView.setText(textView.getText()+"Oh!");
        Intent testIntent= new Intent(this. getApplicationContext(), TestActivity.class);
        startActivity(testIntent);
    }
    public void openCalcActivity(View view) {
        Intent intent = new Intent(this, CalcActivity.class);
        startActivity(intent);
    }

    public void openCalcActivity1(View view) {
        Intent intent = new Intent(this, CalcActivity1.class);
        startActivity(intent);
    }


}