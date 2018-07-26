package com.example.weihong.practice;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView web_content;
    private EditText input;
    private Button get;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.d("UIHandler","before setting text"+msg.obj);
                web_content.setText(String.valueOf(msg.obj));
                web_content.setMovementMethod(new ScrollingMovementMethod());
            }
        };

    }

    private void initView() {
        get = findViewById(R.id.button_get);
        input = findViewById(R.id.edit_website);
        web_content = findViewById(R.id.html_text);
        get.setOnClickListener(this);
        web_content.setOnTouchListener(new TextViewTouchListener());
//        this.getWindow().getDecorView().setOnTouchListener(this);

//        View.OnFocusChangeListener ofcListener = new MyFocusChangeListener();
//        input.setOnFocusChangeListener(ofcListener);
    }

    public void onClick(View v) {

        if (v.getId() == R.id.button_get) {
            final String site = input.getText().toString();

            new Thread(new Runnable() {
                public void run() {
                    // a potentially  time consuming task

                    Log.d("onClick", "========Before getHtml========");
                    new HtmlService().getHtml(site, mainHandler);
                    Log.d("onClick", "========After getHtml========");
                }
            }).start();


        } else {
            web_content.setText("Do you click the button?");
        }

    }
    public class TextViewTouchListener implements View.OnTouchListener{
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Log.d("onTouch", "========Before if========");
            InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return false;
        }
    }

//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        Log.d("onTouch", "========Before if========");
//        if(view.getId() != R.id.edit_website) {
//            Log.d("onTouch", "========Within if========");
//            InputMethodManager imm =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//
//        }
//        return false;
//    }
}
