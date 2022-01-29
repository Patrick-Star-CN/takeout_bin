package com.takeout.intergration;

import jakarta.servlet.http.HttpServletRequest;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;

/**
 * @Auther: Patrick_Star
 * @Date: 2022/1/28 - 01 - 28 - 18:56
 * @Description: com.takeout.intergration
 * @version: 1.0
 */
public class JsonReader {
    public static JSONObject receivePost(HttpServletRequest request) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
        String line = null;
        StringBuffer sb = new StringBuffer();
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        JSONObject json = JSONObject.fromObject(sb.toString());
        return json;
    }

    public JsonReader() {

    }
}
