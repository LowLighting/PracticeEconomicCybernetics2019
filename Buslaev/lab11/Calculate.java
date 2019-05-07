import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@WebServlet("Unnamed")
public class Calculate extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = req.getParameter("Angle");
        Double angle = Double.parseDouble(req.getParameter("Angle"));
        Integer eps = Integer.parseInt(req.getParameter("Epsilon"));
        String func = req.getParameter("Function");
        double res = 0.0;
        try  {
            switch (func) {
                case "sin(x)":
                    res = Math.sin(angle);
                    break;
                case "cos(x)":
                    res = Math.cos(angle);
                    break;
                case "tg(x)":
                    res = Math.tan(angle);
                    break;
                case "ctg(x)":
                    res = 1.0 / Math.tan(angle);
                    break;
            }
            String form = "%." + eps + "g%n";
            String write = String.format(form, res);
            req.setAttribute("answer", write);
        }
        catch (Exception e) {
            req.setAttribute("answer", "Smth wrong");
        }
        req.getRequestDispatcher("result.jsp").forward(req, resp);
    }
}
