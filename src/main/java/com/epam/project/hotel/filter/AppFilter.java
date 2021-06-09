package com.epam.project.hotel.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppFilter implements Filter {
    private Map<String, List<String>> map;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        map = new HashMap<>();
        List<String> links = new ArrayList<>();
        links.add("");
        map.put("USER", links);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException {
        fc.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
