#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_mark_asciiartapp_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}



extern "C"
JNIEXPORT jstring JNICALL
Java_com_mark_asciiartapp_algorithm_Ascii_changeResourceToAscii(JNIEnv *env, jclass type,
                                                                jstring inputPath_) {
    const char *inputPath = env->GetStringUTFChars(inputPath_, 0);


    env->ReleaseStringUTFChars(inputPath_, inputPath);

    const char *returnValue;
    return env->NewStringUTF(returnValue);
}