package com.winbons.tech.dao;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.Consts;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.junit.Test;

public class CdMobileTest {
//    private static final String EXAMPLE_CALL_STR = "http://domian.net/relay/call?caller=13712345678&callee=13512345678&maxMin=10&code=1001&auth=123456&orderId=s12deg34";
//    private static final String EXAMPLE_FAILBILL_STR = "http://domian.net/bill/getFailBill?code=1010";
    
    @Test
    public void testCall() throws URISyntaxException, NoSuchAlgorithmException {
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("r.tel10000.net")
                .setPath("/relay/call")
                .setParameter("caller", "13533630193")
                .setParameter("callee", "18078408887")
                .setParameter("maxMin", "3")
                .setParameter("code", "hby00") 
                // huabangyun
                .setParameter("auth", toMd5Str("13533630193", "18078408887", "huabangyun"))
                .setParameter("orderId", "")
                .build();
        System.out.println("uri: " + uri.toString());
        try {
            Content content = Request.Get(uri).execute().returnContent();
            System.out.println(content.asString(Consts.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGetFailBill() throws URISyntaxException, NoSuchAlgorithmException {
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("r.tel10000.net")
                .setPath("/bill/getFailBill")
                .setParameter("code", "hby00") 
                // huabangyun
                .build();
        System.out.println("uri: " + uri.toString());
        try {
            Content content = Request.Get(uri).execute().returnContent();
            System.out.println(content.asString(Consts.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String toMd5Str(String ... ss) throws NoSuchAlgorithmException  {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        String original = "";
        for (String s : ss) {
            md.update(s.getBytes());
            original += s;
        }
        
        byte [] byteArr = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte elem : byteArr) {
//            System.out.print(String.format("%d", elem) + "\t");
//            System.out.print(String.format("%S", elem) + "\t");
//            System.out.print(String.format("%x", elem) + "\t");
//            System.out.println(String.format("%H", elem));
//            System.out.println(String.format("%#16x", elem));
            sb.append(String.format("%02x", elem & 0xff));
        }
        String md5Str = sb.toString();
        System.out.println("original:" + original);
        // 21218cca77804d2ba1922c33e0151105
        System.out.println("digested(hex):" + md5Str);
        
        return md5Str;
    }
    
    @Test
    public void testTmp() {
        try {
            Content content = Request.Get("http://httpbin.org/get").execute().returnContent();
            System.out.println(content.asString(Consts.UTF_8));
            
            toMd5Str("888888");
            
        } catch (IOException | NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
