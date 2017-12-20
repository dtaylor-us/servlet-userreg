package dao.mysql;

import util.DBException;
import dao.UserDAO;
import model.UserVO;
import org.apache.log4j.Logger;
import constants.GlobalConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl extends MySQL implements UserDAO {

    private final Logger LOGGER = Logger.getLogger(UserDAOImpl.class.getName());

    public UserVO selectUSER(String userId) throws DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        UserVO
                vo = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(MySQLProcedures.SELECT_USER);
            ps.setString(1, userId);
            res = ps.executeQuery();
            vo = getUserVO(res, vo);
            close(conn, ps, res);
        } catch (SQLException e) {
            close(conn, ps, res);
            LOGGER.debug(e.getMessage());
            throw new DBException("Exception while accessing database");
        }
        return vo;
    }

    public UserVO getUserVO(ResultSet res, UserVO vo) throws SQLException {
        if (res != null) {
            while (res.next()) {
                vo = new UserVO();
                vo.setUserId(res.getInt(1));
                vo.setEmail(res.getString(2));
                vo.setFirstName(res.getString(3));
                vo.setLastName(res.getString(4));
                vo.setEmailVerificationHash(res.getString(5));
                vo.setEmailVerificationAttempts(res.getInt(6));
                vo.setStatus(res.getString(7));
                vo.setCreatedTime(res.getString(8));
            }
        }
        return vo;
    }

    public boolean verifyEmailHash(String user_id, String hash) throws DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean verified = false;
        ResultSet res = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(MySQLProcedures.VERIFY_EMAIL_HASH);
            ps.setString(1, user_id);
            ps.setString(2, hash);
            res = ps.executeQuery();
            if (res != null) {
                while (res.next()) {
                    verified = true;
                }
            }
            close(conn, ps, res);
        } catch (SQLException e) {
            close(conn, ps, res);
            LOGGER.debug(e.getMessage());
            throw new DBException("Exception while accessing database");
        }
        return verified;
    }

    public boolean doesEmailExist(String email) throws DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean verified = false;
        ResultSet res = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(MySQLProcedures.DOES_EMAIL_EXIST);
            ps.setString(1, email);
            res = ps.executeQuery();
            if (res != null) {
                while (res.next()) {
                    verified = true;
                }
            }
            close(conn, ps, res);
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            close(conn, ps, res);
            throw new DBException("Exception while accessing database");
        }
        return verified;
    }

    public String insertRow(UserVO vo) throws DBException {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        String id = null;
        ResultSet res = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            ps1 = conn.prepareStatement(MySQLProcedures.INSERT_USER);
            ps1.setString(1, vo.getEmail());
            ps1.setString(2, vo.getFirstName());
            ps1.setString(3, vo.getLastName());
            ps1.setString(4, vo.getEmailVerificationHash());
            ps1.setString(5, vo.getPassword());
            ps1.executeUpdate();

            ps2 = conn.prepareStatement("SELECT LAST_INSERT_ID()");
            res = ps2.executeQuery();

            if (res != null) {
                while (res.next()) {
                    id = res.getString(1);
                }
            }

            conn.commit();
            close(conn, ps1, ps2, res);
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            close(conn, ps1, ps2, res);
            throw new DBException("Exception while accessing database");
        }
        return id;
    }

    public void deleteRow(UserVO vo) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(MySQLProcedures.DELETE_USER);
            ps.setInt(1, vo.getUserId());
            ps.executeUpdate();
            ps.close();
            close(conn, ps);
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            close(conn, ps);
            e.printStackTrace();
        }
    }

    public void updateStaus(String user_id, String status) throws DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(MySQLProcedures.UPDATE_STATUS);
            ps.setString(1, status);
            ps.setString(2, user_id);
            ps.executeUpdate();
            close(conn, ps);
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            close(conn, ps);
            throw new DBException("Exception while accessing database");
        }
    }

    public void updateEmailVerificationHash(String user_id, String hash) throws DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(MySQLProcedures.UPDATE_EMAIL_VERIF_HASH);
            ps.setString(1, hash);
            ps.setInt(2, 0);
            ps.setString(3, user_id);
            ps.executeUpdate();
            close(conn, ps);
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            close(conn, ps);
            throw new DBException("Exception while accessing database");
        }
    }

    public int incrementVerificationAttempts(String user_id) throws DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet res = null;
        int attempts = 0;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(MySQLProcedures.INCREMENT_EMAIL_VERIF_ATTEMPTS);
            ps.setString(1, user_id);
            ps.executeUpdate();

            ps2 = conn.prepareStatement("SELECT EMAIL_VERIFICATION_ATTEMPTS from DEMO_USER");
            res = ps2.executeQuery();

            if (res != null) {
                while (res.next()) {
                    attempts = res.getInt(1);
                }
            }
            close(conn, ps, ps2, res);
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            close(conn, ps, ps2, res);
            throw new DBException("Exception while accessing database");
        }
        return attempts;
    }

    public UserVO verifyLogin(String inputEmail, String inputPassword) throws DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        UserVO vo = null;
        ResultSet res = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(MySQLProcedures.VERIFY_LOGIN);
            ps.setString(1, inputEmail);
            ps.setString(2, inputPassword);
            res = ps.executeQuery();
            if (res != null) {
                while (res.next()) {
                    vo = new UserVO();
                    vo.setUserId(res.getInt(1));
                    vo.setEmail(res.getString(2));
                    vo.setFirstName(res.getString(3));
                    vo.setLastName(res.getString(4));
                    vo.setStatus(res.getString(5));
                    vo.setCreatedTime(res.getString(6));
                }
            }
            close(conn, ps, res);
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            close(conn, ps, res);
            throw new DBException("Exception while accessing database");
        }
        return vo;
    }

    public boolean verifyUserIdAndPassword(String userId, String inputCurrentPassword) throws DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean verified = false;
        ResultSet res = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(MySQLProcedures.VERIFY_USER_ID_AND_PASSWORD);
            ps.setString(1, userId);
            ps.setString(2, inputCurrentPassword);
            res = ps.executeQuery();
            if (res != null) {
                while (res.next()) {
                    verified = true;
                }
            }
            close(conn, ps, res);
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            close(conn, ps, res);
            throw new DBException("Exception while accessing database");
        }
        return verified;
    }

    public void updatePassword(String userId, String inputPassword) throws DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(MySQLProcedures.UPDATE_PASSWORD);
            ps.setString(1, inputPassword);
            ps.setString(2, userId);
            ps.executeUpdate();
            close(conn, ps);
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            close(conn, ps);
            throw new DBException("Exception while accessing database");
        }
    }

    public void updateEmailVerificationHashForResetPassword(String inputEmail, String hash) throws DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(MySQLProcedures.UPDATE_EMAIL_VERIF_HASH_FOR_RESET_PASS);
            ps.setString(1, hash);
            ps.setInt(2, 0);
            ps.setString(3, GlobalConstants.IN_RESET_PASSWORD);
            ps.setString(4, inputEmail);
            ps.executeUpdate();
            close(conn, ps);
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            close(conn, ps);
            throw new DBException("Exception while accessing database");
        }
    }

    public UserVO selectUSERbyEmail(String inputEmail) throws DBException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        UserVO vo = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(MySQLProcedures.SELECT_USER_BY_EMAIL);
            ps.setString(1, inputEmail);
            res = ps.executeQuery();
            vo = getUserVO(res, vo);
            close(conn, ps, res);
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            close(conn, ps, res);
            throw new DBException("Exception while accessing database");
        }
        return vo;
    }


}
