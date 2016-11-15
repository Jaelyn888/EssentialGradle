package com.yishanxiu.yishang.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yishanxiu.yishang.R;

public class WihteRoundCornersDialog extends Dialog {

	private Context mContext;
	private DialogCallBack callBack;
	private int count;
	private LinearLayout ll_title, ll_message, ll_onebutton, ll_twobutton,
			ll_threebutton;
	private TextView tv_title, tv_message, tv_onebutton, tv_twobutton,
			tv_threebutton;
	private String title, message, btn1, btn2, btn3;
	private int what = 0;
	private int titleid = 0, messageid = 0, btn1id = 0, btn2id = 0, btn3id = 0;

	public WihteRoundCornersDialog(Context context) {
		super(context);
		this.mContext = context;
	}

	public WihteRoundCornersDialog(Context context, int theme, int num,
	                               DialogCallBack callBack) {
		super(context, theme);
		this.mContext = context;
		this.count = num;
		this.callBack = callBack;

	}

	public WihteRoundCornersDialog(Context context, int theme, int num,
	                               DialogCallBack callBack, int what) {
		super(context, theme);
		this.mContext = context;
		this.count = num;
		this.callBack = callBack;
		this.what = what;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.whiteroundcornersdialog);
		ll_title = (LinearLayout) findViewById(R.id.white_dialog_ll_title);
		ll_message = (LinearLayout) findViewById(R.id.white_dialog_ll_message);
		ll_onebutton = (LinearLayout) findViewById(R.id.white_dialog_ll_btn);
		ll_twobutton = (LinearLayout) findViewById(R.id.white_ll_two_button);
		ll_threebutton = (LinearLayout) findViewById(R.id.white_ll_three_button);
		tv_title = (TextView) findViewById(R.id.white_dialog_title);
		tv_message = (TextView) findViewById(R.id.white_dialog_message);
		tv_onebutton = (TextView) findViewById(R.id.white_one_button);
		tv_twobutton = (TextView) findViewById(R.id.white_two_button);
		tv_threebutton = (TextView) findViewById(R.id.white_three_button);
		if (count == 1) {
			ll_onebutton.setVisibility(View.VISIBLE);
		} else if (count == 2) {
			ll_onebutton.setVisibility(View.VISIBLE);
			ll_twobutton.setVisibility(View.VISIBLE);
		} else if (count == 3) {
			ll_onebutton.setVisibility(View.VISIBLE);
			ll_twobutton.setVisibility(View.VISIBLE);
			ll_threebutton.setVisibility(View.VISIBLE);
		}
		if (title != null) {
			ll_title.setVisibility(View.VISIBLE);
			tv_title.setText(title);
		} else if (titleid != 0) {
			ll_title.setVisibility(View.VISIBLE);
			tv_title.setText(titleid);
		}
		if (message != null) {
			ll_message.setVisibility(View.VISIBLE);
			tv_message.setText(message);
		} else if (messageid != 0) {
			ll_message.setVisibility(View.VISIBLE);
			tv_message.setText(messageid);
		}
		if (btn1 != null) {
			tv_onebutton.setText(btn1);
		} else if (btn1id != 0) {
			tv_onebutton.setText(btn1id);
		}
		if (btn2 != null) {
			tv_twobutton.setText(btn2);
		} else if (btn2id != 0) {
			tv_twobutton.setText(btn2id);
		}
		if (btn3 != null) {
			tv_threebutton.setText(btn3);
		} else if (btn2id != 0) {
			tv_threebutton.setText(btn2id);
		}
		if (tv_onebutton != null) {
			tv_onebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
	                        if (null == callBack) {
		                        return;
	                        }
                            if (what != 0) {
                                callBack.bttonclick(1, what);
                            } else {
                                callBack.bttonclick(1);
                            }

                        }
                    });
		}
		if (tv_twobutton != null) {
			// ((TextView) findViewById(R.id.white_two_button))
			tv_twobutton
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (null == callBack) {
								return;
							}
							if (what != 0) {
								callBack.bttonclick(2, what);
							} else {
								callBack.bttonclick(2);
							}
						}
					});
		}
		if (tv_threebutton != null) {
			tv_threebutton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
	                        if (null == callBack) {
		                        return;
	                        }
                            if (what != 0) {
                                callBack.bttonclick(3, what);
                            } else {
                                callBack.bttonclick(3);
                            }
                        }
                    });
		}
	}

	public void setButtonBackground(int button, int img) {
		switch (button) {
		case 1:
			tv_onebutton.setBackgroundResource(img);
			break;
		case 2:
			tv_twobutton.setBackgroundResource(img);
			break;
		case 3:
			tv_threebutton.setBackgroundResource(img);
			break;
		default:
			break;
		}
	}

	public void setTitletext(String title) {
		this.title = title;

	}

	public void setTitletext(int title) {
		this.titleid = title;
	}

	public void setMessagetext(String message) {
		this.message = message;
		// tv_message.setText(message);
	}

	public void setMessagetext(int message) {
		this.messageid = message;
		// tv_message.setText(message);
	}

	public void setButtonText(String btn1) {
		this.btn1 = btn1;
		// tv_onebutton.setText(btn1);
	}

	public void setButtonText(int btn1) {
		this.btn1id = btn1;
		// tv_onebutton.setText(btn1);
	}

	public void setButtonText(String btn1, String btn2) {
		this.btn1 = btn1;
		this.btn2 = btn2;
		// tv_onebutton.setText(btn1);
		// tv_twobutton.setText(btn2);
	}

	public void setButtonText(int btn1, int btn2) {
		this.btn1id = btn1;
		this.btn2id = btn2;
		// tv_onebutton.setText(btn1);
		// tv_twobutton.setText(btn2);
	}

	public void setButtonText(String btn1, String btn2, String btn3) {
		this.btn1 = btn1;
		this.btn2 = btn2;
		this.btn3 = btn3;

		// tv_onebutton.setText(btn1);
		// tv_twobutton.setText(btn2);
		// tv_threebutton.setText(btn3);
	}

	public void setButtonText(int btn1, int btn2, int btn3) {
		this.btn1id = btn1;
		this.btn2id = btn2;
		this.btn3id = btn3;
		// tv_onebutton.setText(btn1);
		// tv_twobutton.setText(btn2);
		// tv_threebutton.setText(btn3);
	}

	public interface DialogCallBack {

		void bttonclick(int index);

		void bttonclick(int index, int what);
	}

}
