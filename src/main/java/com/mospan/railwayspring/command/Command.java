package com.mospan.railwayspring.command;

import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    RedirectView execute(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
