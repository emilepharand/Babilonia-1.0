package Data.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Models a SQL task.
 *
 * @see SQLDatabase
 */

public class SQLTask {

    private String query;
    private String errorMsg;
    private SQLConnection sqldb;
    private ResultSet rs;
    private Procedure procedure;
    private PreparedStatement pstmt;

    public SQLTask(SQLConnection sqldb) {
        this.sqldb = sqldb;
    }

    public int getNextInt(String col) {
        int resInt = 0;
        try {
            resInt = rs.getInt(col);
        } catch (SQLException e) {
            System.err.println(errorMsg);
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return resInt;
    }

    public boolean getNextBoolean(String col) {
        boolean resBoolean = true;
        try {
            resBoolean = rs.getString(col).equals("true");
        } catch (SQLException e) {
            System.err.println(errorMsg);
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return resBoolean;
    }

    public String getNextString(String col) {
        String resString = null;
        try {
            resString = rs.getString(col);
        } catch (SQLException e) {
            System.err.println(errorMsg);
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return resString;
    }

    public boolean nextRow() {
        boolean res = false;
        try {
            res = rs.next();
        } catch (SQLException e) {
            System.err.println(errorMsg);
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return res;
    }

    public void executeQuery() {
        try {
            pstmt = sqldb.getConnection().prepareStatement(query);
            rs = pstmt.executeQuery();
            procedure.invoke();
        } catch (SQLException e) {
            System.err.println(errorMsg);
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    public void executeUpdate() {
        try {
            pstmt = sqldb.getConnection().prepareStatement(query);
            procedure.invoke();
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(errorMsg);
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

}
