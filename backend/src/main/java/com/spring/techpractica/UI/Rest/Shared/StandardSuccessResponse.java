package com.spring.techpractica.UI.Rest.Shared;

import lombok.Builder;

@Builder
public record StandardSuccessResponse<T>(T data,
                                         int status,
                                         String message) {
}