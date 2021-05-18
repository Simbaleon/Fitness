package com.example.pr24.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Service
public class FileService {
    public void deleteAllFilesFolder(String path) {
        File folder = new File(path);
        File[] fileList = folder.listFiles();

        if (fileList == null) {
            return;
        }

        for (File myFile : fileList)
            if (myFile.isFile()) myFile.delete();
    }

    public void writeToFile(String fileName, String data) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.print(data);
        writer.close();
    }
}
