package com.mospan.railwayspring.controller;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

@WebServlet(value = "/language")
public class LanguageController extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LanguageController.class);

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession();
            String fmtLocale = "javax.servlet.jsp.jstl.fmt.locale";
            String defaultLocale = "defaultLocale";

            Config.set(session, fmtLocale, request.getParameter("language"));
            logger.info("setting locale: " + request.getParameter("language"));
            session.setAttribute(defaultLocale, request.getParameter("language"));

            String referer = request.getHeader("Referer");
            response.sendRedirect(referer);
        }
}
