package cn.incongress.endorcrinemagazine.activity;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.base.BaseActivity;

public class MyCollectionActivity extends BaseActivity {
    @BindView(R.id.activity_title)
    TextView mTitle;
    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_collection);
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mTitle.setText(getString(R.string.mycollection));
    }

    @Override
    protected void initializeEvents() {

    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {

    }

    @Override
    protected void handleDetailMsg(Message msg) {

    }

    public void back(View view){
        finish();
    }
}
