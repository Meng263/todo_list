package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.repository.ItemRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

public class ItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final ItemRepository repository = ItemRepository.getInstance();
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        boolean isShowsDone = Boolean.parseBoolean(req.getParameter("shows_done"));
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(gson.toJson(repository.getAll(Map.of("is_shows_done", isShowsDone))));
            writer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final ItemRepository repository = ItemRepository.getInstance();
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Item item = gson.fromJson(req.getReader(), Item.class);
        item.setCreated(new Date());
        repository.save(item);
    }
}
