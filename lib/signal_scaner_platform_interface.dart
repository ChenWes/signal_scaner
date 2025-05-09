import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'signal_scaner_method_channel.dart';

abstract class SignalScanerPlatform extends PlatformInterface {
  /// Constructs a SignalScanerPlatform.
  SignalScanerPlatform() : super(token: _token);

  static final Object _token = Object();

  static SignalScanerPlatform _instance = MethodChannelSignalScaner();

  /// The default instance of [SignalScanerPlatform] to use.
  ///
  /// Defaults to [MethodChannelSignalScaner].
  static SignalScanerPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [SignalScanerPlatform] when
  /// they register themselves.
  static set instance(SignalScanerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
