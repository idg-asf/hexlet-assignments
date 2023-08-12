package exercise.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import exercise.User;
import org.apache.commons.lang3.ArrayUtils;

public class UsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");

        showUser(request, response, id);
    }

    private List<User> getUsers() throws JsonProcessingException, IOException {
        // BEGIN
        ObjectMapper objectMapper = new ObjectMapper();


        return objectMapper.readValue(new File("src/main/resources/users.json"), new TypeReference<>() {});
        // END
    }

    private void showUsers(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException {

        // BEGIN
        List<User> users = getUsers();
        StringBuilder body = new StringBuilder();
        body.append("""
                <!DOCTYPE html>
                <html lang="ru">
                    <head>
                    </head>
                    <body>
                          <table>
                """);

        for (User user : users) {
            String userId = user.getId();
            String fullName = user.getFirstName() + " " + user.getLastName();
            body.append(String.format("""
                    <tr>
                        <td> %s </td>
                        <td>
                            <a href="/users/%s"> %s </a>
                        </td>
                    </tr>
                    """, userId, userId, fullName));
        }
        body.append("""
                        </table>
                    </body>
                </html>
                """);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(body.toString());
        // END
    }

    private void showUser(HttpServletRequest request,
                         HttpServletResponse response,
                         String id)
                 throws IOException {

        // BEGIN
        List<User> users = getUsers();

        User user = users.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst()
                .orElse(null);

        String body;
        if (user != null) {
            body = String.format("""
                    <!DOCTYPE html>
                    <html lang="ru">
                        <head>
                            <meta charset="UTF-8">
                            <title>Example application | Users</title>
                            <link rel="stylesheet" href="mysite.css">
                        </head>
                        <body>
                            <table>
                                <tr>
                                    <td>id</td>
                                    <td>first name</td>
                                    <td>last name</td>
                                    <td>email</td>
                                </tr>
                                <tr>
                                    <td>%s</td>
                                    <td>%s</td>
                                    <td>%s</td>
                                    <td>%s</td>
                                </tr>
                            </table>
                        </body>
                    </html>
                    """, user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(body);
            return;
        }

        String notFoundResponse = "Not found";
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
        PrintWriter out = response.getWriter();
        out.println(notFoundResponse);

    }
}
