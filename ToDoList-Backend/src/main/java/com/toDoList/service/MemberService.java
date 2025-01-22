package com.toDoList.service;


import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface MemberService {

    public void deleteMember(String jwt, UUID todoListId, UUID memberId) throws Exception;
}
