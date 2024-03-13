package com.smart_home.Authentication.DTO;

import lombok.Builder;

@Builder
public record AuthenticationTokenDTO(
        String token,
        String permission
) {}
