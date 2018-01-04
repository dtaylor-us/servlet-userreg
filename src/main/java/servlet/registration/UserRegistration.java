package servlet.registration;

import com.google.gson.Gson;
import constants.ErrorMessageConstants;
import constants.FieldConstants;
import constants.ViewConstants;
import util.DBException;
import dao.UserDAO;
import dao.mysql.UserDAOImpl;
import model.StatusVO;
import model.UserVO;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import util.MailUtil;
import util.Utils;

import javax.mail.MessagingException;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/UserRegistration")
public class UserRegistration extends HttpServlet {
    final static Logger logger = Logger.getLogger(UserRegistration.class);
    private static final Gson gson = new Gson();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.getRequestDispatcher(ViewConstants.USER_REGISTRATION).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // collect all input values
        UserDAO userDAO = new UserDAOImpl();

        String email = request.getParameter(FieldConstants.INPUT_EMAIL);
        String firstName = request.getParameter(FieldConstants.INPUT_FIRSTNAME);
        String lastName = request.getParameter(FieldConstants.INPUT_LASTNAME);
        String password = request.getParameter(FieldConstants.INPUT_PASSWORD);

        StatusVO statusVO = new StatusVO();
        String output = "";

        if (!validate(email, firstName, lastName, password)) {

            statusVO.setMessage(ErrorMessageConstants.ERROR_MSG_INVALID_FORM_INPUT);
            output = gson.toJson(statusVO);

        } else {
            // hydrate user VO
            UserVO userVO = new UserVO();
            userVO.setEmail(email);
            userVO.setFirstName(firstName);
            userVO.setLastName(lastName);

            // generate hash for password
            userVO.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));

            // generate hash code for email verification
            String hash = Utils.prepareRandomString(15);

            // generate hash for password
            String emailVerifHash = BCrypt.hashpw(hash, BCrypt.gensalt());
            userVO.setEmailVerificationHash(emailVerifHash);

            try {
                //check if email exists or not
                if (!userDAO.doesEmailExist(email)) {
                    // create account if email not exists
                    String id = userDAO.insertRow(userVO);
                    MailUtil.sendEmailRegistrationLink(id, email, hash);
                    statusVO.setCode(0);
                    statusVO.setMessage("Registation Link Was Sent To Your Mail Successfully. Please Verify Your Email");
                    output = gson.toJson(statusVO);

                } else {
                    // tell user that the email already in use
                    statusVO.setCode(-1);
                    statusVO.setMessage("This email was already registered.");
                    logger.info(statusVO.getCode());
                    logger.info(statusVO.getMessage());
                    output = gson.toJson(statusVO);
                }

            } catch (DBException | MessagingException e) {
                logger.info(hash);
                logger.info(emailVerifHash);
                logger.debug(e.getMessage());
                statusVO.setCode(-1);
                statusVO.setMessage(e.getMessage());
                output = gson.toJson(statusVO);
            }
        }

        // send output to user
        PrintWriter pw = response.getWriter();
        pw.write(output);
        pw.flush();
        pw.close();
    }

    // MOVE TO A FILTER
    public static boolean validate(String email, String firstName, String lastName, String password) {
        if (email == null) {
            return false;
        }
        if (firstName == null) {
            return false;
        }
        if (lastName == null) {
            return false;
        }
        if (password == null) {
            return false;
        }
        return true;
    }

}
