package com.mospan.railwayspring.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(value = {"/auth/logout", "/cabinet", "/tickets/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getSession().getAttribute("user") != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect(request.getContextPath() + "/auth/login");
        }
    }
}
