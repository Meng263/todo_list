package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        UserRepository repository = UserRepository.getInstance();
        User user = repository.save(new User(req.getParameter("name"),
                req.getParameter("email"),
                req.getParameter("password")));
        if (user.getId() == 0) {
            onError(req, resp, "Ошибка при создании пользователя");
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    private void onError(HttpServletRequest req, HttpServletResponse resp, String message) throws ServletException, IOException {
        req.setAttribute("error", message);
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }
}
