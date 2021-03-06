package dao.mysql;

public class MySQLProcedures {
    public static final String SELECT_USER = "select USER_ID, EMAIL, FIRST_NAME, LAST_NAME, EMAIL_VERIFICATION_HASH, EMAIL_VERIFICATION_ATTEMPTS, STATUS, CREATED_TIME from user where USER_ID = ?";
    public static final String VERIFY_EMAIL_HASH = "select 1 from user where USER_ID = ? and EMAIL_VERIFICATION_HASH = ?";
    public static final String DOES_EMAIL_EXIST = "select 1 from user where EMAIL = ?";
    public static final String INSERT_USER = "insert into user (EMAIL,FIRST_NAME,LAST_NAME,EMAIL_VERIFICATION_HASH,PASSWORD) values (?,?,?,?,?)";
    public static final String DELETE_USER = "delete from user where USER_ID = ?";
    public static final String UPDATE_STATUS = "update user set STATUS = ? where USER_ID = ?";
    public static final String UPDATE_EMAIL_VERIF_HASH = "update user set EMAIL_VERIFICATION_HASH = ?, EMAIL_VERIFICATION_ATTEMPTS = ? where USER_ID = ?";
    public static final String INCREMENT_EMAIL_VERIF_ATTEMPTS = "update user set EMAIL_VERIFICATION_ATTEMPTS = EMAIL_VERIFICATION_ATTEMPTS + 1 where USER_ID = ?";
    public static final String VERIFY_LOGIN = "select USER_ID, EMAIL, FIRST_NAME, LAST_NAME, STATUS, CREATED_TIME from user where EMAIL = ? and PASSWORD = ?";
    public static final String VERIFY_USER_ID_AND_PASSWORD = "select 1 from user where USER_ID = ? and PASSWORD = ?";
    public static final String UPDATE_PASSWORD = "update user set PASSWORD = ? where USER_ID = ?";
    public static final String UPDATE_EMAIL_VERIF_HASH_FOR_RESET_PASS = "update user set EMAIL_VERIFICATION_HASH = ?, EMAIL_VERIFICATION_ATTEMPTS = ?, STATUS = ? where EMAIL = ?";
    public static final String SELECT_USER_BY_EMAIL = "select USER_ID, EMAIL, FIRST_NAME, LAST_NAME, EMAIL_VERIFICATION_HASH, EMAIL_VERIFICATION_ATTEMPTS, STATUS, CREATED_TIME from user where EMAIL = ?";
}
