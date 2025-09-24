package com.mjc.school.service.implementation;

import static com.mjc.school.service.exceptions.ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST;

import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.interfaces.BaseService;
import com.mjc.school.service.interfaces.ModelMapper;
import com.mjc.school.service.aspect.annotation.OnDelete;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AuthorService implements BaseService<AuthorDtoRequest, AuthorDtoResponse,Long> {

    private final AuthorRepository authorRepository;

    private final ModelMapper mapper = Mappers.getMapper(ModelMapper.class);

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {return mapper.authorModelListToDtoList(authorRepository.readAll());}

    @Override
    public AuthorDtoResponse readById(Long authorId) {
        if(authorRepository.existById(authorId)){
            AuthorModel authorModel = authorRepository.readById(authorId).get();
            return mapper.authorModelToDto(authorModel);
        } else{
            throw new NotFoundException(String.format(
                String.valueOf(AUTHOR_ID_DOES_NOT_EXIST.getMessage()), authorId));
        }
    }

    @Override
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        AuthorModel authorModel = mapper.authorDtoToModel(createRequest);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        authorModel.setCreateDate(date);
        authorModel.setLastUpdatedDate(date);
        AuthorModel model = authorRepository.create(authorModel);
        return mapper.authorModelToDto(model);
    }

    @Override
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        if(authorRepository.existById(updateRequest.authorId())){
            AuthorModel authorModel = mapper.authorDtoToModel(updateRequest);
            authorModel.setLastUpdatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            AuthorModel model = authorRepository.update(authorModel);
            return mapper.authorModelToDto(model);
        }else {
            throw new NotFoundException(String.format(
                String.valueOf(AUTHOR_ID_DOES_NOT_EXIST.getMessage()), updateRequest.authorId()));
        }
    }

    @Override
    @OnDelete
    public boolean deleteById(Long authorId) {
        if(authorRepository.existById(authorId)){
            return authorRepository.deleteById(authorId);
        } else {
            throw new NotFoundException(String.format(
                    String.valueOf(AUTHOR_ID_DOES_NOT_EXIST.getMessage()), authorId));
        }
    }
}
