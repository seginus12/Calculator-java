package com.example.calcilator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Context;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText textField;
    String lastOperation = "=";
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bAC, bC, bLBracket, bRBracket, bPlus, bMinus, bDiv, bMult, bEqual, bDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textField = findViewById(R.id.textField);
        assignListener(b1, R.id.b1);
        assignListener(b2, R.id.b2);
        assignListener(b3, R.id.b3);
        assignListener(b4, R.id.b4);
        assignListener(b5, R.id.b5);
        assignListener(b6, R.id.b6);
        assignListener(b7, R.id.b7);
        assignListener(b8, R.id.b8);
        assignListener(b9, R.id.b9);
        assignListener(b0, R.id.b0);
        assignListener(bAC, R.id.bAC);
        assignListener(bC, R.id.bC);
        assignListener(bPlus, R.id.bPlus);
        assignListener(bMinus, R.id.bMinus);
        assignListener(bMult, R.id.bMult);
        assignListener(bDiv, R.id.bDiv);
        assignListener(bEqual, R.id.bEqual);
        assignListener(bRBracket, R.id.bRBracket);
        assignListener(bLBracket, R.id.bLBracket);
        assignListener(bDot, R.id.bDot);
    }

    void assignListener(Button button, int id){
        button = findViewById(id);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        if (button.getId() == R.id.bAC) {
            textField.setText("");
            return;
        }
        if (button.getId() == R.id.bC) {
            if (!textField.getText().toString().equals(""))
                textField.setText(textField.getText().toString().substring(0, textField.length() - 1));
            return;
        }
        if (textField.getText().toString().equals("") && operationIsPressed(button) || operationIsPressed(button) && "-+÷×".contains(getLastSymbol(textField))) {
            return;
        }
        if (button.getId() == R.id.bEqual){
            String result = getResult(textField.getText().toString());
            if (result != "Err")
                textField.setText(result);
            return;
        }
        textField.append(button.getText().toString());
    }
    String getResult(String expression){
        try {
            expression = expression.replace('×', '*');
            expression = expression.replace('÷', '/');
            Context context  = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String result =  context.evaluateString(scriptable,expression,"Javascript",1,null).toString();
            if (result.endsWith(".0")){
                result = result.replace(".0","");
            }
            return result;
        } catch (Exception e){
            return "Err";
        }
    }

    boolean operationIsPressed(Button button){
        int id = button.getId();
        if (id == R.id.bMult || id == R.id.bPlus || id == R.id.bMinus || id == R.id.bDiv)
            return true;
        return false;
    }

    String getLastSymbol(EditText textField){
        return textField.getText().toString().substring(textField.length() - 1);
    }
}