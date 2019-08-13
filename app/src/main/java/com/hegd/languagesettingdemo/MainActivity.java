package com.hegd.languagesettingdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * adb shell am broadcast -a "android.intent.action.USER_SET_LANGUAGE" -e "type" "RU"
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private String TAG = "heguodong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewsAndEvents();
    }

    private void initViewsAndEvents() {
        findViewById(R.id.btn_switch_to_zh_CN).setOnClickListener(this);//简体中文
        findViewById(R.id.btn_switch_to_en).setOnClickListener(this);//英语
        findViewById(R.id.btn_switch_to_de).setOnClickListener(this);//德语
        findViewById(R.id.btn_switch_to_fr).setOnClickListener(this);//法语
        findViewById(R.id.btn_switch_to_ru_RU).setOnClickListener(this);//俄语
        findViewById(R.id.btn_switch_to_en_IN).setOnClickListener(this);//英语印度
        findViewById(R.id.btn_switch_to_en_TH).setOnClickListener(this);//英语泰国
        findViewById(R.id.btn_switch_to_en_PM).setOnClickListener(this);//英语巴基斯坦

        findViewById(R.id.btn_finish).setOnClickListener(this);//关闭界面
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_switch_to_zh_CN:
                sendBroadcastToUpdateLanguage("CN");
                break;
            case R.id.btn_switch_to_en:
                sendBroadcastToUpdateLanguage("UK");
                break;
            case R.id.btn_switch_to_de:
                sendBroadcastToUpdateLanguage("DE");
                break;
            case R.id.btn_switch_to_fr:
                sendBroadcastToUpdateLanguage("FR");
                break;
            case R.id.btn_switch_to_ru_RU:
                sendBroadcastToUpdateLanguage("RU");
                break;
            case R.id.btn_switch_to_en_IN:
                sendBroadcastToUpdateLanguage("IN");
                break;
            case R.id.btn_switch_to_en_TH:
                sendBroadcastToUpdateLanguage("PM");
                break;
            case R.id.btn_switch_to_en_PM:
                sendBroadcastToUpdateLanguage("THA");
                break;

            case R.id.btn_finish:
                finish();
                break;
        }
    }

    /**
     * send broadcast to update language,system application will handle it.
     *
     * @param type Type of target language.
     */
    private void sendBroadcastToUpdateLanguage(String type) {
        Intent targetIntent = new Intent();
        targetIntent.setAction("android.intent.action.USER_SET_LANGUAGE");
        targetIntent.putExtra("type",type);
        sendBroadcast(targetIntent);
    }
}
