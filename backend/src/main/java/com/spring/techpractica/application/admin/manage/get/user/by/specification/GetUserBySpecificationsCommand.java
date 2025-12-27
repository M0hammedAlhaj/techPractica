package com.spring.techpractica.application.admin.manage.get.user.by.specification;

public record GetUserBySpecificationsCommand(String userName,String role,
                                             int page,int size) {
}
