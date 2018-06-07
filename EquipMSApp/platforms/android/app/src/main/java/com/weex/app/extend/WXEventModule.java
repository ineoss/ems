package com.weex.app.extend;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.taobao.weex.bridge.JSCallback;
import com.weex.app.MainActivity;
import com.weex.app.QRCreator;
import com.weex.app.SplashActivity;
import com.weex.app.WXPageActivity;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.weex.app.zxingUtil.decode.android.CaptureActivity;
import com.weex.app.zxingUtil.decode.bean.ZxingConfig;
import com.weex.app.zxingUtil.decode.common.Constant;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class WXEventModule extends WXModule {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 0x9;
    private int REQUEST_CODE_SCAN = 111;
    private String dataString = "testdata";
    JSCallback callback = null;

    @JSMethod(uiThread = true)
    public void createQRCode(String msg) {
        Intent intent = new Intent(mWXSDKInstance.getContext(), QRCreator.class);
        intent.putExtra("msg", msg);
        mWXSDKInstance.getContext().startActivity(intent);
    }

    @JSMethod(uiThread = true)
    public void scan(JSCallback callback) {
        if (ContextCompat.checkSelfPermission(mWXSDKInstance.getContext(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mWXSDKInstance.getContext(),
                    Manifest.permission.CAMERA)) {
                Toast.makeText(mWXSDKInstance.getContext(), "扫面二维码需要相机权限", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions((Activity) mWXSDKInstance.getContext(),
                        new String[] { Manifest.permission.CAMERA }, CAMERA_PERMISSION_REQUEST_CODE);
            }
        } else {
            Intent intent = new Intent(mWXSDKInstance.getContext(), CaptureActivity.class);
            Activity activity = (Activity) mWXSDKInstance.getContext();
            this.callback = callback;
            activity.startActivityForResult(intent, REQUEST_CODE_SCAN);

        }
    }

    @JSMethod(uiThread = false)
    public void sendData(String data, JSCallback callback) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                callback.invoke(content);// 回传weex
            }
        }
    }
}
