package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {

    @Id
    @ExcelIgnore
    private String id;
    @Excel(name = "专辑名", needMerge = true)
    private String title;
    @Excel(name = "集数", needMerge = true)
    private Integer count;
    @Excel(name = "配图", type = 2, needMerge = true, width = 20, height = 15)
    private String coverImg;
    @Excel(name = "评级", needMerge = true)
    private Integer score;
    @Excel(name = "作者", needMerge = true)
    private String author;
    @Excel(name = "翻译者", needMerge = true)
    private String broadcast;
    @Excel(name = "简介", needMerge = true, width = 20)
    private String brief;
    @Excel(name = "出版时间", format = "YYYY年MM月dd日", width = 20, needMerge = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date pubDate;
    @ExcelCollection(name = "章节详情")
    private List<Chapter> children;
}
