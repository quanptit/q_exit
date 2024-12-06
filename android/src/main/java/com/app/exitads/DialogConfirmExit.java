package com.app.exitads;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

public class DialogConfirmExit {
//    extends DialogFragment
//    ImageView imvIcon;
//    TextView tvDes, tvTitleDialog;
//    FrameLayout layoutAds;
//    Button btnQuit;
//    Button btnInstall;
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//
//        try {
//            // request a window without the title
//            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return dialog;
//    }
//
//    private void injectView(View v) {
////        imvIcon = v.findViewById(R.id.imvIcon);
////        tvDes = v.findViewById(R.id.tvDes);
////        tvTitleDialog = v.findViewById(R.id.tvTitleDialog);
////        layoutAds = v.findViewById(R.id.layoutAds);
////        btnQuit = v.findViewById(R.id.btnQuit);
////        btnInstall = v.findViewById(R.id.btnInstall);
////        btnQuit.setOnClickListener(new View.OnClickListener() {
////            @Override public void onClick(View v) {
////                btnQuitClick();
////            }
////        });
////        btnInstall.setOnClickListener(new View.OnClickListener() {
////            @Override public void onClick(View v) {
////                btnInstallClick();
////            }
////        });
//    }
//
//    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.dialog_confirm_exit_ads, container, false);
//        injectView(v);
//        NativeExitAdsManager.displayNativeAds(getContext(), layoutAds);
//        tvTitleDialog.setText(R.string.confirm);
//        tvDes.setText(R.string.confirm_quit_mes);
//        btnQuit.setText(R.string.OK);
//        btnInstall.setText(R.string.cancel);
//        return v;
//    }
//
//    void btnInstallClick() {
//        dismiss();
//    }
//
//    void btnQuitClick() {
//        getActivity().finish();
//    }
//
////    public static boolean showDialog(FragmentActivity fragmentActivity) {
////        DialogFragment dialogFragment = new DialogConfirmExit();
////        Bundle bundle = new Bundle();
////        dialogFragment.setArguments(bundle);
////        dialogFragment.show(fragmentActivity.getSupportFragmentManager(), "DialogConfirmExit");
////        return true;
////    }
//
//    @Override public void onDestroyView() {
//        super.onDestroyView();
//    }
//

    public static boolean showDialog(Activity activity, String adUnit) {
        AlertDialog.Builder alert;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alert = new AlertDialog.Builder(new ContextThemeWrapper(activity, android.R.style.Theme_Material_Light_Dialog));
        } else
            alert = new AlertDialog.Builder(new ContextThemeWrapper(activity, android.R.style.Theme_Holo_Light_Dialog));
        View v = LayoutInflater.from(activity).inflate(R.layout.dialog_confirm_exit_ads, null);
        FrameLayout layoutAds = v.findViewById(R.id.layoutAds);
        View vDivider = v.findViewById(R.id.vDivider);
        if (NativeExitAdsManager.displayNativeAds(activity, layoutAds))
            vDivider.setVisibility(View.VISIBLE);
        else{
            vDivider.setVisibility(View.GONE);
            layoutAds.setVisibility(View.GONE);
        }

        alert.setView(v);
        alert.setCancelable(false);
        alert.setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.dismiss();
            try {
                NativeExitAdsManager.cacheAds(activity, adUnit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        alert.setPositiveButton(R.string.OK, (dialog, which) -> {activity.finish();});
        AlertDialog dialog = alert.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        return true;
    }
}
