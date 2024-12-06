import 'dart:io';

import 'package:flutter/material.dart';
import 'package:q_common_utils/languages/laguage_utils.dart';
import 'package:q_common_utils/preferences_utils.dart';
import 'package:q_common_utils/ui_utils.dart';
import 'package:q_exit/q_exit.dart';
import 'package:q_theme/app_theme.dart';

class ConfirmQuitAppWrap extends StatelessWidget {
  final Widget child;
  final String nativeAdUnit;

  const ConfirmQuitAppWrap({Key? key, required this.child, required this.nativeAdUnit}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    if (Platform.isAndroid) {
      return WillPopScope(
        onWillPop: () => showExitPopup(context), //call function on back button press
        child: child,
      );
    }
    return child;
  }

  Future<bool> showExitPopup(BuildContext context) async {
    bool isRemoveAds = await PreferencesUtils.getBool("REMOVE_ADS", defaultValue: false);
    if (isRemoveAds||nativeAdUnit.isEmpty) {
      return await UiUtils.showAlertDialog(context,
              content: LanguagesUtils.getString("exit_app_msg", 'Do you want to exit an App?'),
              title: LanguagesUtils.getString("Confirm", 'Confirm'),
              actions: [
                ElevatedButton(
                  style: ButtonStyle(backgroundColor: WidgetStateProperty.all(T.getColors(context).secondary)),
                  onPressed: () => Navigator.of(context).pop(true),
                  //return true when click on "Yes"
                  child: Text(LanguagesUtils.getString("Yes", "Yes")),
                ),
                ElevatedButton(
                  onPressed: () => Navigator.of(context).pop(false),
                  //return false when click on "NO"
                  child: Text(LanguagesUtils.getString("No", 'No')),
                ),
              ]) ??
          false;
    }

    await QExitLib.showDialogConfirmExit(nativeAdUnit);
    return false;
  }

// return await showDialog(
//       //show confirm dialogue
//       //the return value will be from "Yes" or "No" options
//       context: context,
//       builder: (context) => AlertDialog(
//         // title: Text('Exit App'),
//         content: Text(LanguagesUtils.getString("exit_app_msg", 'Do you want to exit an App?')),
//         actions: [
//           ElevatedButton(
//             onPressed: () => Navigator.of(context).pop(false),
//             //return false when click on "NO"
//             child: Text(LanguagesUtils.getString("No", 'No')),
//           ),
//           ElevatedButton(
//             onPressed: () => Navigator.of(context).pop(true),
//             //return true when click on "Yes"
//             child: Text(LanguagesUtils.getString("Yes", "Yes")),
//           ),
//         ],
//       ),
//     ) ??
//     false; //if showDialouge had returned null, then return false
}
