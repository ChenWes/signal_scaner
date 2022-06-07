package com.cf.signal_scaner;

import static com.alibaba.fastjson.JSON.toJSONString;
import android.os.Handler;
import android.os.Looper;

import com.example.sdk_d80.D80Serial;
import com.example.sdk_d80.MsgRecord;


/**
 * 接收数据接口
 */
public class DataReceived implements D80Serial.D80Interface {


    private Handler mHandler = new Handler(Looper.getMainLooper());

    // D80返回的原有数据格式，需要将它转换成JSON
    private Record record = new Record();

    @Override
    public void CallBackMethod(MsgRecord msgRecord) {

        // 处理日期时间格式
        record.setTime(handleTime(msgRecord.Time));
        // 处理返回数据
        record.setTotalState(msgRecord.TotalState);

        // 数据发送方法
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

        // 年首先处理
        StringBuilder sb = new StringBuilder();
        sb.append((2000 + time[0]) + "-");

        for (int i = 1; i < time.length; i++) {

            //如果是小于10的数字，前面补上0
            if (Integer.parseInt(time[i] + "") < 10) {
                sb.append("0");
            }
            sb.append(time[i]);


            switch (i) {
                case 1:
                    // 月后面加-
                    sb.append("-");
                    break;
                case 2:
                    // 日后面加空格
                    sb.append(" ");
                    break;
                case 3:
                case 4:
                    // 时和分后面加：
                    sb.append(":");
                    break;
                case 5:
                    // 秒后面加.
                    sb.append(".");
                    break;
                case 6:
                    // D80中的方法是返回百分毫秒，如果需要转换成千分毫秒，需要在百分毫秒数字后乘以10，即使有部分精度损失，但大体的数据还在
                    sb.append("0");
                    break;
                default:
                    break;
            }
        }
        return sb.toString();
    }

}
