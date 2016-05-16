package main.java.EVM.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Alexey on 15.03.2016.
 */
public class UserAuth {
    private SimpleIntegerProperty authId;
    private SimpleStringProperty name;
    private SimpleStringProperty login;
    private SimpleStringProperty psw;

    public UserAuth(int authId, String name, String login, String psw) {
        this.authId = new SimpleIntegerProperty(authId);
        this.name = new SimpleStringProperty(name);
        this.login = new SimpleStringProperty(login);
        this.psw = new SimpleStringProperty(psw);
    }

    public int getAuthId() {
        return authId.get();
    }

    public SimpleIntegerProperty authIdProperty() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId.set(authId);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPsw() {
        return psw.get();
    }

    public SimpleStringProperty pswProperty() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw.set(psw);
    }
}
