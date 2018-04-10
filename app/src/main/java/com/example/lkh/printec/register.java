package com.example.lkh.printec;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference db_reference;
    EditText email,passwd,name,m_no,h_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        db_reference = FirebaseDatabase.getInstance().getReference();

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        passwd = (EditText) findViewById(R.id.editText6);
        m_no = (EditText) findViewById(R.id.editText5);
        h_no = (EditText) findViewById(R.id.hostel);

        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(register.this, login.class);
                startActivity(intent);*/
                createAcc();
            }
        });


    }

    public void createAcc(){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), passwd.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            /*FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);*/
                            String id1 = mAuth.getCurrentUser().getUid();

                            save_user_info save_user = new save_user_info(name.getText().toString(), email.getText().toString(), m_no.getText().toString(), h_no.getText().toString());
                            db_reference.child("shop_owner").child(id1).setValue(save_user);

                            Intent i = new Intent(register.this, login.class);
                            startActivity(i);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            /*updateUI(null);*/
                        }
                    }
                });
    }

}
