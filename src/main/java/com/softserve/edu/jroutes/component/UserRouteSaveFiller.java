package com.softserve.edu.jroutes.component;

import org.springframework.stereotype.Component;

@Component
public class UserRouteSaveFiller {

    private String message = "saveRoute.saveError";
    private boolean isValid = true;
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isValid() {
        return isValid;
    }
    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
}
