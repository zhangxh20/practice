package org.dubbo.bean;

import lombok.Data;

@Data
public class JsonResponse {

    private int code;
    
    private String msg;
    
    private Object data;

    
    public static JsonResponse success() {
        JsonResponse json = new JsonResponse();
        json.setCode(0);
        json.setMsg("success");
        return json;
    }
    
    public static JsonResponse error() {
        JsonResponse json = new JsonResponse();
        json.setCode(1);
        json.setMsg("error");
        return json;
    }
    
    
}
