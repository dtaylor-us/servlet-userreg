package filter;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        PrintWriter out = servletResponse.getWriter();
        String password = servletRequest.getParameter("pass");
        if (password.equals("1234")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            out.println("You have entered incorrect password");
            RequestDispatcher rs = servletRequest.getRequestDispatcher("index.html");
            rs.include(servletRequest, servletResponse);
        }
    }

    public void destroy() {

    }
}
