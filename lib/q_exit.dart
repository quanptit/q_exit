library q_exit;

import 'dart:io' show Platform;
import 'package:q_common_utils/preferences_utils.dart';
import 'package:q_exit/src/q_exit_lib_plugin_interface.dart';
export 'src/confirm_quit.dart';

class QExitLib {
  static Future<bool?> cacheExitAds(String nativeAdUnit) async {
    if (!Platform.isAndroid || nativeAdUnit.isEmpty) return false;
    bool isRemoveAds = await PreferencesUtils.getBool("REMOVE_ADS", defaultValue: false);
    if (isRemoveAds) return false;
    return QExitLibPlatform.instance.cacheExitAds(nativeAdUnit);
  }

  static Future<bool?> showDialogConfirmExit(String nativeAdUnit) {
    if (!Platform.isAndroid || nativeAdUnit.isEmpty) return Future.value(false);
    return QExitLibPlatform.instance.showDialogConfirmExit(nativeAdUnit);
  }
}
