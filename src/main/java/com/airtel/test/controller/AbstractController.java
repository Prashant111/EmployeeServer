package com.airtel.test.controller;


import javax.servlet.http.HttpSession;

public class AbstractController implements Controller {

    @Override
    public void saveUserInSession(HttpSession session, String userId) {
        session.setAttribute(USER_ID, userId);
    }

    @Override
    public boolean isUserSignedIn(HttpSession session) {
        return session.getAttribute(USER_ID) != null;
    }

    @Override
    public String getUserIdFromSession(HttpSession session) {
        return (String) session.getAttribute(USER_ID);
    }

    @Override
    public void removeUserIdFromSession(HttpSession session) {
        session.removeAttribute(USER_ID);
    }

}
