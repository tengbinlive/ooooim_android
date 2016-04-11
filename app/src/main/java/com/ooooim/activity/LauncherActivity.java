package com.ooooim.activity;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.RelativeLayout;

import com.core.util.CommonUtil;
import com.core.util.StringUtil;
import com.dao.Parent;
import com.debug.ChannelUtil;
import com.ooooim.AbsActivity;
import com.ooooim.App;
import com.ooooim.BuildConfig;
import com.ooooim.R;
import com.ooooim.activityexpand.AnimatedRectLayout;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 启动界面
 */
public class LauncherActivity extends AbsActivity {

    private final static int TO_LOGIN = 0;
    private final static int TO_GUIDE = 1;

    private int statue;

    @Bind(R.id.launcher_ly)
    RelativeLayout launcherLy;

    @Override
    public void EInit() {
        if (BuildConfig.CHANNEL_DEBUG) {
            String channel_id = ChannelUtil.getChannel(this);
            if (StringUtil.isNotBlank(channel_id)) {
                CommonUtil.showToast(channel_id);
            }
        }
        super.EInit();
        setSwipeBackEnable(false);
        statue = TO_GUIDE;
        long time = 2000;
        activityHandler.sendEmptyMessageDelayed(statue, time);
    }

    @Override
    public void onResume() {
        super.onResume();
        launcherLy.setEnabled(true);
    }

    @OnClick(R.id.launcher_ly)
    void OnClickActivity() {
        launcherLy.setEnabled(false);
        activityHandler.removeMessages(statue);
        statue = TO_LOGIN;
        activityHandler.sendEmptyMessage(statue);
    }

    @Override
    public void onBackPressed() {
        activityHandler.removeMessages(statue);
        App.getInstance().exit();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_launcher;
    }

    private void toLogining() {
        Parent parent = App.getInstance().getUserResult().getParent();
        if (null != parent) {
            toMain();
        } else {
            toLoginActivity();
        }
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("animation_type", AnimatedRectLayout.ANIMATION_WAVE_TR);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void toGuide() {

    }

    private void toMain() {

    }

    @Override
    public void EDestroy() {
        super.EDestroy();
        activityHandler.removeMessages(statue);
        activityHandler = null;
    }

    private Handler activityHandler = new Handler() {
        public void handleMessage(Message msg) {
            toActivity(msg);
        }
    };

    private void toActivity(Message msg) {
        int statue = msg.what;
        switch (statue) {
            case TO_LOGIN:
                toLogining();
                break;
            case TO_GUIDE:
                toGuide();
                break;
            default:
                break;
        }
    }

}
