package model;

public class UserVO {
    private int userId;

    private String email;

    private String firstName;

    private String lastName;

    private String emailVerificationHash;

    private int emailVerificationAttempts;

    private String password;

    private String status;

    private String createdTime;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmailVerificationHash()
    {
        return emailVerificationHash;
    }

    public void setEmailVerificationHash(String emailVerificationHash)
    {
        this.emailVerificationHash = emailVerificationHash;
    }

    public int getEmailVerificationAttempts()
    {
        return emailVerificationAttempts;
    }

    public void setEmailVerificationAttempts(int emailVerificationAttempts)
    {
        this.emailVerificationAttempts = emailVerificationAttempts;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getCreatedTime()
    {
        return createdTime;
    }

    public void setCreatedTime(String createdTime)
    {
        this.createdTime = createdTime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [userId = "+ userId +", email = "+ email +", firstName = "+ firstName +", lastName = "+ lastName +", emailVerificationHash = "+ emailVerificationHash +", emailVerificationAttempts = "+ emailVerificationAttempts +", password = "+ password +", status = "+ status +", createdTime = "+ createdTime +"]";
    }
}
