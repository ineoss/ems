package com.weex.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.weex.app.zxingUtil.encode.CodeCreator;

public class QRCreator extends AppCompatActivity {
    private TextView info;
    private ImageView contentIv;
    private EditText contentEt;
    private Button encodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_qrcreator);
        /*生成的图片*/
        contentIv = findViewById(R.id.contentIv);
        info = findViewById(R.id.info);
        Bitmap bitmap = null;
        String text = extras.getString("msg");
        info.setText(text);
        try {
            bitmap = CodeCreator.createQRCode(text, 500, 500, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            contentIv.setImageBitmap(bitmap);
        }
    }
}
