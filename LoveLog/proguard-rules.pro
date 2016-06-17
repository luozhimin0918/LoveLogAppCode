# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:/myData/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}




-dontwarn butterknife.internal.**
-keep class butterknife.internal..** { *;}
-keep interface butterknife.internal.** { *; }

-dontwarn com.alibaba.fastjson.support.**
-keep class com.alibaba.fastjson.support.** { *;}
-keep interface com.alibaba.fastjson.support.** { *; }


-dontwarn io.realm.processor.**
-keep class io.realm.processor.** { *;}
-keep interface io.realm.processor.** { *; }



-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *;}
-keep interface com.alibaba.fastjson.** { *; }



-dontwarn com.alipay.android.**
-keep class com.alipay.android.** { *;}
-keep interface com.alipay.android.** { *; }


-dontwarn com.tencent.connect.**
-keep class com.tencent.connect.** { *;}
-keep interface com.tencent.connect.** { *; }


-dontwarn uk.co.senab.photoview.**
-keep class uk.co.senab.photoview.** { *;}
-keep interface uk.co.senab.photoview.** { *; }
