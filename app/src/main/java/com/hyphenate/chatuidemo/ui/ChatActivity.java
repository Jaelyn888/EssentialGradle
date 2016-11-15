package com.hyphenate.chatuidemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.hyphenate.chatuidemo.runtimepermissions.PermissionsManager;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;
import com.yishanxiu.yishang.R;
import com.yishanxiu.yishang.activity.MainActivity2;
import com.yishanxiu.yishang.activity.base.BaseActivity;
import com.yishanxiu.yishang.utils.Constant;


public class ChatActivity extends BaseActivity {
    private String toChatUsername;
    private EaseChatFragment chatFragment;
    public static ChatActivity activityInstance;

    /**
     * @author FangDongzhang 2016/5/11 聊天页
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_chat);
        activityInstance = this;
        // 聊天人或群id
        toChatUsername = getIntent().getExtras().getString(Constant.EXTRA_USER_ID);
        // setCenter_title(getIntent().getExtras().getString(Constant.EXTRA_USER_ID));

        // 可以直接new EaseChatFratFragment使用
        chatFragment = new ChatFragment();
        // 传入参数
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
    	// make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }
    }
    
    @Override
    public void onBackPressed() {
        chatFragment.onBackPressed();
        if (EasyUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    
    public String getToChatUsername(){
        return toChatUsername;
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        activityInstance=null;
        super.onDestroy();
    }
}
