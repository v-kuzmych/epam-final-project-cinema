package com.vasilisa.cinema.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

public class SavePosterFileCommand implements Command{
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        Part filePart = null;
        try {
            filePart = request.getPart("file");
            String fileName = filePart.getSubmittedFileName();

            for (Part part : request.getParts()) {
                part.write("/assets/img/posters/" + fileName);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        return null;
    }
}
