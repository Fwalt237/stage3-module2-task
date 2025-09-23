package com.mjc.school.repository.model;

import java.time.LocalDateTime;
import java.util.Objects;


public class AuthorModel implements BaseEntity<Long>{
    private Long authorId;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdatedDate;

    public AuthorModel(
            Long authorId,
            String name,
            LocalDateTime createDate,
            LocalDateTime lastUpdatedDate) {
        this.authorId = authorId;
        this.name = name;
        this.createDate = createDate;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public Long getId() {return authorId;}
    @Override
    public void setId(Long id) {this.authorId = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public LocalDateTime getCreateDate() {return createDate;}
    public void setCreateDate(LocalDateTime createDate) {this.createDate = createDate;}

    public LocalDateTime getLastUpdatedDate() {return lastUpdatedDate;}
    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {this.lastUpdatedDate = lastUpdatedDate;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof AuthorModel authorModel)) return false;
        return Objects.equals(authorId, authorModel.authorId) &&
                Objects.equals(name, authorModel.name) &&
                Objects.equals(createDate, authorModel.createDate) &&
                Objects.equals(lastUpdatedDate, authorModel.lastUpdatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, name, createDate, lastUpdatedDate);
    }
}
