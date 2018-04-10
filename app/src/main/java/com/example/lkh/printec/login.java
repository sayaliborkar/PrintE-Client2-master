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

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText userid, passwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        userid = (EditText) findViewById(R.id.userid);
        passwd = (EditText) findViewById(R.id.passwd);
        Button log = (Button) findViewById(R.id.login_login);
        log.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                signIn();
            }
        });
    }


    public void signIn() {
        mAuth.signInWithEmailAndPassword(userid.getText().toString(), passwd.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            /*FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);*/
                            Intent intent = new Intent(login.this, Home.class);
                            intent.putExtra("shopId",mAuth.getCurrentUser().getUid());
                            intent.putExtra("name",userid.getText().toString());
                            intent.putExtra("mail",userid.getText().toString());
                            startActivity(intent);
                            //System.out.println("Yaay");
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            /*updateUI(null);*/
                        }

                        // ...
                    }
                });
    }

}