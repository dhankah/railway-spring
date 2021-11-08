package com.mospan.railwayspring.web.filter;

import com.mospan.railwayspring.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(value = {"/stations/*", "/routes/*"})
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.isAdmin()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendError(403, "Forbidden");
        }
    }
}
