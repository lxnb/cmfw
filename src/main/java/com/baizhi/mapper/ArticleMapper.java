package com.baizhi.mapper;

import com.baizhi.entity.Article;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleMapper extends Mapper<Article> {
    public List<Article> queryMyGuruText(Integer guruId);

    public List<Article> queryOtherGuruText(Integer guruId);

    public List<Article> queryNewText();

}
