package util;


public class MessageUtil {
	
	public static final String TEXT = "text";
	public static final String NEWS = "news";
    /**
     * 要回复的消息
     * @param fromUser 发送方
     * @param toUser 接收方
     * @param content 回复给用户的内容
     * @return 整理好的XML文本
     * */
    public static String setMessage(String toUser,String fromUser,Long createTime, String msgType, String content){
    	
    	
 
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
    
    /**
     * 通过消息类型获取消息模板
     * @param msgType
     * @return
     */
    private String getMessageModel(String msgType) {
    	String model = "";
    	if (msgType.equals(NEWS)) {
    		model = "<xml>\r\n" + 
    				"  <ToUserName><![CDATA[%s]]></ToUserName>\r\n" + 
    				"  <FromUserName><![CDATA[%s]]></FromUserName>\r\n" + 
    				"  <CreateTime>12345678</CreateTime>\r\n" + 
    				"  <MsgType><![CDATA[%s]]></MsgType>\r\n" + 
    				"  <ArticleCount>1</ArticleCount>\r\n" + 
    				"  <Articles>\r\n" + 
    				"    <item>\r\n" + 
    				"      <Title><![CDATA[%s]]></Title>\r\n" + 
    				"      <Description><![CDATA[%s]]></Description>\r\n" + 
    				"      <PicUrl><![CDATA[%s]]></PicUrl>\r\n" + 
    				"      <Url><![CDATA[%s]]></Url>\r\n" + 
    				"    </item>\r\n" + 
    				"  </Articles>\r\n" + 
    				"</xml>";
    	}
    	
    	return model;
    }
}
