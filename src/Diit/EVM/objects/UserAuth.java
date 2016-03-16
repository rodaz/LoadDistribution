package Diit.EVM.objects;

/**
 * Created by Alexey on 15.03.2016.
 */
public class UserAuth {
    private String name;
    private String login;
    private String psw;

    public UserAuth(String name, String login, String psw) {
        this.name = name;
        this.login = login;
        this.psw = psw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
}
