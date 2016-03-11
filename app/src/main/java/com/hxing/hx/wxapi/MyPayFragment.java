package com.hxing.hx.wxapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.hxing.hx.R;
import com.hxing.hx.common.base.BaseFragment;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class MyPayFragment extends BaseFragment {

	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;

	private RadioGroup mPayGroup;// 支付方式
	private Button mPayButton;// 支付按钮
	private ProgressDialog mLoadingDialog;// 加载框


	@Override
	public int getLayoutId() {
		return R.layout.pay_fragment;
	}

	@Override
	public void initView(View rootView) {
		mPayGroup = (RadioGroup) rootView.findViewById(R.id.rg_pay);
		mPayButton = (Button) rootView.findViewById(R.id.btn_pay);
		mPayButton.setOnClickListener(this);
	}


	private void pay() {// 支付
		int checkid = mPayGroup.getCheckedRadioButtonId();
		switch (checkid) {
			case R.id.rb_alipay:
				getAliPayInfo();
				break;
			case R.id.rb_wexinpay:
				getWexinPayInfo();
				break;
			default:
				break;
		}
	}

	private void getAliPayInfo() {// 获取支付宝支付信息
	}

	private void getWexinPayInfo() {// 获取微信支付信息
	}

	/**
	 * 支付宝支付
	 * @param payInfo 支付宝支付信息
	 */
	private void alipay(final String payInfo) {
		Toast.makeText(mActivity, "支付宝支付", Toast.LENGTH_SHORT).show();
		// 必须异步调用
		Thread payThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(mActivity);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		});
		payThread.start();
	}

	/**
	 * 微信支付
	 * @param reqParams 微信支付信息
	 */
	private void weixinpay(PayReq reqParams) {// 微信支付
		Toast.makeText(mActivity, "微信支付", Toast.LENGTH_SHORT).show();
		IWXAPI msgApi = WXAPIFactory.createWXAPI(mActivity, null);
		msgApi.registerApp(reqParams.appId);
		msgApi.sendReq(reqParams);
	}

	@SuppressLint({ "HandlerLeak", "InlinedApi" })
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (mLoadingDialog != null) {
				mLoadingDialog.dismiss();
				mLoadingDialog = null;
			}
			super.handleMessage(msg);
			switch (msg.what) {
			case SDK_PAY_FLAG: {// 支付宝支付结果
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				// String resultInfo = payResult.getResult();
				System.out.println("payResult---->" + payResult.getResult());
				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(mActivity, "支付成功", Toast.LENGTH_SHORT)
							.show();
					Intent intent = new Intent();
					intent.putExtra("flag", true);
					mActivity.setResult(Activity.RESULT_OK, intent);
					mActivity.finish();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(mActivity, "支付结果确认中", Toast.LENGTH_SHORT)
								.show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(mActivity, "支付失败", Toast.LENGTH_SHORT)
								.show();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(mActivity, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}

		}
	};

	@Override
	protected void lazyLoad() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_pay:// 支付
				pay();
				break;
			default:
				break;
		}
	}
}
