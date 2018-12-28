package com.baizhi;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Test
    public void upLoad() throws FileNotFoundException {
        File file = new File("f:/15.jpg");
        StorePath storePath = fastFileStorageClient.uploadFile(new FileInputStream(file), file.length(), FilenameUtils.getExtension("f:7.jpg"), null);
        System.out.println(storePath);
    }

    @Test
    public void downLoad() throws IOException {
        byte[] bytes = fastFileStorageClient.downloadFile("group2", "M00/00/00/wKjziVwl5p2AWBpIAAAP-hTF5Tc353.jpg", new DownloadByteArray());
        FileOutputStream fileOutputStream = new FileOutputStream("e:/图片.jpg");
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }
}
