package com.ooooim.activity;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.core.CommonResponse;
import com.core.util.CommonUtil;
import com.dao.ParentDao;
import com.gitonway.lee.niftymodaldialogeffects.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.NiftyDialogBuilder;
import com.ooooim.App;
import com.ooooim.R;
import com.ooooim.activityexpand.AnimatedRectActivity;
import com.ooooim.bean.user.UserResult;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AnimatedRectActivity {

    public NiftyDialogBuilder dialogBuilder;
    public LayoutInflater mInflater;
    @Bind(R.id.toolbar_left_btn)
    TextView toolbarLeftBtn;
    @Bind(R.id.toolbar_intermediate_tv)
    TextView toolbarIntermediateTv;

    private boolean isCancle;

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void animationStartEnd() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCancle = getIntent().getBooleanExtra("login", false);
        mInflater = LayoutInflater.from(this);
        ButterKnife.bind(this);
        setStatusBar();
    }

    @Override
    public void onBackPressed() {
        if (isCancle) {
            App.getInstance().exit();
        }
        super.onBackPressed();
    }

    /**
     * 设置statusbar全透明
     */
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        toolbarIntermediateTv.setText(R.string.login);
        toolbarLeftBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                onBackPressed();
            }
        });
    }

    public void dialogShow(int title) {
        dialogDismiss();
        LinearLayout convertView = (LinearLayout) mInflater.inflate(R.layout.loading_view, null);
        TextView dialog_confirm_content = (TextView) convertView.findViewById(R.id.dialog_confirm_content);
        dialog_confirm_content.setText(title);
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getRepeatCount() == 0) {
                    return true;
                }
                return false;
            }
        });
        dialogBuilder.withDuration(700) // def
                .isCancelableOnTouchOutside(false) // def | isCancelable(true)
                .withEffect(Effectstype.Fadein) // def Effectstype.Slidetop
                .setCustomView(convertView, this); // .setCustomView(View
        activityHandler.sendEmptyMessage(DIALOGSHOW);

    }

    public void dialogDismiss() {
        if (null != dialogBuilder && dialogBuilder.isShowing()) {
            activityHandler.sendEmptyMessage(DIALOGDISMISS);
        }
    }

    private void loadLogin(CommonResponse resposne) {
        dialogDismiss();
        if (resposne.isSuccess()) {
            UserResult result = (UserResult) resposne.getData();
            loginSuccess(result);
        } else {
            if (App.getInstance().isNoAccount()) {
                loginSuccess(UserResult.testData());
                return;
            }
            CommonUtil.showToast(resposne.getMsg());
        }
    }

    private void loginSuccess(UserResult result) {
        App.getInstance().setUserResult(result);
        ParentDao dao = App.getDaoSession().getParentDao();
        dao.deleteAll();
        dao.insertInTx(result.getParent());
    }

    private final static int LOGIN_ING = 3;
    private final static int DIALOGSHOW = 1;
    private final static int DIALOGDISMISS = 0;

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DIALOGSHOW:
                    dialogBuilder.show();
                    break;
                case DIALOGDISMISS:
                    dialogBuilder.dismiss();
                    break;
                case LOGIN_ING:
                    loadLogin((CommonResponse) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

}
