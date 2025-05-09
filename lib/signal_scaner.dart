
import 'package:flutter/services.dart';

import 'signal_scaner_platform_interface.dart';

class SignalScaner {
  static const MethodChannel _channel = MethodChannel('signal_scaner');
  static const EventChannel _eventChannel = EventChannel('signal_scaner/event');
  static late Stream _eventStream;

  Future<String?> getPlatformVersion() {
    return SignalScanerPlatform.instance.getPlatformVersion();
  }

  /// 打开设备
  static Future<bool?> get openDevice async {
    final bool? result = await _channel.invokeMethod('openDevice');
    return result;
  }

  /// Stream(Event) coming from Android
  /// 调用数据流
  static Stream get receiveStream {
    _eventStream = _eventChannel
        .receiveBroadcastStream()
        .map<dynamic>((dynamic value) => value);
    return _eventStream;
  }
}
