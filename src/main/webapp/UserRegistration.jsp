<%@ page import="constants.ErrorMessageConstants" %>
<%@ page import="constants.FieldConstants" %>
<%@ page import="constants.RegexConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<%@ include file="includes/_header.jsp" %>

<body>
<div style="max-width:390px;padding:5px;margin:auto;">
    <h2 style="text-align:center">Register Here</h2>
    <div class="alert alert-danger" role="alert" id="errorField" style="display:none">
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
        <span class="sr-only">Error:</span>
        <span class="message"></span>
    </div>
    <div class="alert alert-success" role="alert" id="successField" style="display:none">
        <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
        <span class="sr-only">Success:</span>
        <span class="message"></span>
    </div>
    <form class="form-horizontal" id="formRegister" data-toggle="validator" role="form">
        <div class="form-group">
            <label for="<%=FieldConstants.INPUT_EMAIL%>" class="control-label">Email</label>
            <input name="<%=FieldConstants.INPUT_EMAIL%>" pattern="<%=RegexConstants.REGEX_VALID_EMAIL%>" class="form-control"
                   id="<%=FieldConstants.INPUT_EMAIL%>" placeholder="Enter Email" data-error="<%=ErrorMessageConstants.ERROR_MSG_INVALID_EMAIL%>" required>
            <div class="help-block with-errors"></div>
        </div>
        <div class="form-group">
            <label for="<%=FieldConstants.INPUT_FIRSTNAME%>" class="control-label">First Name</label>
            <input pattern="<%=RegexConstants.REGEX_VALID_ALPHA_NUMERIC_STRING%>{1,20}" name="<%=FieldConstants.INPUT_FIRSTNAME%>" class="form-control"
                   id="<%=FieldConstants.INPUT_FIRSTNAME%>" placeholder="Enter First Name" data-error="<%=ErrorMessageConstants.ERROR_MSG_INVALID_NAME%>" required>
            <div class="help-block with-errors"></div>
        </div>
        <div class="form-group">
            <label for="<%=FieldConstants.INPUT_LASTNAME%>" class="control-label">Last Name</label>
            <input pattern="<%=RegexConstants.REGEX_VALID_ALPHA_NUMERIC_STRING%>{1,20}" name="<%=FieldConstants.INPUT_LASTNAME%>" class="form-control"
                   id="<%=FieldConstants.INPUT_LASTNAME%>" placeholder="Enter Last Name" data-error="<%=ErrorMessageConstants.ERROR_MSG_INVALID_NAME%>" data-toggle="tooltip"
                   data-placement="right" required>
            <div class="help-block with-errors"></div>
        </div>
        <div class="form-group">
            <label for="<%=FieldConstants.INPUT_PASSWORD%>" class="control-label">Password</label>
            <input type="password" pattern="<%=RegexConstants.REGEX_VALID_PASSWORD%>" name="<%=FieldConstants.INPUT_PASSWORD%>" class="form-control"
                   id="<%=FieldConstants.INPUT_PASSWORD%>" placeholder="Enter Password" data-error="<%=ErrorMessageConstants.ERROR_MSG_INVALID_PASSWORD%>" required>
            <div class="help-block with-errors"></div>
        </div>
        <div class="form-group">
            <label for="<%=FieldConstants.INPUT_PASSWORD_CONFIRM%>" class="control-label">Confirm Password</label>
            <input type="password" name="<%=FieldConstants.INPUT_PASSWORD_CONFIRM%>" class="form-control"
                   id="<%=FieldConstants.INPUT_PASSWORD_CONFIRM%>" data-match="#inputPassword" placeholder="Enter Password Again"
                   data-error="<%=ErrorMessageConstants.ERROR_MSG_INVALID_CONFIRM_PASSWORD%>" required>
            <div class="help-block with-errors"></div>
        </div>
        <div class="form-group">
            <button style="width:100%" type="submit" class="btn btn-default btn-primary">Register</button>
        </div>
    </form>
</div>
<script>
    $(function () {
        $('#formRegister').validator().on('submit', function (e) {
            if (e.isDefaultPrevented()) {
                // handle the invalid form...
            } else {
                blockUi();
                // everything looks good!
                $.post("RegisterEmail", $( "#formRegister").serialize(), function( data ) {
                    var jdata = JSON.parse(data);
                    if(jdata.code == -1) {
                        $("#errorField .message").text(jdata.message);
                        $("#errorField").show();
                        $("#successField").hide();
                    } else {
                        $("#successField .message").text(jdata.message);
                        $("#errorField").hide();
                        $("#successField").show();
                        $("#formRegister").hide();
                    }
                });
            }
            return false;
        });

    });
</script>
</body>
</html>
