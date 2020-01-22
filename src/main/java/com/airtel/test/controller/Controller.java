package com.airtel.test.controller;

import javax.servlet.http.HttpSession;

public interface Controller {
    public final String USER_ID = "USER_ID";

    public void saveUserInSession(HttpSession session, String userId);

    public boolean isUserSignedIn(HttpSession session);

    public String getUserIdFromSession(HttpSession session);

    public void removeUserIdFromSession(HttpSession session);
}
