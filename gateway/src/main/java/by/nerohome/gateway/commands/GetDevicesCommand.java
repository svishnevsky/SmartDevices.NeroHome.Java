package by.nerohome.gateway.commands;

public class GetDevicesCommand extends OledoCommand {
    private static final String COMMAND_NAME = "getModemParams";

    public GetDevicesCommand(String label) {
        super(OledoCommandClasses.NETRO, COMMAND_NAME, label);
    }

    public Boolean getSavedIds() {
        return true;
    }
    
}
