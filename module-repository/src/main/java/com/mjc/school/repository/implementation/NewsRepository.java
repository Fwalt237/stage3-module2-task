package com.mjc.school.repository.implementation;

import com.mjc.school.repository.interfaces.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.data.NewsDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {

    private final NewsDatasource newsDatasource;

    @Autowired
    public NewsRepository(NewsDatasource newsDatasource) {
        this.newsDatasource = newsDatasource;
    }

    @Override
    public List<NewsModel> readAll() {
        return newsDatasource.getNewsList();
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        return newsDatasource.getNewsList().stream()
                .filter(news -> news.getId().equals(id)).findFirst();
    }

    @Override
    public NewsModel create(NewsModel entity) {
        List<NewsModel> newsList = newsDatasource.getNewsList();
        newsList.sort(Comparator.comparing(NewsModel::getId));
        if (!newsList.isEmpty()) {
            entity.setId(newsList.get(newsList.size() - 1).getId() + 1);
        } else{
            entity.setId(1L);
        }
        newsList.add(entity);
        return entity;
    }

    @Override
    public NewsModel update(NewsModel entity) {
        NewsModel updatedEntity = readById(entity.getId()).get();
        updatedEntity.setTitle(entity.getTitle());
        updatedEntity.setContent(entity.getContent());
        updatedEntity.setLastUpdatedDate(entity.getLastUpdatedDate());
        updatedEntity.setAuthorId(entity.getAuthorId());
        return updatedEntity;
    }

    @Override
    public boolean deleteById(Long id) {
        List<NewsModel> deleteList = new ArrayList<>();
        NewsModel newsModel = newsDatasource.getNewsList().stream()
                .filter(news -> news.getId().equals(id)).findFirst().get();

        deleteList.add(newsModel);
        return newsDatasource.getNewsList().removeAll(deleteList);
    }

    @Override
    public boolean existById(Long id) {
        return newsDatasource.getNewsList().stream().anyMatch(news -> news.getId().equals(id));
    }
}
