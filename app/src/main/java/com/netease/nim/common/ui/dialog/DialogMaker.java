package com.netease.nim.common.ui.dialog;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;

import com.easyhoms.easydoctor.common.utils.LogUtils;


public class DialogMaker {
	private static EasyProgressDialog progressDialog;

    public static EasyProgressDialog showProgressDialog(Context context, String message) {
        return showProgressDialog(context, null, message, true, null);
    }
    public static EasyProgressDialog showProgressDialog(Context context) {
        return showProgressDialog(context, null, "请稍后", true, null);
    }

	public static EasyProgressDialog showProgressDialog(Context context, String message, boolean cancelable) {
		return showProgressDialog(context, null, message, cancelable, null);
	}
	
	@Deprecated
	public static EasyProgressDialog showProgressDialog(Context context,
			String title, String message, boolean canCancelable, OnCancelListener listener) {

		if (progressDialog == null) {
			progressDialog = new EasyProgressDialog(context, message);
		} else if (progressDialog.getContext() != context) {
			// maybe existing dialog is running in a destroyed activity cotext
			// we should recreate one
			LogUtils.e("dialog", "there is a leaked window here,orign context: "
					+ progressDialog.getContext() + " now: " + context);
			dismissProgressDialog();
			progressDialog = new EasyProgressDialog(context, message);
		}

		progressDialog.setCancelable(canCancelable);
		progressDialog.setOnCancelListener(listener);

		progressDialog.show();

		return progressDialog;
	}
	
	public static void dismissProgressDialog() {
		if (null == progressDialog) {
			return;
		}
		if (progressDialog.isShowing()) {
			try {
				progressDialog.dismiss();
				progressDialog = null;
			} catch (Exception e) {
				// maybe we catch IllegalArgumentException here.
			}

		}

	}
	
	public static void setMessage(String message) {
		if (null != progressDialog && progressDialog.isShowing()
				&& !TextUtils.isEmpty(message)) {
			progressDialog.setMessage(message);
		}
	}

	public static void updateLoadingMessage(String message) {
		if (null != progressDialog && progressDialog.isShowing()
				&& !TextUtils.isEmpty(message)) {
			progressDialog.updateLoadingMessage(message);
		}
	}
	
	public static boolean isShowing() {
		return (progressDialog != null && progressDialog.isShowing());
	}
}
