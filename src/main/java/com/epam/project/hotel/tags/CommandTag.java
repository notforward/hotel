package com.epam.project.hotel.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Simple tag for command pattern realization
 */
public class CommandTag extends SimpleTagSupport {

    private String command;

    @Override
    public void doTag() throws IOException {
        JspWriter writer = getJspContext().getOut();
        writer.write("<input type='hidden' name='command' value='" + command + "'/>");
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
