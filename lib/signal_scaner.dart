import 'dart:async';

import 'package:flutter/services.dart';

class SignalScaner {
  static const MethodChannel _channel = MethodChannel('signal_scaner');
  static const EventChannel _eventChannel = EventChannel('signal_scaner/event');
  static late Stream _eventStream;

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  /// 打开设备
  static Future<String?> get openDevice async {
    final String? result = await _channel.invokeMethod('openDevice');
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
