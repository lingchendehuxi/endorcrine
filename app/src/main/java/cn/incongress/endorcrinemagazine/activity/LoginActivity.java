package cn.incongress.endorcrinemagazine.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.incongress.endorcrinemagazine.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView tv = (TextView) findViewById(R.id.login_register);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(getApplication(),RegisterActivity.class);
                startActivity(it);
                finish();
            }
        });
    }
    public void back(View view){
        finish();
    }
}
