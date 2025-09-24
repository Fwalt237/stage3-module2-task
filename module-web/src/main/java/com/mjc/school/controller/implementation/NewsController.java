package com.mjc.school.controller.implementation;

import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.controller.interfaces.BaseController;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.implementation.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse,Long> {

    private final NewsService newsService;

   @Autowired
   public NewsController(NewsService newsService) {
       this.newsService = newsService;
   }

    @Override
    @CommandHandler("1")
    public List<NewsDtoResponse> readAll() {
        return newsService.readAll();
    }

    @Override
    @CommandHandler("2")
    public NewsDtoResponse readById(@CommandParam("newsId") Long newsId) {
        return  newsService.readById(newsId);
    }

    @Override
    @CommandHandler("3")
    public NewsDtoResponse create(@CommandBody NewsDtoRequest request) {
        return newsService.create(request);
    }

    @Override
    @CommandHandler("4")
    public NewsDtoResponse update(@CommandBody NewsDtoRequest request) {
        return newsService.update(request);
    }

    @Override
    @CommandHandler("5")
    public boolean deleteById(@CommandParam("newsId") Long newsId) {
       return newsService.deleteById(newsId);
    }
}
