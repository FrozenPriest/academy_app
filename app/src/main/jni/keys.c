#include <jni.h>

JNIEXPORT jstring JNICALL
Java_ru_frozenrpiest_academyapp_data_network_RetrofitModule_getMoviesApiKey(JNIEnv *env, jobject thiz) {
 return (*env)->  NewStringUTF(env, "8d40239a344fb5b50a5eaf04b6dae3c5");
}