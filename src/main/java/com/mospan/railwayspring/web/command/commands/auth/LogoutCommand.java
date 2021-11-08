package com.mospan.railwayspring.web.command.commands.auth;

import com.mospan.railwayspring.web.command.Command;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {

    private static final Logger logger = Logger.getLogger(LogoutCommand.class);

    @Override
    public RedirectView execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("logging out");
        String locale = (String) request.getSession().getAttribute("defaultLocale");
        request.getSession().invalidate();
        request.getSession().setAttribute("defaultLocale", locale);
        return new RedirectView(request.getContextPath() + "/auth/login");
    }
}
