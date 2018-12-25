package com.baizhi.mapper;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AlbumMapper extends Mapper<Album> {
    //专辑，章节
    public List<Album> queryAlbum(@Param("page") Integer page, @Param("rows") Integer rows);

    public List<Album> queryAllAlbum();
}
