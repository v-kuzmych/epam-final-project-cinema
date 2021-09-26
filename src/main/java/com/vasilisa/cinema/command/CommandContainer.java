package com.vasilisa.cinema.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;


public class CommandContainer {

   private static final Logger logger = LogManager.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        // common commands
        commands.put("switch_language", new SwitchLanguageCommand());
        commands.put("register", new RegisterCommand());
        commands.put("schedule", new GetScheduleCommand());
        commands.put("seances", new GetAllSeancesCommand());
        commands.put("user_films_page", new GetAllFilmsForUserCommand());
        commands.put("order_page", new ShowOrderPageCommand());
        commands.put("film_page", new ShowFilmPageCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("noCommand", new NoCommand());

        // client commands
        commands.put("login", new LoginCommand());
        commands.put("add_order", new AddOrderCommand());
        commands.put("profile", new GetUserProfileCommand());
        commands.put("update_user", new UpdateUserProfileCommand());

        // admin commands
        commands.put("admin_films_page", new GetAllFilmsForAdminCommand());
        commands.put("admin_users_page", new GetAllUsersCommand());
        commands.put("admin_orders_page", new GetAllOrdersForAdminCommand());
        commands.put("add_film", new ShowAddFilmPageCommand());
        commands.put("film_edit", new ShowEditFilmPageCommand());
        commands.put("save_film", new SaveFilmCommand());
        commands.put("delete_film", new DeleteFilmCommand());
        commands.put("delete_seance", new DeleteSeanceCommand());
        commands.put("add_seance", new AddSeanceCommand());
        commands.put("user_info", new GetUserInfoForAdminCommand());
        commands.put("admin_dashboard", new ShowDashboardPageCommand());

        logger.debug("Command container was successfully initialized");
        logger.trace("Number of commands --> " + commands.size());
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
            logger.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}
