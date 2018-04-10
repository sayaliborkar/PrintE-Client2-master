package com.example.lkh.printec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
public class Jobs extends AppCompatActivity{
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        Intent intent = getIntent();
        /*Bundle bundle = intent.getExtras();
        save_job_info x = (save_job_info) bundle.getSerializable("obj");*/
        save_job_info x = (save_job_info) intent.getSerializableExtra("obj");
        String jobId = intent.getStringExtra("jobId");

        TextView textView8 = (TextView) findViewById(R.id.textView8);
        textView8.setText(jobId);

        TextView textView11 = (TextView) findViewById(R.id.textView11);
        textView11.setText(x.user_id);
        TextView textView13 = (TextView) findViewById(R.id.textView13);
        textView13.setText(x.copies);

        RadioButton radioButton = findViewById(R.id.radioButton);
        RadioButton radioButton2 = findViewById(R.id.radioButton2);
        if(Integer.valueOf(x.double_sided)==1){
            radioButton.setChecked(true);
        }
        else{
            radioButton2.setChecked(true);
        }

        b = (Button) findViewById(R.id.buttonC);
    }


    public void updateJob() {

        b.setEnabled(false);

    }

}