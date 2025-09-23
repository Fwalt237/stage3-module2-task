package com.mjc.school.service.aspect;

import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.validator.Validator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorAspect {

    private final Validator validator;
    private final NewsRepository newsRepository;

    @Autowired
    public AuthorAspect(Validator validator, NewsRepository newsRepository) {
        this.validator = validator;
        this.newsRepository = newsRepository;
    }

    @Before("execution(* com.mjc.school.service.implementation.AuthorService.readById(*))" +
            " && args(id))")
    public void beforeAuthorReadById(Long id) {validator.validateOnlyAuthorId(id);}

    @Before("execution(* com.mjc.school.service.implementation.AuthorService.create(*))" +
            " && args(createRequest))")
    public void beforeAuthorCreate(AuthorDtoRequest createRequest) {
        validator.validateAuthorDto(createRequest);
    }

    @Before("execution(* com.mjc.school.service.implementation.AuthorService.update(*))" +
            " && args(updateRequest))")
    public void beforeAuthorUpdate(AuthorDtoRequest updateRequest) {
        validator.validateOnlyAuthorId(updateRequest.authorId());
        validator.validateAuthorDto(updateRequest);
    }

    @Around("@annotation(com.mjc.school.service.aspect.annotation.OnDelete) && args(id)")
    public Object AroundAuthorDeleteById(ProceedingJoinPoint joinPoint, Long id) throws Throwable {
        validator.validateOnlyAuthorId(id);
        Object bool = joinPoint.proceed();
        newsRepository.readAll().removeIf(news -> news.getAuthorId().equals(id));
        return bool;
    }

}
