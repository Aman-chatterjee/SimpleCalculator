package com.dannproductions.simplecalculator;

import android.os.AsyncTask;
import android.renderscript.Script;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.evgenii.jsevaluator.JsEvaluator;
import com.evgenii.jsevaluator.interfaces.JsCallback;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {


    //Constants
    private static final String ADD = "+";
    private static final String MINUS = "-";
    private static final String MULTIPLY = "×";
    private static final String DIVIDE = "÷";
    private static final String EQUALS = "=";
    private static final String CLEAR = "ac";
    private static final String DEL = "del";

    //vars
    private TextView textScreen;
    private TextView resultScreen;
    private String expression="";
    JsEvaluator jsEvaluator;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textScreen = findViewById(R.id.screen);
        resultScreen = findViewById(R.id.result_screen);
        jsEvaluator = new JsEvaluator(this);
    }



    public void number_clicked(View view){
        textScreen.setText(String.valueOf(textScreen.getText()+view.getTag().toString()));
    }


    public void operator_clicked(View view){
        if(textScreen.getText().length()>0&&Character.isDigit(textScreen.getText().charAt(textScreen.getText().length()-1))) {
            textScreen.setText(String.valueOf(textScreen.getText() + view.getTag().toString()));
        }
    }




    public void symbol_clicked(View view){

        if(view.getTag().toString().equals(EQUALS)) {

            expression = textScreen.getText().toString();
            if(expression.contains(DIVIDE)){ expression = expression.replace(DIVIDE,"/"); }
            if(expression.contains(MULTIPLY)){ expression = expression.replace(MULTIPLY,"*"); }

            jsEvaluator.evaluate(expression, new JsCallback() {
                @Override
                public void onResult(String s) {
                    resultScreen.setText(String.valueOf("= "+s));
                }
                @Override
                public void onError(String s) {
                    resultScreen.setText(String.valueOf("Invalid expression"));
                }
            });
        }

        if(view.getTag().toString().equals(DEL)){
            if(textScreen.getText().length()>0) {
                textScreen.setText(textScreen.getText().toString().substring(0, textScreen.getText().length() - 1));
            }
        }

        if(view.getTag().toString().equals(CLEAR)) {
            expression = "";
            textScreen.setText("");
            resultScreen.setText("");
        }
    }






















//    public void numExtractor(String operator,String expression){
//
//        if(isValid(expression)){
//            expression = "/"+expression+"/";
//            int index = expression.indexOf(operator);
//
//            //Getting the first number
//            for (int i=index-1;i>=0;i--) {
//                if(!Character.isDigit(expression.charAt(i))||expression.charAt(i)=='/'){
//                    num_1 = Double.parseDouble(expression.substring(i+1,index));
//                    sIndex = i+1;
//                    break;
//                }
//            }
//            //Getting the second number
//            for (int i=index+1;i<=expression.length()-1;i++) {
//                if(!Character.isDigit(expression.charAt(i))||expression.charAt(i)=='/'){
//                    num_2 = Double.parseDouble(expression.substring(index+1,i));
//                    eIndex = i;
//                    break;
//                }
//            }
//           this.expression = expression.replace(expression.substring(sIndex,eIndex)," ");
//        }
//    }
//
//
//    public void calculate(){
//
//        DecimalFormat dFormat = new DecimalFormat();
//       while (expression.contains(DIVIDE)||expression.contains(MULTIPLY)||expression.contains(ADD)||expression.contains(MINUS)) {
//            if (expression.contains(DIVIDE)) {
//                numExtractor(DIVIDE,expression);
//                result = num_1 / num_2;
//                expression = expression.replace(dFormat.format(num_1) + DIVIDE + dFormat.format(num_2), String.valueOf(dFormat.format(result)));
//            } else if (expression.contains(MULTIPLY)) {
//                numExtractor(MULTIPLY, expression);
//                result = num_1 * num_2;
//                expression = expression.replace(dFormat.format(num_1) + MULTIPLY + dFormat.format(num_2), String.valueOf(dFormat.format(result)));
//            } else if (expression.contains(ADD)) {
//                numExtractor(ADD, expression);
//                result = num_1 + num_2;
//                expression = expression.replace(dFormat.format(num_1) + ADD + dFormat.format(num_2), String.valueOf(dFormat.format(result)));
//            } else if (expression.contains(MINUS)) {
//                numExtractor(MINUS, expression);
//                result = num_1 - num_2;
//                expression = expression.replace(dFormat.format(num_1) + MINUS + dFormat.format(num_2), String.valueOf(dFormat.format(result)));
//            }
//            resultScreen.setText(String.valueOf(result));
//       }
//
//    }
//
//
//
//    public boolean isValid(String expression){
//        return Character.isDigit(expression.charAt(expression.length()-1));
//    }


}
