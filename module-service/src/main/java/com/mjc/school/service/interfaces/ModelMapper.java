package com.mjc.school.service.interfaces;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface ModelMapper {

    List<NewsDtoResponse> newsModelListToDtoList(List<NewsModel> newsModelList);
    List<AuthorDtoResponse> authorModelListToDtoList(List<AuthorModel> authorModelList);

    @Mapping(source = "id", target = "newsId")
    NewsDtoResponse newsModelToDto(NewsModel newsModel);

    @Mapping(source = "id", target = "authorId")
    AuthorDtoResponse authorModelToDto(AuthorModel authorModel);


    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdatedDate", ignore = true)
    })
    NewsModel newsDtoToModel(NewsDtoRequest newsModelRequest);

    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdatedDate", ignore = true)
    })
    AuthorModel authorDtoToModel(AuthorDtoRequest authorModelRequest);
}
