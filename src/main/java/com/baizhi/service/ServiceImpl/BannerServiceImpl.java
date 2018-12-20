package com.baizhi.service.ServiceImpl;

import com.baizhi.entity.Banner;
import com.baizhi.entity.BannerDTO;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerMapper mapper;


    @Override
    public BannerDTO queryAll(Integer page, Integer rows) {
        Banner banner = new Banner();
        BannerDTO dto = new BannerDTO();
        RowBounds bb = new RowBounds((page - 1) * rows, rows);
        List<Banner> banners = mapper.selectByRowBounds(banner, bb);
        Integer count = mapper.selectCount(banner);
        dto.setRows(banners);
        dto.setTotal(count);
        return dto;
    }

    @Override
    public void update(Banner banner) {
        mapper.updateByPrimaryKeySelective(banner);
    }

    @Override
    public void delete(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insert(String title, String description, HttpSession session, MultipartFile file) {
        try {
            ServletContext servlet = session.getServletContext();
            String realPath = servlet.getRealPath("/myimg");
            System.out.println(realPath);
            //目标文件
            Long time = new Date().getTime();
            File descFile = new File(realPath + "/" + time + "-" + file.getOriginalFilename());
            //上传
            file.transferTo(descFile);
            Banner banner = new Banner(null, title, "/myimg/" + time + "-" + file.getOriginalFilename(), "N", new Date(), description);
            mapper.insert(banner);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }


}
