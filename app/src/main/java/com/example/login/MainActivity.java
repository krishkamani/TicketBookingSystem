package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button login;
    TextView newuser;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    TextView changepass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            Intent intent=new Intent(this,a.class);
            startActivity(intent);
        }
        email=(EditText)findViewById(R.id.semail);
        password=(EditText)findViewById(R.id.spassword);
        login=(Button)findViewById(R.id.slogin);
        newuser=(TextView) findViewById(R.id.snewuser);
        progressDialog=new ProgressDialog(this);

        changepass=(TextView)findViewById(R.id.sforgotpassword);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,changepassword.class);
                startActivity(intent);
            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Newuser.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uemail = email.getText().toString().trim();
                String upass = password.getText().toString().trim();
                if (uemail.isEmpty() || upass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter all dedtails", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("Verifying email and password");
                    progressDialog.show();
                    firebaseAuth.signInWithEmailAndPassword(uemail, upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                checkEmailverification();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "email or password is incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });                }
            }
        });
    }
    private void checkEmailverification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();
        if(emailflag==true)
        {   finish();
            Intent intent=new Intent(this,a.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this,"Please verify your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }


}
