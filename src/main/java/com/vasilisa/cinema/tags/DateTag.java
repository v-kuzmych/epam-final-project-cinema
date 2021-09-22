package com.vasilisa.cinema.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTag extends TagSupport{
    private static final Logger logger = LogManager.getLogger(DateTag.class);

    @Override
    public int doStartTag() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String date = dateFormatter.format(LocalDateTime.now());
        JspWriter out = pageContext.getOut();
        try {
            out.print(date);
        } catch(IOException e){
            logger.error("Tag exception",e);
        }
        return SKIP_BODY;
    }
}
