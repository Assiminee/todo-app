package com.toDoList.service;


import com.toDoList.model.Member;
import com.toDoList.model.User;
import com.toDoList.repository.MemberRepository;
import com.toDoList.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MemberServiceImpl implements MemberService {

    private TodoListRepository todoListRepository;

    private MemberRepository memberRepository;

    private UserService userService;

    @Autowired
    public MemberServiceImpl(TodoListRepository todoListRepository, MemberRepository memberRepository,
                             UserService userService) {
        super();
        this.todoListRepository = todoListRepository;
        this.memberRepository = memberRepository;
        this.userService = userService;
    }

    @Override
    public void deleteMember(String jwt, UUID todoListId, UUID memberId) throws Exception {

        User loggedInUser = userService.getProfile(jwt);

        // Verify ownership
        if (!todoListRepository.isOwner(todoListId, loggedInUser.getId())) {
            throw new Exception("You are not authorized to delete members from this TodoList.");
        }

        Member member = memberRepository.findMemberById(memberId);

        if (member.getUser().getId() == loggedInUser.getId()) {
            throw new Exception("You are owner of this list. you cant delete this member.");
        }
        
        memberRepository.deleteById(memberId);

    }
}
