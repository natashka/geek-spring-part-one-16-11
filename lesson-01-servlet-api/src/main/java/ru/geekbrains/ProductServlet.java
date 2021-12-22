package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter wr = resp.getWriter();
        if (req.getParameter("id") != null) {
            // TODO: My realization
            long id = Long.parseLong(req.getParameter("id"));
            if (id <= productRepository.findAll().size()) {
                Product product = productRepository.findById(Long.parseLong(req.getParameter("id")));
                wr.println("<h1>" + "Product with name: " + product.getName() + "</h1>");
            } else {
                wr.println("<h1>" + "There is no product with id="  + req.getParameter("id") + "</h1>");
            }
        } else if (req.getPathInfo() == null || req.getPathInfo().equals("/")) {
            wr.println("<table>");

            wr.println("<tr>");
            wr.println("<th>Id</th>");
            wr.println("<th>Name</th>");
            wr.println("</tr>");

            for (Product product : productRepository.findAll()) {
                wr.println("<tr>");
                Long id = product.getId();
                wr.println("<td>" + id + "</td>");
                wr.println("<td><a href='product?id=" + id.toString() + "'>" + product.getName() + "</a></td>");
                // TODO <a href='product?id=12'></a>
                wr.println("</tr>");
            }
            wr.println("</table>");
        }
    }
}
