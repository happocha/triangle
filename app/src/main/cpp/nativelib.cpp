//
// Created by v.potelov on 24.04.2021.
//

#include <unordered_map>
#include "Triangle.hpp"
#include <android/log.h>
#include "../../../../../../Library/Android/sdk/ndk/21.2.6472646/toolchains/llvm/prebuilt/darwin-x86_64/sysroot/usr/include/jni.h"

#define  LOG_TAG    "Log From JNI"
#define  ALOG(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)

std::unordered_map<jint, Triangle> map;

extern "C"
JNIEXPORT void JNICALL
Java_com_example_TriangleFragment_setRadCallback(JNIEnv *env, jobject thiz, jobject callback,
                                                 jint jHash) {
    map[jHash] = Triangle();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_TriangleFragment_draw(JNIEnv *env, jobject thiz, jfloat w, jfloat h, jfloat scale,
                                       jfloat angle, jint jHash) {
    map[jHash].draw(w, h, scale, angle);
}
