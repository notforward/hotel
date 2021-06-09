package com.epam.project.hotel.command;

import com.epam.project.hotel.command.entities.*;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Map<String, Command> commands;
    static {
        commands = new HashMap<>();
        commands.put("login", new AuthorisationCommand());
        commands.put("register", new RegistrationCommand());
        commands.put("showrooms", new ShowRoomsCommand());
        commands.put("showroom", new ShowRoomCommand());
        commands.put("logout", new LogOutCommand());
        commands.put("bookroom", new BookRoomCommand());
        commands.put("createcheck", new CreateCheckCommand());
        commands.put("checkdate", new CheckDateCommand());
        commands.put("showuserchecks", new ShowUserChecksCommand());
        commands.put("showusercheck", new ShowUserCheckCommand());
        commands.put("paycheck", new PayCheckCommand());
        commands.put("createrequest", new CreateRequestCommand());
        commands.put("showrequests", new ShowRequestsCommand());
        commands.put("editroom", new EditRoomCommand());
    }
    public static Command getCommand(String command){
        return commands.get(command);
    }

}
