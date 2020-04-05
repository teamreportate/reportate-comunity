package security;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * MC4
 * La Paz - Bolivia
 * Cliente Referido - TIGO
 * bo.com.tigo.userModule.security.filter
 * 08/12/2015 - 10:58 AM
 * Created by: Vladimir Edwin
 */
public class CacheFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        httpResponse.setHeader("x-ua-compatible", "IE=8");
//        httpResponse.setHeader("Cache-Control", "private, no-cache, no-store, must-revalidate, max-age=0, post-check=0, pre-check=0");
//        httpResponse.setHeader("Pragma", "no-cache");
//        httpResponse.setHeader("X-Powered-By", "none");
//        httpResponse.setHeader("Server", "none");
//        httpResponse.setDateHeader("Expires", 0);
//        httpResponse.setHeader("X-Frame-Options", "deny");
//        httpResponse.setHeader("X-XSS-Protection", "1; mode=block");
//        httpResponse.setHeader("Strict-Transport-Security", "max-age=15552000; includeSubDomains; preload");
//        httpResponse.setDateHeader("Last-Modified", new Date().getTime());
//        httpResponse.setHeader("X-Firefox-Spdy", "3.1");
//        httpResponse.setHeader("x-content-customerPhone-options", "nosniff");
//        httpResponse.setHeader("Options", "-Indexes");
//        httpResponse.setHeader("-Pins Público-Key", "pin-sha256 = YWxlcGFjby5tYXRvbg==; max-age=15768000; includeSubDomains");
//        httpResponse.setHeader("X-Content-Type-Options","nosniff");
//
//        httpResponse.addHeader("Content-Security-Policy",
//                "default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval' ; img-src 'self'; style-src 'self'  'unsafe-inline'  ; font-src 'self'");
//        httpResponse.addHeader("X-Content-Security-Policy",
//                "default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; img-src 'self' ;  style-src 'self' 'unsafe-inline' ; font-src 'self'");
//        httpResponse.addHeader("X-Webkit-CSP",
//                "default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; img-src 'self' ; style-src 'self' 'unsafe-inline' ; font-src 'self'");
//
//        httpResponse.addHeader("Cache-Control", "private, no-cache, no-store, must-revalidate");
//        httpResponse.addHeader("Pragma", "no-cache");
//
//
//        String contextPath = "/";//httpRequest.getContextPath();
//        String secure = "";
//        if (request.isSecure()) {
//            secure = "; Secure";
//        }
//        httpResponse.setHeader("SET-COOKIE", "JSESSIONID=" + httpRequest.getSession(true).getId() + "; Path=" + contextPath + "; HttpOnly" + secure);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
