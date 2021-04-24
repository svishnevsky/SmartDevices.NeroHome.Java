package by.nerohome.gateway.commands;

public class ControlDeviceCommand extends OledoCommand {
    private int id;
    private DeviceActions action;

    public ControlDeviceCommand(
        OledoCommandClasses commandClass,
        int id,
        DeviceActions action,
        String label) {
        super(commandClass, "controlDevice", label);
        this.id = id;
        this.action = action;
    }

    public String getAction() {
        return action.toString().toLowerCase();
    }

    public int getId() {
        return id;
    }
    
}
