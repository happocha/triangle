//
// Created by v.potelov on 24.04.2021.
//

#include "Triangle.hpp"
#include <android/log.h>
#include "../../../../../../Library/Android/sdk/ndk/21.2.6472646/toolchains/llvm/prebuilt/darwin-x86_64/sysroot/usr/include/jni.h"

#define  LOG_TAG    "Log From JNI"
#define  ALOG(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

Triangle triangle;

extern "C" JNIEXPORT void JNICALL
Java_com_example_TriangleJNI_draw(
        JNIEnv *env,
        jclass clazz,
        jfloat w,
        jfloat h,
        jfloat scale,
        jfloat angle) {

    triangle.draw(w, h, scale, angle);
}

jclass callbacksClass;
jobject callbacksInstance;

extern "C"
JNIEXPORT void JNICALL
Java_com_example_TriangleJNI_setRadCallback(JNIEnv *env, jclass clazz, jobject callback) {
    callbacksInstance = env->NewGlobalRef(callback);
    jclass objClass = env->GetObjectClass(callback);
    if (objClass) {
        callbacksClass = reinterpret_cast<jclass>(env->NewGlobalRef(objClass));
        env->DeleteLocalRef(objClass);
    }

    jmethodID methodId = env->GetMethodID(callbacksClass, "onRadian", "(F)V");
    triangle.setRadCallback(
            [env, methodId](float radian) -> void {
                ALOG("radian is = %f", radian);
                env->CallVoidMethod(callbacksInstance, methodId, (jfloat) radian);
            }
    );
}
