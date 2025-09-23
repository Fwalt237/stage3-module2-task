package com.mjc.school.service.aspect;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.validator.Validator;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NewsAspect {

    private final Validator validator;

    @Autowired
    public NewsAspect(Validator validator) {
        this.validator = validator;
    }

    @Before("execution(* com.mjc.school.service.implementation.NewsService.readById(*))" +
            " && args(id))")
    public void beforeNewsReadById(Long id) {
        validator.validateNewsId(id);
    }




    @Before("execution(* com.mjc.school.service.implementation.NewsService.create(*))" +
            " && args(createRequest))")
    public void beforeNewsCreate(NewsDtoRequest createRequest) {
        validator.validateNewsDto(createRequest);
    }




    @Before("execution(* com.mjc.school.service.implementation.NewsService.update(*))" +
            " && args(updateRequest))")
    public void beforeNewsUpdate(NewsDtoRequest updateRequest) {
        validator.validateNewsId(updateRequest.newsId());
        validator.validateNewsDto(updateRequest);
    }




    @Before("execution(* com.mjc.school.service.implementation.NewsService.deleteById(*))" +
            " && args(id))")
    public void beforeNewsDeleteById(Long id) {
        validator.validateNewsId(id);
    }




}
