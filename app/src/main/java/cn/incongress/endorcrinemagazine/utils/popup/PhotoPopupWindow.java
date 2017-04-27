package cn.incongress.endorcrinemagazine.utils.popup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import cn.incongress.endorcrinemagazine.R;
import cn.incongress.endorcrinemagazine.utils.ToastUtils;


/**
 * Created by Jacky on 2016/4/2.
 */
public class PhotoPopupWindow extends BasePopupWindow implements View.OnClickListener{
    private LinearLayout mLlCamera,mLlChooseFromPhoto,mLlCancel;
    private View popupView;

    private OnTakePhotoListener mTakePhotoListener;
    private OnAlbumListener mAlbumListener;

    public PhotoPopupWindow(Activity context, OnTakePhotoListener photoListener, OnAlbumListener albumListener) {
        super(context);

        mLlCamera = (LinearLayout) mPopupView.findViewById(R.id.ll_take_photo);
        mLlChooseFromPhoto = (LinearLayout) mPopupView.findViewById(R.id.ll_choose_from_camera);
        mLlCancel = (LinearLayout) mPopupView.findViewById(R.id.ll_cancel);

        this.mTakePhotoListener = photoListener;
        this.mAlbumListener = albumListener;

        mLlCamera.setOnClickListener(this);
        mLlChooseFromPhoto.setOnClickListener(this);
        mLlCancel.setOnClickListener(this);

        setAutoShowInputMethod(true);
    }

    @Override
    protected Animation getShowAnimation() {
        return getDefaultAlphaInAnimation();
    }

    @Override
    public Animation getExitAnimation() {
        return getDefaultAlphaOutAnimation();
    }

    @Override
    public View getPopupViewById(int resId) {
        popupView= LayoutInflater.from(mContext).inflate(R.layout.popup_photo,null);
        return popupView;
    }

    @Override
    protected View getClickToDismissView() {
        return  popupView.findViewById(R.id.rl_bg);
    }

    @Override
    public View getPopupView() {
        return getPopupViewById(R.layout.popup_photo);
    }

    @Override
    public View getAnimaView() {
        return popupView.findViewById(R.id.ll_popup_container);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_cancel:
                dismiss();
                break;
            case R.id.ll_take_photo:
                if(mTakePhotoListener != null)
                    mTakePhotoListener.takePhotoListen();
                break;
            case R.id.ll_choose_from_camera:
                if(mAlbumListener != null)
                    mAlbumListener.albumListen();
                ToastUtils.showShortToast("相册", mContext);
                break;
        }
    }

    public interface OnTakePhotoListener{
        void takePhotoListen();
    }

    public interface OnAlbumListener{
        void albumListen();
    }
}
