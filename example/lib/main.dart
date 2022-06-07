import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:signal_scaner/signal_scaner.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String? _platformVersion = 'Unknown';
  String _deviceValue = '';
  int i = 0;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion =
          await SignalScaner.platformVersion ?? 'Unknown platform version';

      try {

        // 打开串口
        String? test = await SignalScaner.openDevice;

      } catch (ex) {
        print("打开设备出现错误：" + ex.toString());
      }


      // 不声明变量获取回调函数
      SignalScaner.receiveStream.listen((event) {
        print("返回的数据" + event.toString());

        // 返回数据
        setState(() {
          _deviceValue = _deviceValue + "=>" + event.toString();
        });

      }, onError: (error) {
        print(error.toString());
      });


    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
            child: Column(
          children: [
            Text('Device : $_platformVersion\n'),
            Text('Value : $_deviceValue\n'),
          ],
        )),
      ),
    );
  }
}
