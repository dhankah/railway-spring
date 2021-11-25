package com.mospan.railwayspring.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;


@Controller
public class LanguageController {
    private static final Logger logger = Logger.getLogger(LanguageController.class);

        @GetMapping("/language")
        public RedirectView doGet(HttpServletRequest request) {
            HttpSession session = request.getSession();
            String fmtLocale = "javax.servlet.jsp.jstl.fmt.locale";
            String defaultLocale = "defaultLocale";

            Config.set(session, fmtLocale, request.getParameter("language"));
            logger.info("setting locale: " + request.getParameter("language"));
            session.setAttribute(defaultLocale, request.getParameter("language"));

            String referer = request.getHeader("Referer");
            return new RedirectView(referer);
        }
}
