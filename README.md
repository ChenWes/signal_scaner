# signal_scaner

D80 信号板，信号接收Flutter插件，内部项目使用

## 应用调用插件方式

![](./signal_scaner.png)

* 应用端向插件端发送[打开串口]方法调用，这时会打开串口设备并开启线程读取串口中的数据，并同步串口设备时间
* 第一步成功后，通过实现接口，调用CallBackIOData向eventChannel发送json数据
* 应用端监听[receiveStream]，完成数据接收


## 插件使用说明

安装插件 (pubspec.yaml)
```
  signal_scaner:
    git:
      url: https://github.com/ChenWes/signal_scaner
      ref: main
```


引用插件
```
package:signal_scaner/signal_scaner.dart
```


使用插件（首先打开设备，监听设备返回值）
```
      floatingActionButton: FloatingActionButton(
        onPressed: (){
          // 打开
          await SignalScaner.openDevice;

          // 不声明变量获取回调函数
          SignalScaner.receiveStream.listen((event) {
            print("返回的数据卡数据" + event.toString());

            // 返回数据
            setState(() {
              _deviceValue = _deviceValue + "=>" + event.toString();
            });

          }, onError: (error) {
            print(error.toString());
          });
        },
        tooltip: 'Start',
        child: const Icon(Icons.add),
      ),
```
## 特别注意

修改android/settings.gradle，在末尾增加以下内容，因为插件的SO文件需要打包至Flutter项目中
```
//在android studio调试时将so文件打包
gradle.beforeProject({ project->
    if (project.hasProperty("target-platform") && !project.getProperty("target-platform").split(",").contains("android-arm")) {
        project.setProperty("target-platform", "android-arm")
    }
})
```
