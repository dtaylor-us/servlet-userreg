package dao;


import util.DBException;
import model.UserVO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO {
    UserVO selectUSER(String userId) throws DBException;

    UserVO getUserVO(ResultSet res, UserVO vo) throws SQLException;

    boolean verifyEmailHash(String user_id, String hash) throws DBException;

    boolean doesEmailExist(String email) throws DBException;

    String insertRow(UserVO vo) throws DBException;

    void deleteRow(UserVO vo);

    void updateStaus(String user_id, String status) throws DBException;

    void updateEmailVerificationHash(String user_id, String hash) throws DBException;

    int incrementVerificationAttempts(String user_id) throws DBException;

    UserVO verifyLogin(String inputEmail, String inputPassword) throws DBException;

    boolean verifyUserIdAndPassword(String userId, String inputCurrentPassword) throws DBException;

    void updatePassword(String userId, String inputPassword) throws DBException;

    void updateEmailVerificationHashForResetPassword(String inputEmail, String hash) throws DBException;

    UserVO selectUSERbyEmail(String inputEmail) throws DBException;
}
