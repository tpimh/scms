package org.golovin.scms;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;

public class DatabaseReader extends HttpServlet {

    final String tablename = "people";

    /**
     * Tries to connect to our db
     *
     * @throws SQLException if something went wrong with the database
     */
    private Statement connectToDatabase() throws SQLException {
        String url = "mysql://localhost:3306/",
               database = "test", login = "test", pass = "test";

        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                throw new SQLException("Driver Not Found! " + ex.getMessage());
            }

            java.sql.Connection con = DriverManager.getConnection(
                                          "jdbc:" + url + database, login, pass);
            return con.createStatement();
        } catch (SQLException ex) {
            throw new SQLException("Error connecting to database! " + ex.getMessage());
        }
    }

    /**
     * Returns list.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void generateList(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {

            String sql = "SELECT\n"
                         + "CONCAT(`fname`, ' ', `lname`) AS `name`, `id`\n"
                         + "FROM `" + tablename + "`\n"
                         + "ORDER BY " + "RAND()" + "\n"
                         + "LIMIT 5000";

            ResultSet rs = connectToDatabase().executeQuery(sql);

            rs.last();
            int max_users = rs.getRow();
            rs.beforeFirst();

            String[][] users = new String[max_users][2];

            for (int i = 0; i < max_users; i++) {
                if (rs.next()) {
                    users[i][0] = rs.getString("name");
                    users[i][1] = rs.getString("id");
                }
            }

            request.setAttribute("users", users);
            request.setAttribute("sql", StringEscapeUtils.escapeHtml4(sql));
            request.getRequestDispatcher("WEB-INF/jsp/list.jsp").forward(request, response);
        } catch (SQLException ex) {
            request.setAttribute("errcode", "Database problem:"
                                 + "<br \\><br \\>SQLException: " + ex.getMessage()
                                 + "<br \\>SQLState: " + ex.getSQLState()
                                 + "<br \\>VendorError: " + ex.getErrorCode());
            request.getRequestDispatcher("WEB-INF/jsp/oops.jsp").forward(request, response);
        }
    }

    /**
     * Returns page.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @param id unique number of row in the database
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void generatePage(HttpServletRequest request, HttpServletResponse response, int id)
    throws ServletException, IOException {

        try {

            String sql = "SELECT\n"
                         + "CONCAT(`fname`, ' ', `lname`) AS `name`, `info`\n"
                         + "FROM `" + tablename + "` WHERE (`id` = '" + id + "') LIMIT 1";

            ResultSet rs = connectToDatabase().executeQuery(sql);

            if (rs.next()) {
                rs.first();

                request.setAttribute("info", StringEscapeUtils.escapeHtml4(rs.getString("info")));
                request.setAttribute("name", rs.getString("name"));
                request.setAttribute("id", id);
                request.setAttribute("sql", StringEscapeUtils.escapeHtml4(sql));

                request.getRequestDispatcher("WEB-INF/jsp/page.jsp").forward(request, response);
            } else {
                request.setAttribute("errcode", "No such page (id" + id + ")");
                request.getRequestDispatcher("WEB-INF/jsp/oops.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            request.setAttribute("errcode", "Database problem:"
                                 + "<br \\><br \\>SQLException: " + ex.getMessage()
                                 + "<br \\>SQLState: " + ex.getSQLState()
                                 + "<br \\>VendorError: " + ex.getErrorCode());
            request.getRequestDispatcher("WEB-INF/jsp/oops.jsp").forward(request, response);
        }
    }

    /**
     * Processes requests for both HTTP and decides which page to show
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        int id = 0;
        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            generateList(request, response);
        } else {
            try {
                id = Integer.valueOf(idStr);
            } catch (NumberFormatException e) {
                // this should never happen
                // normally you'll see a 404 if number was not parsed correctly
                // or "No such page" if database doesn't contain a row with specified id
                request.setAttribute("errcode", e.getMessage());
                request.getRequestDispatcher("WEB-INF/jsp/oops.jsp").forward(request, response);
            }
            generatePage(request, response, id);
        }
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Database reader - parses the user request and returns a page with information from the database";
    }
}
