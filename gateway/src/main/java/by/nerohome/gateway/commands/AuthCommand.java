package by.nerohome.gateway.commands;

public class AuthCommand extends OledoCommand {
    private static final String COMMAND_NAME = "register";

    public AuthCommand(
        String login,
        String password,
        String label) {
        super(OledoCommandClasses.AUTH, COMMAND_NAME, label);
        this.password = password;
        this.login = login;
    }

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
