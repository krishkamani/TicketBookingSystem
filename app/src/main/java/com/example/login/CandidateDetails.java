package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CandidateDetails extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
    Button goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_details);

        t1=(TextView)findViewById(R.id.detail11);
        t2=(TextView)findViewById(R.id.detail22);
        t3=(TextView)findViewById(R.id.detail33);
        t4=(TextView)findViewById(R.id.detail44);
        t5=(TextView)findViewById(R.id.detail55);
        t6=(TextView)findViewById(R.id.detail66);
        t7=(TextView)findViewById(R.id.detail77);
        t8=(TextView)findViewById(R.id.detail88);
        t9=(TextView)findViewById(R.id.detail99);
        goback=(Button)findViewById(R.id.but1);

        Bundle b=getIntent().getExtras();
        t1.setText(b.getString("name"));
        t2.setText(String.valueOf(b.getInt("age")));
        t3.setText(String.valueOf(b.getLong("mobileno")));
        t4.setText(b.getString("tickettype"));
        t5.setText(b.getString("servicetype"));
        t6.setText(b.getString("departure"));
        t7.setText(b.getString("arrival"));
        t8.setText(b.getString("dates"));
        t9.setText(b.getString("times"));

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int2=new Intent(CandidateDetails.this,MainActivity.class);
                startActivity(int2);
            }
        });
    }
}

