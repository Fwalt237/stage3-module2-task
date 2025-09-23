package com.mjc.school.repository.model.data;

import com.mjc.school.repository.model.AuthorModel;
import org.springframework.stereotype.Component;

import static com.mjc.school.repository.utils.Utils.getRandomContentByFilePath;
import static com.mjc.school.repository.utils.Utils.getRandomDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorDatasource {

    private static final String AUTHORS_FILE_NAME = "authors";
    private final List<AuthorModel> authorList = new ArrayList<>();

    public AuthorDatasource(){init();}

    private void init(){
        for (long i = 1; i <= 20; i++) {
            LocalDateTime date = getRandomDate();
            authorList.add(new AuthorModel(i, getRandomContentByFilePath(AUTHORS_FILE_NAME),date,date ));
        }
    }

    public List<AuthorModel> getAuthorList(){return authorList;}
}
