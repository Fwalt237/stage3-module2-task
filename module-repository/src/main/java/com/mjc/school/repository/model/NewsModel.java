package com.mjc.school.repository.model;

import java.time.LocalDateTime;
import java.util.Objects;


public class NewsModel implements BaseEntity<Long> {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdatedDate;
    private Long authorId;

    public NewsModel(
            Long id,
            String title,
            String content,
            LocalDateTime createDate,
            LocalDateTime lastUpdatedDate,
            Long authorId
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.authorId = authorId;
    }

    @Override
    public Long getId() {return id;}
    @Override
    public void setId(Long id) {this.id = id;}


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }
    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Long getAuthorId() {
        return authorId;
    }
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsModel newsModel)) return false;
        return Objects.equals(id, newsModel.id) &&
                Objects.equals(title, newsModel.title) &&
                Objects.equals(content, newsModel.content) &&
                Objects.equals(createDate, newsModel.createDate) &&
                Objects.equals(lastUpdatedDate, newsModel.lastUpdatedDate) &&
                Objects.equals(authorId, newsModel.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createDate, lastUpdatedDate, authorId);
    }
}

