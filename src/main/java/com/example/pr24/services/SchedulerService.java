package com.example.pr24.services;

import com.example.pr24.dao.AuthorDAO;
import com.example.pr24.dao.BookDAO;
import com.example.pr24.models.Author;
import com.example.pr24.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@ManagedResource(objectName = "CurseMBeans:category=MBeans,name=SchedulerService")
public class SchedulerService {
    @Value("C:\\Users\\simba\\Desktop\\Curse\\pr24\\dataFolder")
    private String pathToDataFolder;

    private final FileService fileService;

    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;

    @Autowired
    public SchedulerService(FileService fileService, BookDAO bookDAO, AuthorDAO authorDAO) {
        this.fileService = fileService;
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
    }

    @Scheduled(fixedRate = 1800000)
    @ManagedOperation
    public void saveDataFromDBToFolder() {
        fileService.deleteAllFilesFolder(pathToDataFolder);

        StringBuilder stringAuthors = new StringBuilder();
        for (Author author: authorDAO.findAll()) {
            stringAuthors.append(author.toString()).append("\n");
        }
        fileService.writeToFile(pathToDataFolder + "\\author.txt", stringAuthors.toString());

        StringBuilder stringBook = new StringBuilder();
        for (Book book: bookDAO.findAll()) {
            stringBook.append(book.toString()).append("\n");
        }
        fileService.writeToFile(pathToDataFolder + "\\book.txt", stringBook.toString());
    }
}
