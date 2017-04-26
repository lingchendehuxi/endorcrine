package cn.incongress.endorcrinemagazine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.activity.FeedbackActivity;
import cn.incongress.endorcrinemagazine.activity.LoginActivity;
import cn.incongress.endorcrinemagazine.activity.MyCollectionActivity;
import cn.incongress.endorcrinemagazine.base.BaseLazyFragment;
import cn.incongress.endorcrinemagazine.utils.ShareUtils;

/**
 * Created by Jacky on 2017/4/7.
 * 我
 */

public class MeFragment extends BaseLazyFragment implements View.OnClickListener{
    private ImageView tx_img;
    private Button login_btn;
    private LinearLayout myCollection,recommend,feedback;
    @Override
    protected void initPrepare() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        myCollection = (LinearLayout) view.findViewById(R.id.myCollection);
        feedback = (LinearLayout) view.findViewById(R.id.feedback);
        recommend = (LinearLayout) view.findViewById(R.id.recommend);
        tx_img = (ImageView) view.findViewById(R.id.img_tx);
        login_btn = (Button) view.findViewById(R.id.btn_me_login);

        myCollection.setOnClickListener(this);
        feedback.setOnClickListener(this);
        recommend.setOnClickListener(this);
        tx_img.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        return view;
    }
private UMShareListener listener = new UMShareListener() {
    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
};
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myCollection:
                startActivity( new Intent(getActivity(), MyCollectionActivity.class));
                break;
            case R.id.feedback:
                startActivity( new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.recommend:
              new ShareUtils().shareTextWithUrl(getActivity(),"分享","分享内容","http://www.baidu.com",listener);
                break;
            case R.id.img_tx:

                break;
            case R.id.btn_me_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
