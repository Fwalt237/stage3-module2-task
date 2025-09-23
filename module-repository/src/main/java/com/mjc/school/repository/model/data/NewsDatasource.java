package com.mjc.school.repository.model.data;

import com.mjc.school.repository.model.NewsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mjc.school.repository.utils.Utils.getRandomContentByFilePath;
import static com.mjc.school.repository.utils.Utils.getRandomDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class NewsDatasource {

    private static final String CONTENT_FILE_NAME = "content";
    private static final String NEWS_FILE_NAME = "news";
    private final AuthorDatasource authorDatasource;
    private final List<NewsModel> newsList= new ArrayList<>();

    @Autowired
    public NewsDatasource(AuthorDatasource authorDatasource){
        this.authorDatasource = authorDatasource;
        init();
    }

    private void init() {
        Random random = new Random();
        for (long i = 1; i <= 20; i++) {
            LocalDateTime date = getRandomDate();
            newsList.add(
                    new NewsModel(
                            i,
                            getRandomContentByFilePath(NEWS_FILE_NAME),
                            getRandomContentByFilePath(CONTENT_FILE_NAME),
                            date,
                            date,
                            authorDatasource.getAuthorList().get(random.nextInt(authorDatasource.getAuthorList().size())).getId()));

        }
    }
    public List<NewsModel> getNewsList() {return newsList;}
}
