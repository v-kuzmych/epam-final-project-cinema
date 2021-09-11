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
        commands.put("user_schedule", new GetScheduleForUserCommand());
        commands.put("user_films_page", new GetAllFilmsForUserCommand());
        commands.put("order_page", new ShowOrderPageCommand());
        commands.put("add_order", new AddOrderCommand());
        commands.put("noCommand", new NoCommand());

        // client commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());

        // admin commands
        commands.put("admin_films_page", new GetAllFilmsForAdminCommand());
        commands.put("add_film", new ShowAddFilmPageCommand());
        commands.put("film_edit", new ShowEditFilmPageCommand());
        commands.put("save_film", new AddFilmCommand());
//        commands.put("film_delete", new DeleteFilmCommand());
        commands.put("add_seance", new AddSeanceCommand());
        commands.put("admin_schedule", new GetScheduleForAdminCommand());
        commands.put("admin_users_page", new GetAllUsersCommand());
        commands.put("user_info", new GetUserInfoForAdminCommand());
        commands.put("save_poster", new SavePosterFileCommand());



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
