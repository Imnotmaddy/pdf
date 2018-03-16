package com.mistakes.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class FileDownloadController {

    private static final int BUFFER_SIZE = 4096;

    private String filePath = "EnglishFixedNames.csv";

        @GetMapping("/downloadCSV")
            public void doDownload(HttpServletRequest request,
                    HttpServletResponse response) throws IOException {

                // get absolute path of the application
                ServletContext context = request.getServletContext();

                File downloadFile = new File(filePath);
                FileInputStream inputStream = new FileInputStream(downloadFile);

                // get MIME type of the file
                String mimeType = context.getMimeType(filePath);
                if (mimeType == null) {
                    // set to binary type if MIME mapping not found
                    mimeType = "application/octet-stream";
                }
                System.out.println("MIME type: " + mimeType);

                // set content attributes for the response
                response.setContentType(mimeType);
                response.setContentLength((int) downloadFile.length());

                // set headers for the response
                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"",
                        downloadFile.getName());
                response.setHeader(headerKey, headerValue);

                // get output stream of the response
                OutputStream outStream = response.getOutputStream();

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;

                // write bytes read from the input stream into the output stream
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }

                inputStream.close();
                outStream.close();
        }

}
