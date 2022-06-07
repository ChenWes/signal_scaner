package com.cf.signal_scaner;

import androidx.annotation.NonNull;


import com.example.sdk_d80.D80;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * SignalScanerPlugin
 */
public class SignalScanerPlugin implements FlutterPlugin, MethodCallHandler, EventChannel.StreamHandler{
   // static { System.loadLibrary("libserial_port"); }
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;

    // 事件通知
    public static EventChannel.EventSink mEventSink;

    // 通过


    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "signal_scaner");
        channel.setMethodCallHandler(this);

        // 声明将有数据返回
        final EventChannel eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "signal_scaner/event");
        eventChannel.setStreamHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case "getPlatformVersion":
                result.success("Android " + android.os.Build.VERSION.RELEASE);
                break;
            case "openDevice":
                try {
                    if (this.openDevice()) {
                        result.success("openDeviceSuccess");

                        //监听信号变化
                        DataReceived dataReceived = new DataReceived();
                        D80.CallBackIOData(dataReceived);
                    } else {
                        throw new Exception("OpenIODevFail");
                    }
                } catch (Exception exception) {
                    result.error("openDeviceFail", "openDeviceFail", exception);
                }
                break;
            case "closeDevice":
                break;
            default:
                result.notImplemented();
                break;
        }
    }



    private boolean openDevice(){
       return D80.OpenIODev();
    }

    private void closeDevice(){
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        mEventSink = events;
    }

    @Override
    public void onCancel(Object arguments) {
        mEventSink = null;
    }
}
