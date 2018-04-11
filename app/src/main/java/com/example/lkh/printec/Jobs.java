package com.example.lkh.printec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Jobs extends AppCompatActivity{
    Button b;

    private TextView JobID;
    private TextView file_name;
    private TextView username;
    private TextView owner_mobile;
    private TextView loc;
    private TextView no_copies;
    private TextView col;
    private TextView dou_side;
    private TextView timee;
    private TextView file_link;

    private save_job_info x;

    private DatabaseReference db_reference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        Intent intent = getIntent();
        /*Bundle bundle = intent.getExtras();
        save_job_info x = (save_job_info) bundle.getSerializable("obj");*/
        x = (save_job_info) intent.getSerializableExtra("obj");

        JobID = (TextView) findViewById(R.id.jobID_value);
        username = (TextView) findViewById(R.id.user_name_value);
        file_name = (TextView) findViewById(R.id.document_name_value);
        file_link = (TextView) findViewById(R.id.doc_link_value);
        timee = (TextView) findViewById(R.id.time_value);
        no_copies = (TextView) findViewById(R.id.copies_value);
        col = (TextView) findViewById(R.id.color_value);
        dou_side = (TextView) findViewById(R.id.double_value);


        timee.setText(x.created_on);
        JobID.setText(x.job_id);
        file_name.setText(x.document_name);
        username.setText(x.user_id);
        file_link.setText(x.document_link);
        no_copies.setText(x.copies);
        String colour,double_side;
        if(x.coloured.equals("0")){
            colour = "NO";
        }
        else{
            colour = "YES";
        }
        col.setText(colour);
        if(x.double_sided.equals("0")){
            double_side = "NO";
        }
        else{
            double_side = "YES";
        }
        dou_side.setText(double_side);


        b = (Button) findViewById(R.id.buttonC);
        b.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        final String finishedDate = df.format(c.getTime());
                        x.finished_on = finishedDate;

                        final DatabaseReference db_finish = db_reference1.child("jobs").child(x.job_id);
                        db_finish.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                db_finish.child("finished_on").setValue(finishedDate);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
        );

        db_reference1 = FirebaseDatabase.getInstance().getReference();
    }


    public void updateJob() {

    }

}