package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.data.AuthorDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository implements BaseRepository<AuthorModel,Long> {

    private final AuthorDatasource authorDatasource;

    @Autowired
    public AuthorRepository(AuthorDatasource authorDatasource) {
        this.authorDatasource = authorDatasource;
    }

    @Override
    public List<AuthorModel> readAll() {
        return authorDatasource.getAuthorList();
    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
        return authorDatasource.getAuthorList().stream()
                .filter(author -> author.getId().equals(id)).findFirst();
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        List<AuthorModel> authorList = authorDatasource.getAuthorList();
        authorList.sort(Comparator.comparing(AuthorModel::getId));
        if(!authorList.isEmpty()){
            entity.setId(authorList.get(authorList.size()-1).getId() + 1);
        } else {
            entity.setId(1L);
        }
        authorList.add(entity);
        return entity;
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        AuthorModel updatedEntity = readById(entity.getId()).get();
        updatedEntity.setName(entity.getName());
        updatedEntity.setLastUpdatedDate(entity.getLastUpdatedDate());
        return updatedEntity;
    }

    @Override
    public boolean deleteById(Long id) {
        List<AuthorModel> deleteList = new ArrayList<>();
        AuthorModel authorModel = readById(id).get();
        deleteList.add(authorModel);
        return authorDatasource.getAuthorList().removeAll(deleteList);
    }

    @Override
    public boolean existById(Long id) {
        return authorDatasource.getAuthorList().stream().anyMatch(author -> author.getId().equals(id));
    }
}
