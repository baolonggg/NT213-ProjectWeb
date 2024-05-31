package com.TwitterClone.ProjectBackend.security.csp;

import org.springframework.util.Base64Utils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

public class CSPNonceFilter extends GenericFilterBean {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String nonce = "L0CTy0btwKVldokkJh5J_BqRcnLEYqnW2CXPOQJpMpU=";
        request.setAttribute("nonce", nonce);

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Content-Security-Policy", "script-src 'self' 'nonce-" + nonce + "';"+"script-src-attr 'nonce-" +nonce+"' 'unsafe-inline' 'unsafe-inline' 'unsafe-eval';");

        chain.doFilter(request, response);

//        //final String nonce;
//        final Object existingNonce = request.getAttribute("nonce");
//        final var nonce = "L0CTy0btwKVldokkJh5J_BqRcnLEYqnW2CXPOQJpMpU=";
//        request.setAttribute("nonce", nonce);
//
////        if (existingNonce == null) {
////            final var nonceArray = new byte[32];
////            secureRandom.nextBytes(nonceArray);
////            nonce = base64Encoder.encodeToString(nonceArray);
////            request.setAttribute("nonce", nonce);
////            System.err.format("Nonce generated in filter = %s%n", nonce);
////        } else {
////            nonce = (String) existingNonce;
////            System.err.format("Existing nonce retained in filter = %s%n", nonce);
////        }
//
//        chain.doFilter(request, new NonceResponseWrapper((HttpServletResponse) response, nonce));
    }

    private String generateNonce() {
        byte[] nonce = new byte[16];
        secureRandom.nextBytes(nonce);
        return base64Encoder.encodeToString(nonce);
    }
}
