package com.LDLS.Litigation.Project.Authentication.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class EntityResponse<T> {
    private String message;
    private T entity;
    private Integer statusCode;
}
