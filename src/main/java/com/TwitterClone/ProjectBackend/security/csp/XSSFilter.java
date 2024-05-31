package com.TwitterClone.ProjectBackend.security.csp;

import com.TwitterClone.ProjectBackend.security.csp.XSSRequestWrapper;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.Filter;
public class XSSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        XSSRequestWrapper wrappedRequest = new XSSRequestWrapper(httpRequest);
        // Continue the filter chain with the wrapped request
        chain.doFilter(wrappedRequest, httpResponse);

    }
}
