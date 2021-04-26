package com.example;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private volatile float angle = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        );
        frameLayout.setLayoutParams(layoutParams);
        TextView textView = new TextView(this);
        FrameLayout.LayoutParams textViewParams = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        textViewParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        textView.setLayoutParams(textViewParams);
        textView.setText(String.valueOf(angle));
        textViewParams.setMargins(0, 120, 0, 0);
        SeekBar seekBar = new SeekBar(this);
        seekBar.setMax(360);
        FrameLayout.LayoutParams seekParams = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        seekParams.gravity = Gravity.BOTTOM;
        seekParams.setMargins(120, 0, 120, 120);
        seekBar.setLayoutParams(seekParams);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                angle = (float) i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        TriangleJNI.Callback callback = new TriangleJNI.Callback() {
            @Override
            public void onRadian(float radian) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(String.valueOf(radian));
                    }
                });
            }
        };

        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new GLSurfaceView.Renderer() {

            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                TriangleJNI.setRadCallback(callback);
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                TriangleJNI.draw(1000f, 2000f, 0f, angle);
            }
        });

        frameLayout.addView(glSurfaceView);
        frameLayout.addView(seekBar, seekParams);
        frameLayout.addView(textView, textViewParams);
        setContentView(frameLayout);
    }
}
