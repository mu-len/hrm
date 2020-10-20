package com.jdbc.hrm.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DButil<T> {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hrm", "root", "0701");
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection con) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (null != ps) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (null != con) {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public List<T> selects(String sql, Object... obj) {
        List<T> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = getConn();
            ps = con.prepareStatement(sql);
            if (obj.length > 0) {
                for (int i = 0; i < obj.length; i++) {
                    ps.setObject(i + 1, obj[i]);
                }
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(getEmtity(rs));
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(rs, ps, con);
        }
        return null;
    }

    public boolean updates(String sql, Object... obj) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = getConn();
            ps = con.prepareStatement(sql);
            if (obj.length > 0) {
                for (int i = 0; i < obj.length; i++) {
                    ps.setObject(i + 1, obj[i]);
                }
            }
            int i = ps.executeUpdate();
            if(i>0){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            close(null,ps,con);
        }
        return false;
    }

    public abstract T getEmtity(ResultSet rs) throws SQLException;
}
