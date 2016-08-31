package com.cdesign.spittr.data.service;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ageev Evgeny on 29.08.2016.
 */
@Service
public class FileStorageService {

    public FileStorageService() {}

    public String saveFile(String fileName, MultipartFile file) throws IOException {
        File f = new File(fileName);
        try {
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(f));
            FileCopyUtils.copy(file.getInputStream(), stream);
            stream.close();
            //saveFile(fileName, f, file.getContentType());
        } finally {
            //f.delete();
        }
        return fileName;
    }

}
