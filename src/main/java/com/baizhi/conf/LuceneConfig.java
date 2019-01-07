package com.baizhi.conf;

import com.baizhi.indexDao.LuceneProductDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LuceneConfig {

    @Bean
    public LuceneProductDao getLuceneChapterDao() {
        return new LuceneProductDao();
    }

}
