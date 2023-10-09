package step.learning.android_spu121;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CalcActivity extends AppCompatActivity {
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0;
    TextView calcTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        btn1=findViewById(R.id.btn_1);
        btn2=findViewById(R.id.btn_2);
        btn3=findViewById(R.id.btn_3);
        btn4=findViewById(R.id.btn_4);
        btn5=findViewById(R.id.btn_5);
        btn6=findViewById(R.id.btn_6);
        btn7=findViewById(R.id.btn_7);
        btn8=findViewById(R.id.btn_8);
        btn9=findViewById(R.id.btn_9);
        btn0=findViewById(R.id.btn_0);
        calcTvResult=findViewById(R.id.calc_tv_result);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText=calcTvResult.getText().toString();
                calcTvResult.setText(currentText + "1");
            }

        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText=calcTvResult.getText().toString();
                calcTvResult.setText(currentText + "2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText=calcTvResult.getText().toString();
                calcTvResult.setText(currentText + "3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText=calcTvResult.getText().toString();
                calcTvResult.setText(currentText + "4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText=calcTvResult.getText().toString();
                calcTvResult.setText(currentText + "5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText=calcTvResult.getText().toString();
                calcTvResult.setText(currentText + "6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText=calcTvResult.getText().toString();
                calcTvResult.setText(currentText + "7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText=calcTvResult.getText().toString();
                calcTvResult.setText(currentText + "8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText=calcTvResult.getText().toString();
                calcTvResult.setText(currentText + "9");
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText=calcTvResult.getText().toString();
                calcTvResult.setText(currentText + "0");
            }
        });
    }
}