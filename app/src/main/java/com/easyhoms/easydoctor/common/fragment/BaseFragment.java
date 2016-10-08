package com.easyhoms.easydoctor.common.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.netease.nim.common.ui.dialog.DialogMaker;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.xutils.x;


/**
 * Created by wyouflf on 15/11/4.
 */
public abstract class BaseFragment extends Fragment {
    private ProgressDialog mProgressDialog;
    public DisplayImageOptions mImageOptions;
    protected Activity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //显示图片的配置
        mImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        x.view().inject(this, this.getView());

        initView();
        initListener();
    }


    /**
     * 等待登录对话框
     */
    public void showLoginDlg() {

        DialogMaker.showProgressDialog(mContext);
    }

    public void closeDialog() {
//        ((BaseActivity) getActivity()).closeDialog();
//        if(mProgressDialog!=null&&mProgressDialog.isShowing()){
//            mProgressDialog.dismiss();
//        }

        DialogMaker.dismissProgressDialog();
    }

    public void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int stringId) {
        Toast.makeText(getActivity(), getText(stringId), Toast.LENGTH_SHORT).show();
    }

    public Context mContext(){
        return this.getActivity();
    }

    protected abstract void initView();

    protected abstract void initListener();



}
