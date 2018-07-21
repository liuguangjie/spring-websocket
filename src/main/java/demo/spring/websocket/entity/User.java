package demo.spring.websocket.entity;

import java.io.Serializable;

/**
 * Created by free on 18-7-21.
 */
public class User implements Serializable {

    private String userName;

    private String passwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
