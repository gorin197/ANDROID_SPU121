package step.learning.android_spu121;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;


public class CalcActivity1 extends AppCompatActivity {
    Evaluator evaluator = new Evaluator();

    private TextView tvExpression ;
    private TextView tvResult ;
    private String resultValue = ""; // переменная для хранения значений в tvResult
    private double evalResult = 0;
    private  String operator="";
    public double evaluateExpression(String expression) throws EvaluationException {
        Evaluator evaluator = new Evaluator();
        return Double.parseDouble(evaluator.evaluate(expression));
    }


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_calc1 );

        tvExpression = findViewById( R.id.calc1_tv_expression ) ;
        tvResult = findViewById( R.id.calc1_tv_result ) ;
        clearClick( null ) ;

        findViewById( R.id.calc_btn_c ).setOnClickListener( this::clearClick ) ;
        // Пройти циклом по ідентифікаторах calc_btn_[i], всім вказати один обробник
        for( int i = 0; i < 10; i++ ) {
            findViewById(
                    getResources()  // R.
                            .getIdentifier(    // .id (R.id.calc_btn_[i])
                                    "calc_btn_" + i,
                                    "id",
                                    getPackageName()
                            )
            ).setOnClickListener( this::digitClick );
        }
        Button btnPlus = findViewById(R.id.calc_btn_plus);
        btnPlus.setOnClickListener(this::plusClick);
        Button btnEqual=findViewById(R.id.calc_btn_equal);
        btnEqual.setOnClickListener(this::equalsClick);
        Button btnMinus=findViewById(R.id.calc_btn_minus);
        btnMinus.setOnClickListener(this::minusClick);
        Button btnMultiplication=findViewById(R.id.calc_btn_multiplication);
        btnMultiplication.setOnClickListener(this::multiplicateClick);
        Button btnMDevide=findViewById(R.id.calc_btn_divide);
        btnMDevide.setOnClickListener(this::devideClick);

    }

    private void equalsClick(View view) {       //кнопка[=]
        String expression = tvExpression.getText().toString();
        String result = tvResult.getText().toString();
        if (!expression.isEmpty() && !result.isEmpty()) {
            String fullExpression = expression + operator + result;
            try {
                double evalResult = evaluateExpression(fullExpression);
                String formattedResult = formatResult(evalResult);
                tvResult.setText(formattedResult);
            } catch (Exception e) {
                tvResult.setText("Ошибка");
            }
            tvExpression.setText(""); // Очищаем tvExpression
            resultValue = "";

        }
    }
    private void digitClick( View view ) {  // [для цифрових кнопок]
       if(resultValue.isEmpty()){
        tvResult.setText("");
       }
        String digit = tvResult.getText().toString();
        //String digit = ((Button) view).getText().toStri1ng();
        if( digit.equals( getString( R.string.calc_btn_0 ) ) ) {
            digit = ( (Button) view ).getText().toString() ;
        }
        else {
            digit += ( (Button) view ).getText() ;
        }
        resultValue=digit;
        tvResult.setText( resultValue ) ;
    }
    private void clearClick( View view ) {   // [ C ]
        tvExpression.setText( "" ) ;
        tvResult.setText( R.string.calc_btn_0 ) ;
    }

    private void plusClick(View view) {  // кнопка[+]
        String result = tvResult.getText().toString();
        String expression = tvExpression.getText().toString();
        operator="+";
        if (!result.equals(getString(R.string.calc_btn_0))) {
            if (!expression.isEmpty()) {
                //expression += " + " + result;
                expression += operator + result;
            } else {
                expression = result;
            }
            tvExpression.setText(expression);
            resultValue = "";
            try {
                double evalResult = evaluateExpression(expression);
                String formattedResult = formatResult(evalResult);
                tvResult.setText(formattedResult);
            } catch (Exception e) {
                tvResult.setText("Ошибка");
            }
        }
    }

    private void minusClick(View view) {
        String result = tvResult.getText().toString();
        String expression = tvExpression.getText().toString();
        operator="-";
        if (!result.equals(getString(R.string.calc_btn_0))) {
            if (!expression.isEmpty()) {
                expression += operator + result;
            } else {
                expression = result;
            }
            tvExpression.setText(expression);
            resultValue = "";
            try {
                double evalResult = evaluateExpression(expression);
                String formattedResult = formatResult(evalResult);
                tvResult.setText(formattedResult);
            } catch (Exception e) {
                tvResult.setText("Ошибка");
            }
        }
    }

    private void multiplicateClick(View view) {  // кнопка[+]
        String result = tvResult.getText().toString();
        String expression = tvExpression.getText().toString();
        operator="*";
        if (!result.equals(getString(R.string.calc_btn_0))) {
            if (!expression.isEmpty()) {
                expression += operator + result;
            } else {
                expression = result;
            }
            tvExpression.setText(expression);
            resultValue = "";
            try {
                double evalResult = evaluateExpression(expression);
                String formattedResult = formatResult(evalResult);
                tvResult.setText(formattedResult);
            } catch (Exception e) {
                tvResult.setText("Ошибка");
            }
        }
    }

    private void devideClick(View view) {  // кнопка[+]
        String result = tvResult.getText().toString();
        String expression = tvExpression.getText().toString();
        operator="/";
        if (!result.equals(getString(R.string.calc_btn_0))) {
            if (!expression.isEmpty()) {
                expression += operator + result;
            } else {
                expression = result;
            }
            tvExpression.setText(expression);
            resultValue = "";
            try {
                double evalResult = evaluateExpression(expression);
                String formattedResult = formatResult(evalResult);
                tvResult.setText(formattedResult);
            } catch (Exception e) {
                tvResult.setText("Ошибка");
            }
        }
    }

//    private String getOperatorFromButton(View view) {
//        String operator = "";
//        int viewId = view.getId();
//
//        if (viewId == R.id.calc_btn_plus) {
//            operator = "+";
//        } else if (viewId == R.id.calc_btn_minus) {
//            operator = "-";
//        } else if (viewId == R.id.calc_btn_multiplication) {
//            operator = "*";
//        } else if (viewId == R.id.calc_btn_divide) {
//            operator = "/";
//        }
//       //Сюда добавляем еще "+", "-"
//
//        return operator;
//    }


    private String formatResult(double result) {
        if (result == (int) result) {
            return String.valueOf((int) result);
        } else {
            return String.valueOf(result);
        }
    }






    /*
    Зміна конфігурації
    "+" автоматично визначається потрібний ресурс
    "-" активність перестворюється і втрачаються напрацьовані дані
    Для того щоб мати можливість збереження/відновлення цих даних
    задаються наступні обробники:
     */

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("result",tvResult.getText());
        outState.putCharSequence("expression",tvExpression.getText());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tvResult.setText(savedInstanceState.getCharSequence("result"));
        tvExpression.setText(savedInstanceState.getCharSequence("expression"));
    }
}