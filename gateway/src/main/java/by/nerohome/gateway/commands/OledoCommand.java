package by.nerohome.gateway.commands;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonAutoDetect
@JsonPropertyOrder({"class", "command"})
public abstract class OledoCommand {
    private OledoCommandClasses commandClass;
    private String command;
    private String label;

    protected OledoCommand(
        OledoCommandClasses commandClass,
        String command,
        String label) {
        this.commandClass = commandClass;
        this.command = command;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @JsonProperty("class")
    public String getCommandClass() {
        return commandClass.toString().toLowerCase();
    }

    public String getCommand() {
        return command;
    }
}
