package com.app.exitads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

public class QExitLibPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    Context context;
    Activity activity;
    NativeExitAdsManager nativeExitAdsManager;

    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "q_exit_libs");
        context = flutterPluginBinding.getApplicationContext();
        channel.setMethodCallHandler(this);
    }


    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (activity == null) {
            result.error("context null", null, null);
            return;
        }
        String adUnit;
        switch (call.method) {
            case "showDialogConfirmExit":
                adUnit = call.argument("nativeAdUnit");
                DialogConfirmExit.showDialog(activity, adUnit);
                result.success(true);
                break;
            case "cacheExitAds":
                adUnit = call.argument("nativeAdUnit");
                nativeExitAdsManager = NativeExitAdsManager.cacheAds(context, adUnit);
                result.success(true);
                break;
            default:
                result.notImplemented();
                break;
        }
    }


    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        context = null;
        nativeExitAdsManager = null;
        channel.setMethodCallHandler(null);
    }

    @Override public void onAttachedToActivity(@NonNull ActivityPluginBinding activityPluginBinding) {
        activity = activityPluginBinding.getActivity();
    }

    @Override public void onDetachedFromActivityForConfigChanges() {

    }

    @Override public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding activityPluginBinding) {

    }

    @Override public void onDetachedFromActivity() {
        activity = null;
    }
}
