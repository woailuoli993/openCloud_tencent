package udp_model;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import com.google.protobuf.ByteString;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

import udp_model.UrlInfo;

public class UdpUrlSec {
    private final static int APPID =  666666666;        // add your APPID
    private final static String SEC_KEY = "ABCDEFG";           // add your SEC_KEY
    private final static String TENCENT_API_URL = "cloud.urlsec.qq.com";
    private final static int TENCENT_API_PROT = 15113;


    public static UrlInfo getSec(String url) throws Exception {
        String str_url = url;

        WXBizMsgCrypt pc = new WXBizMsgCrypt(SEC_KEY);

                                                                //------------- 填充header头 -------------
        Req.ReqPkg.Header.Builder header = Req.ReqPkg.Header.newBuilder();
        header.setAppid(APPID);                                 //查询官方唯一标识符
        header.setEchostr(ByteString.copyFromUtf8("vic1"));     //随机字符串 长度为16位 只接受数字和字母的组合
        header.setIp(1234);                                     //发起请求的客户端ip | 对应整数 （ip于ipv6 字段最少存在一个）
        long currentTime = new Date().getTime();
        header.setTime(currentTime/1000);                       //设置请求时间戳 unixtimestamp
        String sign = UdpUrlSec.MD5(String.valueOf(currentTime/1000)+SEC_KEY);
        sign = sign.toLowerCase().substring(16, 32);
        header.setSign(ByteString.copyFromUtf8(sign));          //请求参数签名， 用于服务器校验
        header.setV(ByteString.copyFromUtf8("1.0"));            //版本号 1.0 返回明文字符串 2.0 返回密文，需用key解密

                                                                //----------请求消息体 urllist ------------
        Req.ReqPkg.ReqInfo.Builder reqInfo = Req.ReqPkg.ReqInfo.newBuilder();
        reqInfo.setDeviceid(ByteString.copyFromUtf8("1"));      // 设备唯一标示
        reqInfo.setId(1);                                       // 查询id
        reqInfo.setUrl(ByteString.copyFromUtf8(str_url));       // 需要识别的 url


                                                                // reInfo 请求加密
        byte[] s = reqInfo.build().toByteArray();
        String miwen = pc.encrypt(s);

        Req.ReqPkg.Builder reqPkg = Req.ReqPkg.newBuilder();
        reqPkg.setHeader(header);                               //填充请求包
        reqPkg.setReqinfo(ByteString.copyFromUtf8(miwen));


        DatagramSocket client = new DatagramSocket();

        InetAddress addr = InetAddress.getByName(TENCENT_API_URL); //获取ip

        DatagramPacket sendPacket
                = new DatagramPacket(reqPkg.build().toByteArray() , reqPkg.build().toByteArray().length , addr , TENCENT_API_PROT);
        client.send(sendPacket);


        byte[] recvBuf = new byte[100];
        DatagramPacket recvPacket
                = new DatagramPacket(recvBuf , recvBuf.length);
        client.receive(recvPacket);

        Rsp.RspPkg rsp = Rsp.RspPkg.parseFrom(Arrays.copyOf(recvPacket.getData(), recvPacket.getLength()));


        client.close();

        UrlInfo ui = new UrlInfo();
        ui.setUrl(rsp.getInfos().getUrl().toStringUtf8());
        ui.setStatus(rsp.getStatus());
        ui.setUrltype(rsp.getInfos().getUrltype());
        ui.setEviltype(rsp.getInfos().getEviltype());

        return ui;



    }


    public final static String MD5(String s) {
	        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	        try {
	            byte[] btInput = s.getBytes();
	            // 获得MD5摘要算法的 MessageDigest 对象
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");
	            // 使用指定的字节更新摘要
	            mdInst.update(btInput);
	            // 获得密文
	            byte[] md = mdInst.digest();
	            // 把密文转换成十六进制的字符串形式
	            int j = md.length;
	            char str[] = new char[j * 2];
	            int k = 0;
	            for (int i = 0; i < j; i++) {
	                byte byte0 = md[i];
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	                str[k++] = hexDigits[byte0 & 0xf];
	            }
	            return new String(str);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	public static void main(String[] args) throws Exception {

        String url = "www.google.com";

        UrlInfo ui = UdpUrlSec.getSec(url);
        System.out.println(ui);



    }
}

