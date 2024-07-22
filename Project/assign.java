
// Servlet: - 

import java.io.IOException;

import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

// @WebServlet("/JavaServlet")   

public class JavaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";

    private static final String DB_USER = "your_username";

    private static final String DB_PASSWORD = "your_password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)

            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        String serv = request.getParameter("serv");

        if (serv.equals("insert")) {

            insertStudent(request, out);

        } else if (serv.equals("update")) {

            updateStudent(request, out);

        } else if (serv.equals("retrieve")) {

            retrieveStudent(request, out);

        }

    }

    private void insertStudent(HttpServletRequest request, PrintWriter out) {

        try {

            String name = request.getParameter("name");

            int age = Integer.parseInt(request.getParameter("age"));

            String grade = request.getParameter("grade");

            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {

                String sql = "INSERT INTO student_details (student_name, student_age, student_grade) VALUES (?, ?, ?)";

                try (PreparedStatement statement = conn.prepareStatement(sql)) {

                    statement.setString(1, name);

                    statement.setInt(2, age);

                    statement.setString(3, grade);

                    int rowsInserted = statement.executeUpdate();

                    if (rowsInserted > 0) {

                        out.println("Student information inserted successfully!");

                    } else {

                        out.println("Failed to insert student information.");

                    }

                }

            } catch (SQLException e) {

                e.printStackTrace();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void updateStudent(HttpServletRequest request, PrintWriter out) {

    }

    private void retrieveStudent(HttpServletRequest request, PrintWriter out) {

        try {

            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {

                String sql = "SELECT * FROM student_details";

                try (PreparedStatement statement = conn.prepareStatement(sql)) {

                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {

                        out.println("ID: " + resultSet.getInt("student_id") + "<br>");

                        out.println("Name: " + resultSet.getString("student_name") + "<br>");

                        out.println("Age: " + resultSet.getInt("student_age") + "<br>");

                        out.println("Grade: " + resultSet.getString("student_grade") + "<br>");

                        out.println("<hr>");

                    }

                }

            } catch (SQLException e) {

                e.printStackTrace();

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}