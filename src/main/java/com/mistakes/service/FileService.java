package com.mistakes.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.mistakes.model.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class FileService {

    private static Logger logger = Logger.getLogger(FileService.class.getName());
    private static Font font;

    @PostConstruct
    private void initFont() throws Exception {
        String pathToFont = ResourceUtils.getFile("classpath:ARIALUNI.TTF").getAbsolutePath();
        BaseFont baseFont = BaseFont.createFont(pathToFont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        font = new Font(baseFont, 12, Font.NORMAL);
    }


    public List<User> readUsers(String fileName) throws DocumentException {
        List<User> users = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:" + fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String name;
            while ((name = reader.readLine()) != null) {
                final User user = new User();
                user.setName(name);
                user.setTelephoneNumber(reader.readLine());
                user.setAddress(reader.readLine());
                users.add(user);
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
        return users;
    }

    public void writeUsersCSV(List<User> users, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName +".csv"));
            char c = '"';
            char COMMA = ',';
            for (User user : users) {
                writer.write(c + user.getName() + c + COMMA);

                writer.write(c + user.getAddress() + c + COMMA);

                writer.write(c + user.getTelephoneNumber() + c + "\n");
            }
            writer.close();

        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    public void writeUsersPDF(List<User> users, String fileName)
            throws DocumentException, IOException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName+".pdf"));
        document.open();
        for (User user : users) {
            document.add(new Paragraph(user.getName(), font));
            document.add(new Paragraph(user.getAddress(), font));
            document.add(new Paragraph(user.getTelephoneNumber(), font));
        }
        document.close();
    }
}
