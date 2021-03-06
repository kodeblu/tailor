package com.bencorp.thetailor;

import android.annotation.TargetApi;
import android.graphics.Color;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Test extends AppCompatActivity {
    Button btn;
    LinearLayout job;
    SqliteHandler myDB;

    int[] viewIds = new int[]{R.id.customer_name,R.id.job_price,R.id.customer_measurement};
    // UI references.
    EditText name;
    EditText price;
    EditText measuremnts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    insertData(name.getText().toString().trim(),
                            Integer.parseInt(price.getText().toString().trim()),
                            measuremnts.getText().toString().trim(),view);
                }
                //Toast.makeText(getApplicationContext(),"btn clicked",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void init(){
        btn = (Button) findViewById(R.id.save_btn);
        name = (EditText)findViewById(R.id.customer_name);
        price = (EditText)findViewById(R.id.job_price);
        measuremnts = (EditText)findViewById(R.id.customer_measurement);
        myDB = new SqliteHandler(getApplicationContext());
        //job = (LinearLayout) findViewById(R.id.customer_form);
    }
    public Boolean validate(){
        View focusView = null;
        Boolean allow = true;
        for(int i = 0; i < viewIds.length;i++){
            EditText formInput = (EditText)findViewById(viewIds[i]);
            if(formInput.getText().toString().trim().isEmpty()){
                formInput.setError(getString(R.string.error_field_required));
                focusView = formInput;
                allow = false;
                break;
            }

        }
        return allow;
    }
    
    public void insertData(String name,Integer price,String measurement,View view){
        String date = new SimpleDateFormat("dd MMMM, yyyy | EEEE", Locale.getDefault()).format(new Date());

        Boolean inserting = myDB.addJob(name,price,measurement,date);
        if(inserting){
            Snackbar.make(view,"Details have been saved successfully",Snackbar.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(),"successful creation",Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },2000);

        }else{
            //Snackbar snackbar = Snackbar.make(view,"S",Snackbar.LENGTH_LONG);
            Snackbar snackbar = Snackbar.make(view,"Something went wrong, please resart app and try again",Snackbar.LENGTH_LONG);
            View sub = snackbar.getView();
            TextView textView = (TextView) sub.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.RED);
            snackbar.show();
        }
    }


}
