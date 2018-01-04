package constants;

public class ConfigurationConstants {
    public static final String DB_URL = System.getenv("DOCKER_DATABASE_URL");
    public static final String DB_USERNAME = System.getenv("DOCKER_DATABASE_USERNAME");
    public static final String DB_PASSWORD = System.getenv("DOCKER_DATABASE_PASSWORD");
    public static final String MAIL_USERNAME = "test.mail.taylor@gmail.com"; // like example@outlook.com
    public static final String MAIL_PASSWORD = "test123123123";  // your mail password here
    public static final String MAIL_SMTP_HOST = "smtp.gmail.com";
    public static final String MAIL_REGISTRATION_SITE_LINK = "http://localhost:8080/demos/VerifyRegisteredEmailHash";
}
