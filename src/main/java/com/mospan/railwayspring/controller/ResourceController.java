package com.mospan.railwayspring.controller;

import com.mospan.railwayspring.model.Entity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public abstract class ResourceController extends HttpServlet {
    private static final String ACTION_CREATE = "create";
    private static final String ACTION_EDIT = "edit";


    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (req.getParameter("_method") != null) {
            method = req.getParameter("_method");
        }
        method = method.toUpperCase();

        String path = req.getPathInfo();
        String[] parts = {};

        if (path != null) {
            path = path.substring(1);

            if (!path.isEmpty()) {
                parts = path.split("/");
            }
        }

        String id = null;
        String action = null;

        if (parts.length > 1) {
            id = parts[0];
            action = parts[1];
        } else if (parts.length > 0) {
            if (parts[0].equals(ACTION_CREATE)) {
                action = parts[0];
            } else {
                id = parts[0];
            }
        }
        Entity entity = null;


        if (!"page".equals(action)) {
            entity = (id != null) ? this.findModel(id) : null;
            if (id != null && entity == null) {
                resp.sendError(404, "Not found");
                return;
            }
        }

        switch (method) {
            case "GET":
                if (action == null) {
                    if (id != null) {
                        this.view(entity, req, resp);
                    } else {
                        this.list(req, resp);
                    }
                    return;
                }
                switch (action) {
                    case ACTION_CREATE:
                        this.create(req, resp);
                        return;
                    case ACTION_EDIT:
                        this.edit(entity, req, resp);
                        return;
                    case "choose" :
                        this.choose(entity, req, resp);
                        return;
                    case "change_password" :
                        this.changePassword(entity, req, resp);
                        return;
                    case "route_info" :
                        this.routeInfo(entity, req, resp);
                        return;
                    case "page" :
                        this.goToPage(Long.parseLong(id), req, resp);
                        return;
                    case "reset_password" :
                        this.resetPassword(req, resp);
                        return;
                    case "reset_password_page" :
                        this.resetPasswordPage(req, resp);
                        return;
                }

                break;
            case "POST":
                this.store(req, resp);
                return;
            case "PUT":
                this.update(entity, req, resp);
                return;
            case "DELETE":
                this.delete(entity, req, resp);
                return;
        }

        super.service(req, resp);
    }


    abstract Entity findModel(String id);

    protected void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }

    protected void view(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }

    protected void update(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }

    protected void edit(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }

    protected void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }

    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }

    protected void delete(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }

    protected void choose(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }

    protected void changePassword(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }

    protected void routeInfo(Entity entity, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }

    protected void goToPage(long id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }

    protected void resetPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }

    protected void resetPasswordPage( HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(404, "Not found");
    }
}