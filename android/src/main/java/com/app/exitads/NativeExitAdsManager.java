package com.app.exitads;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdk;

public class NativeExitAdsManager {
    private static String TAG = "NativeExitAdsManager";
    public static NativeExitAdsManager instance = null;

    MaxNativeAdView nativeAdView;
    MaxNativeAdLoader nativeAdLoader;

    private static void setNativeAdView(MaxNativeAdView nativeAdView) {
        if (instance == null) instance = new NativeExitAdsManager();
        instance.nativeAdView = nativeAdView;
    }

    public static NativeExitAdsManager cacheAds(Context context, String adUnit) {
        if (adUnit==null) return null;
        if (instance == null) instance = new NativeExitAdsManager();

        if (!AppLovinSdk.getInstance(context).isInitialized()) {
            Log.d(TAG, "AppLovinSdk Init");
            AppLovinSdk.getInstance(context).setMediationProvider("max");
            AppLovinSdk.initializeSdk(context, configuration -> instance._cacheAds(context, adUnit));
        } else {
            instance._cacheAds(context, adUnit);
        }
        return instance;
    }

    public static boolean displayNativeAds(Context context, FrameLayout layoutAds) {
        if (instance == null) return false;
        return instance._displayNativeAds(context, layoutAds);
    }

    private void _cacheAds(Context context, String adUnit) {
        Log.d(TAG, "cache exit Ads");
        try {
            if (nativeAdLoader != null) {
                nativeAdLoader.destroy();
                nativeAdView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        nativeAdLoader = new MaxNativeAdLoader(adUnit, context);
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                Log.d(TAG, "onNativeAdLoaded: " + ad.getNetworkName());
                try {
//                    ColorStateList textColor = getSystemAttrColor(context, android.R.attr.textColorPrimary);
                    nativeAdView.getTitleTextView().setTextColor(Color.BLACK);
                    nativeAdView.getAdvertiserTextView().setTextColor(Color.BLACK);
                    nativeAdView.getBodyTextView().setTextColor(Color.BLACK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setNativeAdView(nativeAdView);
            }

            @Override public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                Log.d(TAG, "onNativeAdLoadFailed: " + error.getMessage());
            }
        });

        nativeAdLoader.loadAd();
    }

    private boolean _displayNativeAds(Context context, FrameLayout nativeAdContainer) {
        if (nativeAdView == null || nativeAdView.getParent() != null)
            return false;
        try {
            // Add ad view to view.
            nativeAdContainer.removeAllViews();
            nativeAdContainer.addView(nativeAdView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ColorStateList getSystemAttrColor(Context context, int attr) {
        TypedArray a = context.obtainStyledAttributes(new int[]{attr});
        ColorStateList color = a.getColorStateList(a.getIndex(0)); a.recycle();
        return color;
    }
}
