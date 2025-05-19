package com.tzs.antique.common.reponse;


import java.util.HashMap;

public class GymResponse<T> extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    private Integer code;
    private String message;
    private Object data;

    public GymResponse(){

    }
    public GymResponse(Integer code, String message, Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public GymResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public GymResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public GymResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
