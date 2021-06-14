package com.epam.project.hotel.filter;

import com.epam.project.hotel.command.CommandContainer;
import com.epam.project.hotel.sql.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebFilter(filterName = "AppFilter", urlPatterns = "/controller", dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class AppFilter implements Filter {
    private static final Logger log = LogManager.getLogger(AppFilter.class);

    private final Map<String, List<String>> map = new HashMap<>();
    @Override
    public void init(FilterConfig filterConfig) {
        List<String> links = CommandContainer.getUserCommands();
        map.put("1", links);
        links = CommandContainer.getManagerCommands();
        map.put("2", links);
        links = CommandContainer.getUnknownCommands();
        map.put("UNKNOWN", links);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        User user = (User) request.getSession().getAttribute("user");
        String role;
        if(user != null){
            role = user.getRole();
        }
        else {
            role = "UNKNOWN";
        }
        List<String> commands = map.get(role);
        String command = request.getParameter("command");
        log.info("Role = " + role + " command = " + command);
        if(command == null){
            fc.doFilter(req, resp);
            return;
        }
        if(commands.contains(command)){
            fc.doFilter(req, resp);
            return;
        }
        HttpServletResponse response = (HttpServletResponse) resp;
        response.sendError(403);
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
