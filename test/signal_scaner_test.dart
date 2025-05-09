import 'package:flutter_test/flutter_test.dart';
import 'package:signal_scaner/signal_scaner.dart';
import 'package:signal_scaner/signal_scaner_platform_interface.dart';
import 'package:signal_scaner/signal_scaner_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockSignalScanerPlatform
    with MockPlatformInterfaceMixin
    implements SignalScanerPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final SignalScanerPlatform initialPlatform = SignalScanerPlatform.instance;

  test('$MethodChannelSignalScaner is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelSignalScaner>());
  });

  test('getPlatformVersion', () async {
    SignalScaner signalScanerPlugin = SignalScaner();
    MockSignalScanerPlatform fakePlatform = MockSignalScanerPlatform();
    SignalScanerPlatform.instance = fakePlatform;

    expect(await signalScanerPlugin.getPlatformVersion(), '42');
  });
}
