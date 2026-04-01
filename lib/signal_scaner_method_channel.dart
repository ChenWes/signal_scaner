import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'signal_scaner_platform_interface.dart';

/// An implementation of [SignalScanerPlatform] that uses method channels.
class MethodChannelSignalScaner extends SignalScanerPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('signal_scaner');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>(
      'getPlatformVersion',
    );
    return version;
  }
}
