package com.mjc.school.service.implementation;

import static com.mjc.school.service.exceptions.ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST;

import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.ModelMapper;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.validator.Validator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse,Long> {

    private final NewsRepository newsRepository;
    private final Validator validator;

    private final ModelMapper mapper = Mappers.getMapper(ModelMapper.class);

    @Autowired
    public NewsService(NewsRepository newsRepository, Validator validator) {
        this.newsRepository = newsRepository;
        this.validator = validator;
    }
    @Override
    public List<NewsDtoResponse> readAll() {
        return mapper.newsModelListToDtoList(newsRepository.readAll());
    }

    @Override
    public NewsDtoResponse readById(Long newsId) {
        if(newsRepository.existById(newsId)){
            NewsModel newsModel = newsRepository.readById(newsId).get();
            return mapper.newsModelToDto(newsModel);
        } else {
            throw new NotFoundException(
                String.format(String.valueOf(NEWS_ID_DOES_NOT_EXIST.getMessage()), newsId));
        }
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        NewsModel newsModel = mapper.newsDtoToModel(createRequest);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        newsModel.setCreateDate(date);
        newsModel.setLastUpdatedDate(date);
        NewsModel model = newsRepository.create(newsModel);
        return mapper.newsModelToDto(model);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        if(newsRepository.existById(updateRequest.newsId())){
            NewsModel newsModel = mapper.newsDtoToModel(updateRequest);
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            newsModel.setLastUpdatedDate(date);
            NewsModel model = newsRepository.update(newsModel);
            return mapper.newsModelToDto(model);
        } else {
            throw new NotFoundException(
                String.format(String.valueOf(NEWS_ID_DOES_NOT_EXIST.getMessage()), updateRequest.newsId()));
        }
    }

    @Override
    public boolean deleteById(Long newsId) {
        if(newsRepository.existById(newsId)){
            return newsRepository.deleteById(newsId);
        }else{
            throw new NotFoundException(
                    String.format(String.valueOf(NEWS_ID_DOES_NOT_EXIST.getMessage()), newsId));
        }
    }
}
