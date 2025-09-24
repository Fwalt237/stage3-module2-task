package com.mjc.school.controller.implementation;

import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.controller.interfaces.BaseController;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.implementation.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorDtoRequest, AuthorDtoResponse,Long> {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    @CommandHandler("6")
    public List<AuthorDtoResponse> readAll() {
        return authorService.readAll();
    }

    @Override
    @CommandHandler("7")
    public AuthorDtoResponse readById(@CommandParam("authorId") Long authorId) {
        return authorService.readById(authorId);
    }

    @Override
    @CommandHandler("8")
    public AuthorDtoResponse create(@CommandBody AuthorDtoRequest request) {
        return authorService.create(request);
    }

    @Override
    @CommandHandler("9")
    public AuthorDtoResponse update(@CommandBody AuthorDtoRequest request) {
        return authorService.update(request);
    }

    @Override
    @CommandHandler("10")
    public boolean deleteById(@CommandParam("authorId") Long authorId) {
        return authorService.deleteById(authorId);
    }
}
