package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;

public interface ArticleService {
    public List<Article> queryMyGuruText(Integer guruId);

    public List<Article> queryOtherGuruText(Integer guruId);
}
