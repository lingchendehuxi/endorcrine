package cn.incongress.endorcrinemagazine.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.incongress.endorcrinemagazine.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView title = (TextView) findViewById(R.id.activity_title);
        title.setText(R.string.register_rgs);
    }
    public void back(View view){
        finish();
    }
}
