package com.example.admin.dairyfirm.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admin.dairyfirm.R;
import com.example.admin.dairyfirm.Utility.SharedPrefereneceUtil;

public class LogoutActivity extends AppCompatActivity {
    Button btnlogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        btnlogout=(Button)findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefereneceUtil.LogOut(LogoutActivity.this);
                SharedPreferences settings = LogoutActivity.this.getSharedPreferences("Login", Context.MODE_PRIVATE);
                settings.edit().clear().apply();
                Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });
    }
}


