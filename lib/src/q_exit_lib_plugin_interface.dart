import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

abstract class QExitLibPlatform extends PlatformInterface {
  QExitLibPlatform() : super(token: _token);

  static final Object _token = Object();

  static QExitLibPlatform _instance = MethodChannelQExitLib();

  static QExitLibPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [QExitLibPlatform] when
  /// they register themselves.
  static set instance(QExitLibPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<bool?> showDialogConfirmExit(String nativeAdUnit);

  Future<bool?> cacheExitAds(String nativeAdUnit);
}

/// An implementation of [QExitLibPlatform] that uses method channels.
class MethodChannelQExitLib extends QExitLibPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('q_exit_libs');

  @override
  Future<bool?> cacheExitAds(String nativeAdUnit) async {
    final result =
        await methodChannel.invokeMethod<bool?>('cacheExitAds', <String, String>{'nativeAdUnit': nativeAdUnit});
    return result;
  }

  @override
  Future<bool?> showDialogConfirmExit(String nativeAdUnit) async {
    final result = await methodChannel
        .invokeMethod<bool?>("showDialogConfirmExit", <String, String>{'nativeAdUnit': nativeAdUnit});
    return result;
  }
}
