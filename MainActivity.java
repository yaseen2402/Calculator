package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTv,solutionTv;
    MaterialButton buttonC,buttonAC,buttonBracketOpen,buttonBracketClosed,buttonDivide,buttonAdd,buttonSubtract,buttonMultiply;
    MaterialButton button0,button1,button2,button3,button4,button5,button6,button7,button8,button9,buttonEquals,buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv =findViewById(R.id.solution_tv);

        assign(buttonC ,R.id.button_c);
        assign(buttonBracketOpen ,R.id.button_open_bracket);
        assign(buttonBracketClosed ,R.id.button_closed_bracket);
        assign(buttonEquals ,R.id.button_equals);
        assign(buttonDot ,R.id.button_dot);
        assign(buttonAC ,R.id.button_all_clear);
        assign(button0 ,R.id.button_zero);
        assign(button1 ,R.id.button_one);
        assign(button2 ,R.id.button_two);
        assign(button3 ,R.id.button_three);
        assign(button4 ,R.id.button_four);
        assign(button5 ,R.id.button_five);
        assign(button6 ,R.id.button_six);
        assign(button7 ,R.id.button_seven);
        assign(button8 ,R.id.button_eight);
        assign(button9 ,R.id.button_nine);
        assign(buttonAdd ,R.id.button_add);
        assign(buttonSubtract ,R.id.button_subtract);
        assign(buttonMultiply ,R.id.button_multiply);
        assign(buttonDivide ,R.id.button_divide);

    }

    void assign(MaterialButton btn,int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button =(MaterialButton)view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();
        if (buttonText.equals("AC")){
            solutionTv.setText("");
            resultTv.setText("");
            return;
        }
        if(buttonText.equals("=")){
            solutionTv.setText(resultTv.getText());
        }
        if(buttonText.equals("C")){
           if(dataToCalculate.length()>0){
               StringBuilder sb = new StringBuilder(solutionTv.getText());
               sb.deleteCharAt(solutionTv.getText().length()-1);
               dataToCalculate = sb.toString();
               solutionTv.setText(dataToCalculate);
               resultTv.setText(dataToCalculate);
           }
           return;
        }
        else {
            dataToCalculate =dataToCalculate+buttonText;
        }
        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if(!finalResult.equals("err")){
            resultTv.setText(finalResult);
        }

    }
    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult =finalResult.replace(".0","");
            }

            return finalResult;
        }catch(Exception e){
            return "err";
        }
    }

}