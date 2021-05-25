package com.example.schoolapp.domain.req;

import lombok.Data;

@Data
public class UserRoleUpdateRequest {
    private Long userId;
    private Long roleId;
}
