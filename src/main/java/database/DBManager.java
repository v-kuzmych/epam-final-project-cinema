package database;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;


public class DBManager {

    private static final Logger log = Logger.getLogger(DBManager.class.getName());

    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }

    /**
     * Returns a DB connection from the Pool Connections. Before using this method
     * you must configure the Date Source and the Connections Pool in your
     * WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return A DB connection.
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env/");

            // mycinema - the name of data source
            DataSource ds = (DataSource) envContext.lookup("jdbc/mycinema");
            con = ds.getConnection();
        } catch (NamingException ex) {
            log.severe("Cannot obtain a connection from the pool. " + ex.getMessage());
        }
        return con;
    }

    private DBManager() {
    }

    /**
     * Commits and close the given connection.
     *
     * @param con Connection to be committed and closed.
     */
    public void commitAndClose(Connection con) {
        try {
            con.commit();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Rollbacks and close the given connection.
     *
     * @param con Connection to be rollbacked and closed.
     */
    public void rollbackAndClose(Connection con) {
        try {
            con.rollback();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**************** THIS METHOD IS NOT USED IN THE PROJECT *******/
    /**
     * Returns a DB connection. This method is just for a example how to use the
     * DriverManager to obtain a DB connection. It does not use a pool connections
     * and not used in this project. It is preferable to use
     * {@link #getConnection()} method instead.
     *
     * @return A DB connection.
     */
    public Connection getConnectionWithDriverManager() throws SQLException {
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/cinema", "root", "password");
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.setAutoCommit(false);
        return connection;
    }

    /**************************************************************/
    public static void testDb(Connection conn) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                int n = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= n; i++) {
                    System.out.print(":" + rs.getString(i) + ":");
                }
                System.out.println();
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore

                stmt = null;
            }
        }
    }

    public static void main(String[] args) {
        DBManager db = DBManager.getInstance();
        Connection conn = null;
        try {
            conn = db.getConnectionWithDriverManager();
            testDb(conn);
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}

