package com.hiring.hiring.auth.dto;

import java.util.List;
import java.util.Set;

@Value
public class UserInfo {
    private String id, displayName, email;
    private List<String> roles;
    private Set<String> following;
    private Set<String> followers;
}
