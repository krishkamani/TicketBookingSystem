package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Newuser extends AppCompatActivity {
    EditText username,useremail,userpassword,userage;
    Button usersignup;
    TextView userlogin;
    FirebaseAuth firebaseAuth;
    ImageView userprofile;

    String uname,uemail,uage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newuser);
        firebaseAuth=FirebaseAuth.getInstance();

        username=(EditText)findViewById(R.id.user_name);
        useremail=(EditText)findViewById(R.id.lemail);
        userpassword=(EditText)findViewById(R.id.lpassword);
        usersignup=(Button)findViewById(R.id.signup);
        userlogin=(TextView)findViewById(R.id.login);

        userage=(EditText)findViewById(R.id.lage);
        userprofile=(ImageView)findViewById(R.id.profile_image);

        usersignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname=username.getText().toString().trim();
                uemail=useremail.getText().toString().trim();
                uage=userage.getText().toString().trim();
                String upassword=userpassword.getText().toString().trim();

                if(uname.isEmpty() || uemail.isEmpty() || upassword.isEmpty()|| uage.isEmpty())
                {
                    Toast.makeText(Newuser.this,"Please fill all details",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.createUserWithEmailAndPassword(uemail,upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                sendEmailVerification1();
                            }
                            else
                            {
                                Toast.makeText(Newuser.this,"Registation failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Newuser.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendEmailVerification1()
    {
        final FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Newuser.this,"Succesfully registered,verfication mail is sent",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        Intent intent=new Intent(Newuser.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(Newuser.this,"Verification mail isn't sent",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
