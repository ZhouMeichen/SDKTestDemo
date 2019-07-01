【apk使用注意点】
1、使用SDK.apk安装后，在第一次加载应用时由于需要解析资源，页面会等待约5s显示，属正常现象，此时请勿退出应用，耐心等待
2、除cloud端的中文内核（sdk直接返回network abnormal或core is not exist），native端的exam内核（sdk返回core is invalid）无法使用外，其余可正常使用
3、res中有为.g4结尾的选项，需使用.g4（如：eng.snt.g4、eng.wrd.g4、eng.rec.g4）

【FAQ】
1、cloud端如何加入新server、appKey、secretKey和证书？
（1）在res/values/strings.xml中增加新server（如：ws://xx.x.xxx.xxx:port）以及appKey、secretKey对应关系配置
<string-array name="cloudprovision">
	<item name="server">ws://xx.x.xxx.xxx:port</item>
	<item name="appKey">对应appKey</item>
	<item name="secretKey">对应secretKey</item>
	……
</string-array>

（2）在res/values/strings.xml中增加新server选项配置
    <string-array name="cloudserver">
        <item>ws://xx.x.xxx.xxx:port</item>
        ……
    </string-array>

（3）在assets中增加新证书
证书命名规则如下：
“aiengine_cloud_[ip或域名].provision”
如“aiengine_cloud_10.0.200.169.provision”或“aiengine_cloud_cloud.chivox.com.provision”

2、native和native-simp的内核在哪？
native和native-simp的内核均打包在resources.zip中，且resources.zip放于assets文件夹中，可替换（根据需要在代码中修改内核文件的路径）

3、如何查看日志？
在手机根目录的sdcard文件夹下保存了sdk的日志，可使用文件浏览器等工具进行查阅（/sdcard/sdkdemo.log）

4、如何获取native和native-simp下的录音文件？
在手机根目录的sdcard文件夹下保存了.ogg格式的音频文件（/sdcard/xxxxx.ogg）

5、如何获取cloud下的录音文件？
在Result中，会显示cloud端保存的录音文件的网址（.ogg格式），且提供点击超链接进入网页播放器的功能