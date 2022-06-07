package com.cf.signal_scaner;

import static com.alibaba.fastjson.JSON.toJSONString;

import android.os.Handler;
import android.os.Looper;

import com.example.sdk_d80.D80Serial;
import com.example.sdk_d80.MsgRecord;

import io.flutter.plugin.common.EventChannel;

/**
 * 接收数据接口
 */
public class DataReceived implements D80Serial.D80Interface {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Record record = new Record();

    @Override
    public void CallBackMethod(MsgRecord msgRecord) {
        record.setTime(handleTime(msgRecord.Time));
        record.setTotalState(msgRecord.TotalState);
        onDataReceived(toJSONString(record));
    }

    /**
     * 数据发送
     *
     * @param msgRecord
     */
    private void onDataReceived(final String msgRecord) {
        if (SignalScanerPlugin.mEventSink != null) {

            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    // 通过数据流发送数据
                    SignalScanerPlugin.mEventSink.success(msgRecord);
                }
            });
        }
    }

    /**
     * 返回标准日期格式
     *
     * @param time
     * @return
     */
    private String handleTime(byte[] time) {
        StringBuilder sb = new StringBuilder();
        sb.append((2000 + time[0]) + "-");
        for (int i = 1; i < time.length; i++) {
            if (Integer.parseInt(time[i] + "") < 10) {
                sb.append("0");
            }
            sb.append(time[i]);
            switch (i) {
                case 1:
                    sb.append("-");
                    break;
                case 2:
                    sb.append(" ");
                    break;
                case 3:
                case 4:
                    sb.append(":");
                    break;
                case 5:
                    sb.append(".");
                    break;
                case 6:
                    sb.append("0");
                    break;
                default:
                    break;
            }
        }
        return sb.toString();
    }

}
