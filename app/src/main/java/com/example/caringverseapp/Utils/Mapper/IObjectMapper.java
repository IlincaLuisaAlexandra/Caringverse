package com.example.caringverseapp.Utils.Mapper;

import org.json.JSONObject;

public interface IObjectMapper<T> {
    T map(JSONObject obj);
}
