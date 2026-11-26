package tn.iset;

import java.sql.*;
import java.util.*;

public class StudentDao {
    private final Connection cn;

    public StudentDao(String url) throws SQLException {
        cn = DriverManager.getConnection(url, "sa", "");
        try (Statement st = cn.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS student(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) NOT NULL)");
        }
    }

    public void add(String name) throws SQLException {
        try (PreparedStatement ps = cn.prepareStatement("INSERT INTO student(name) VALUES(?)")) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    public List<String> findAll() throws SQLException {
        List<String> list = new ArrayList<>();
        try (Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT name FROM student ORDER BY id")) {
            while (rs.next()) list.add(rs.getString(1));
        }
        return list;
    }
}
