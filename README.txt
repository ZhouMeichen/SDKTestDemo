��apkʹ��ע��㡿
1��ʹ��SDK.apk��װ���ڵ�һ�μ���Ӧ��ʱ������Ҫ������Դ��ҳ���ȴ�Լ5s��ʾ�����������󣬴�ʱ�����˳�Ӧ�ã����ĵȴ�
2����cloud�˵������ںˣ�sdkֱ�ӷ���network abnormal��core is not exist����native�˵�exam�ںˣ�sdk����core is invalid���޷�ʹ���⣬���������ʹ��
3��res����Ϊ.g4��β��ѡ���ʹ��.g4���磺eng.snt.g4��eng.wrd.g4��eng.rec.g4��

��FAQ��
1��cloud����μ�����server��appKey��secretKey��֤�飿
��1����res/values/strings.xml��������server���磺ws://xx.x.xxx.xxx:port���Լ�appKey��secretKey��Ӧ��ϵ����
<string-array name="cloudprovision">
	<item name="server">ws://xx.x.xxx.xxx:port</item>
	<item name="appKey">��ӦappKey</item>
	<item name="secretKey">��ӦsecretKey</item>
	����
</string-array>

��2����res/values/strings.xml��������serverѡ������
    <string-array name="cloudserver">
        <item>ws://xx.x.xxx.xxx:port</item>
        ����
    </string-array>

��3����assets��������֤��
֤�������������£�
��aiengine_cloud_[ip������].provision��
�硰aiengine_cloud_10.0.200.169.provision����aiengine_cloud_cloud.chivox.com.provision��

2��native��native-simp���ں����ģ�
native��native-simp���ں˾������resources.zip�У���resources.zip����assets�ļ����У����滻��������Ҫ�ڴ������޸��ں��ļ���·����

3����β鿴��־��
���ֻ���Ŀ¼��sdcard�ļ����±�����sdk����־����ʹ���ļ�������ȹ��߽��в��ģ�/sdcard/sdkdemo.log��

4����λ�ȡnative��native-simp�µ�¼���ļ���
���ֻ���Ŀ¼��sdcard�ļ����±�����.ogg��ʽ����Ƶ�ļ���/sdcard/xxxxx.ogg��

5����λ�ȡcloud�µ�¼���ļ���
��Result�У�����ʾcloud�˱����¼���ļ�����ַ��.ogg��ʽ�������ṩ��������ӽ�����ҳ�������Ĺ���