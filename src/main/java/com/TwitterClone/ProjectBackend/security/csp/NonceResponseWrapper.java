package com.TwitterClone.ProjectBackend.security.csp;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

class NonceResponseWrapper extends HttpServletResponseWrapper {
    private final String nonce;

    NonceResponseWrapper(HttpServletResponse response, String nonce) {
        super(response);
        this.nonce = nonce;
    }

    private String getHeaderValue(String name, String value) {
        final String retVal;

        if (name.equals("Content-Security-Policy") && StringUtils.hasText(value)) {
            retVal = value.replace("{nonce}", nonce);
            System.out.println(retVal);
        } else {
            retVal = value;
        }

        return retVal;
    }

    @Override
    public void setHeader(String name, String value) {
        super.setHeader(name, getHeaderValue(name, value));
    }

    @Override
    public void addHeader(String name, String value) {
        super.addHeader(name, getHeaderValue(name, value));
    }
}