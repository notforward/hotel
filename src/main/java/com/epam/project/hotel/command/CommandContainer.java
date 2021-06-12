package com.epam.project.hotel.command;

import com.epam.project.hotel.command.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        commands.put("editroom", new EditRoomCommand());
        commands.put("declinerequest", new DeclineRequestCommand());
        commands.put("confirmrequest", new ConfirmRequestCommand());
        commands.put("showrequests", new ShowRequestsCommand());
        commands.put("showrequest", new ShowRequestCommand());
        commands.put("checkdateadmin", new CheckDateAdminCommand());
        commands.put("showuserrequest", new ShowUserRequestCommand());
        commands.put("showprofile", new ShowProfileCommand());
    }
    public static List<String> getUserCommands(){
        List<String> links = new ArrayList<>();
        links.add("showrooms");
        links.add("showroom");
        links.add("logout");
        links.add("bookroom");
        links.add("createcheck");
        links.add("showuserchecks");
        links.add("showusercheck");
        links.add("checkdate");
        links.add("paycheck");
        links.add("createrequest");
        links.add("showuserrequest");
        links.add("showprofile");
        return links;
    }
    public static List<String> getManagerCommands(){
        List<String> links = new ArrayList<>();
        links.add("showrooms");
        links.add("showroom");
        links.add("logout");
        links.add("checkdateadmin");
        links.add("showuserrequest");
        links.add("declinerequest");
        links.add("confirmrequest");
        links.add("showrequests");
        links.add("editroom");
        links.add("showrequest");
        links.add("showprofile");
        return links;
    }
    public static List<String> getUnknownCommands(){
        List<String> links = new ArrayList<>();
        links.add("login");
        links.add("register");
        links.add("showrooms");
        links.add("showroom");
        links.add("showprofile");
        return links;
    }
    public static Command getCommand(String command){
        return commands.get(command);
    }

}
