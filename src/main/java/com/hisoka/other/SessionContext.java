package com.hisoka.other;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 
 * @author Hinsteny
 * @date 2015年8月11日
 * @copyright: 2015 All rights reserved.
 */
public class SessionContext {

	private static volatile ThreadLocal<HttpServletRequest> requestLocal= new ThreadLocal();
    private static volatile ThreadLocal<HttpServletResponse> responseLocal= new ThreadLocal();
      
   public static HttpServletRequest getRequest() {  
       return (HttpServletRequest)requestLocal.get();  
   }

   public static void setRequest(HttpServletRequest request) {  
       requestLocal.set(request);  
   }  
   
   /**
    * 返回请求的具体访问路径
    * @return
    */
   public static String getFullPath(){
	   HttpServletRequest request = getRequest();
	   StringBuilder fullPath = new StringBuilder();
	   if(request.getProtocol().contains("HTTPS")){
		   fullPath.append("https://");
	   }else{
		   fullPath.append("http://");
	   }
	   
	   fullPath.append(request.getServerName());
	   
	   if(request.getServerPort()!=80){
		   fullPath.append(":").append(request.getServerPort());
	   }
	   fullPath.append(request.getContextPath());
	   return fullPath.toString();
   }

    public static String getContextPath(){
        HttpServletRequest request = getRequest();
        StringBuilder contextPath = new StringBuilder();
        if(request.getProtocol().contains("HTTPS")){
            contextPath.append("https://");
        }else{
            contextPath.append("http://");
        }

        contextPath.append(request.getServerName());
        if(request.getServerPort()!=80){
            contextPath.append(":").append(request.getServerPort());
        }
        return contextPath.toString();
    }
   
   /**
    * 返回web应用的真实路径,此方法只能创建了Session后才能使用
    * @return
    * @see {@link (org.springframework.context.ApplicationContext)}
    */
   public static String getWebAppPath(){
	   try{
		   return URLDecoder.decode(getSession().getServletContext().getRealPath("/"),"UTF-8");
	   }catch(UnsupportedEncodingException e){
		   e.printStackTrace();
		   return "";
	   }
   }

   public static HttpServletResponse getResponse() {  
       return (HttpServletResponse)responseLocal.get();  
   }

   public static void setResponse(HttpServletResponse response) {  
       responseLocal.set(response);  
   }

   public static HttpSession getSession() {
       return (HttpSession)((HttpServletRequest)requestLocal.get()).getSession();  
   }  
	
}
