package com.example;


public class TriangleJNI {

    static {
        System.loadLibrary("triangle");
    }

    interface Callback {
        void onRadian(float radian);
    }

    public static native void draw(float w, float h, float scale, float angle);

    public static native void setRadCallback(Callback callback);
}
