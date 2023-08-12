package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static exercise.Data.getCompanies;

public class CompaniesServlet extends HttpServlet {

    public static String parameter = "search";
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        // BEGIN
        PrintWriter out = response.getWriter();
        List<String> companies = getCompanies();
        Optional<String> parameterContent = Optional.ofNullable(request.getParameter(parameter));
        if (parameterContent.isPresent()) {
            String content = parameterContent.get();
            List<String> result = companies.stream()
                    .filter(Objects::nonNull)
                    .filter(c -> c.contains(content))
                    .toList();

            if (!result.isEmpty()) {
                result.forEach(out::println);
            } else {
                out.println("Companies not found");
            }
            return;
        }

        companies.forEach(out::println);
        // END
    }
}
