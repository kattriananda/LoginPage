package com.example.login.Response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AuthenticationData<T> {
    private String status;
    private List<String> messages = new ArrayList<>();
    private T payload;
}
