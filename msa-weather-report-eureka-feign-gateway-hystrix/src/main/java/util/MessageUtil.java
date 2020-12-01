package util;


public class MessageUtil {
    /**
     * 要回复的消息
     * @param fromUser 发送方
     * @param toUser 接收方
     * @param content 回复给用户的内容
     * @return 整理好的XML文本
     * */
    public static String setMessage(String toUser,String fromUser,int createTime, String msgType, String content){
 
        String str = "<xml>\n" +
                "  <ToUserName><![CDATA[%s]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[%s]]></FromUserName>\n" +
                "  <CreateTime>%d</CreateTime>\n" +
                "  <MsgType><![CDATA[%s]]></MsgType>\n" +
                "  <Content><![CDATA[%s]]></Content>\n" +
                "</xml>\n" +
                "\n";
        return String.format(str, toUser, fromUser, createTime, msgType, content);
    }
}
