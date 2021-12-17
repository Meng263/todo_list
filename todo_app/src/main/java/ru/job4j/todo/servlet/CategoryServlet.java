package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final CategoryRepository repository = CategoryRepository.getInstance();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(gson.toJson(repository.getAll()));
            writer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final CategoryRepository repository = CategoryRepository.getInstance();
        Category category = gson.fromJson(req.getReader(), Category.class);
        repository.save(category);
    }
}
