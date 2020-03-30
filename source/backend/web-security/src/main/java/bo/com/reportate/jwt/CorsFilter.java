package bo.com.reportate.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Project    :unicolegio
 * Package    :bo.com.mc4.unicolegio.authenticate.filter
 * Date       :18/12/2018
 * Created by :fmontero
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("[init], CORS Filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
        }else filterChain.doFilter(servletRequest, servletResponse);


//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpServletRequest request= (HttpServletRequest) servletRequest;
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "*");
//
//        response.setHeader("x-ua-compatible", "IE=8");
//        response.setHeader("Cache-Control", "private, no-cache, no-store, must-revalidate, max-age=0, post-check=0, pre-check=0");
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("X-Powered-By", "none");
//        response.setHeader("Server", "none");
//        response.setDateHeader("Expires", 0); // Proxies
//        response.setHeader("X-Frame-Options", "deny");
//        response.setHeader("X-XSS-Protection", "1; mode=block");
//        response.setHeader("Strict-Transport-Security", "max-age=15552000; includeSubDomains; preload");
//        response.setDateHeader("Last-Modified", new Date().getTime());
//        response.setHeader("X-Firefox-Spdy", "3.1");
//        response.setHeader("x-content-type-options", "nosniff");
//        response.setHeader("Options", "-Indexes");
//        response.setHeader("-Pins Público-Key", "pin-sha256 = YWxlcGFjby5tYXRvbg==; max-age=15768000; includeSubDomains");
//        response.setHeader("X-Content-Type-Options","nosniff");
//        response.addHeader("Content-Security-Policy",
//                "default-src 'self' blob: https://reportate.reportate.com; child-src 'self' blob: https://reportate.reportate.com; script-src 'self' 'unsafe-inline' 'unsafe-eval'; img-src 'self' data: https://reportate.reportate.com;  style-src 'self' 'unsafe-inline' ; font-src 'self' ");
//        response.addHeader("X-Content-Security-Policy",
//                "default-src 'self' data: https://reportate.reportate.com; script-src 'self' 'unsafe-inline' 'unsafe-eval'; script-src-elem 'self' 'unsafe-inline' 'unsafe-eval'; img-src 'self' data: https://reportate.reportate.com;  style-src 'self' 'unsafe-inline' ; font-src 'self' ");
//        response.addHeader("X-Webkit-CSP",
//                "default-src 'self' data: https://reportate.reportate.com; script-src 'self' 'unsafe-inline'  'unsafe-eval'; script-src-elem 'self' 'unsafe-inline' 'unsafe-eval'; img-src 'self'  data: https://reportate.reportate.com; style-src 'self' 'unsafe-inline' ; font-src 'self' ");
//        if ("OPTIONS".equalsIgnoreCase(request.getMethod())){
//            response.setStatus(HttpServletResponse.SC_OK);
//        }else filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
