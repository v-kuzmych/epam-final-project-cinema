package com.vasilisa.cinema.command;

import java.util.Map;
import java.util.TreeMap;


public class CommandContainer {
//    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        // common commands
        commands.put("switch_language", new SwitchLanguageCommand());
        commands.put("register", new RegisterCommand());
        commands.put("schedule", new GetScheduleCommand());
        commands.put("user_films_page", new GetAllFilmsForUserCommand());
        commands.put("order_page", new ShowOrderPageCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("noCommand", new NoCommand());

        // client commands
        commands.put("login", new LoginCommand());
        commands.put("add_order", new AddOrderCommand());
        commands.put("profile", new GetUserProfileCommand());

        // admin commands
        commands.put("admin_films_page", new GetAllFilmsForAdminCommand());
        commands.put("admin_users_page", new GetAllUsersCommand());
        commands.put("add_film", new ShowAddFilmPageCommand());
        commands.put("film_edit", new ShowEditFilmPageCommand());
        commands.put("save_film", new SaveFilmCommand());
        commands.put("delete_film", new DeleteFilmCommand());
        commands.put("delete_seance", new DeleteSeanceCommand());
        commands.put("add_seance", new AddSeanceCommand());
        commands.put("user_info", new GetUserInfoForAdminCommand());
        commands.put("admin_dashboard", new ShowDashboardPageCommand());



//        log.debug("Command container was successfully initialized");
//        log.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName
     *            Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
//            log.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}
