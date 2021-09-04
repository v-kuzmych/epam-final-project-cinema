package com.vasilisa.cinema.command;

import java.util.Map;
import java.util.TreeMap;


public class CommandContainer {
//    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        // common commands
        commands.put("switch_language", new GeneralCommand()::switchLanguage);
        commands.put("register", new GeneralCommand()::register);
        commands.put("user_films_page", new GeneralCommand()::getAllFilms);
        commands.put("noCommand", new GeneralCommand()::noCommand);

        // client commands
        commands.put("login", new UserCommand()::login);
        commands.put("logout", new UserCommand()::logout);

        // admin commands
        commands.put("admin_films_page", new AdminCommand()::getAllFilms);
        commands.put("add_film", new AdminCommand()::showAddFilmPage);
        commands.put("film_edit", new AdminCommand()::showEditFilmPage);
        commands.put("save_film", new AdminCommand()::saveFilm);
//        commands.put("film_delete", new AdminCommand()::deleteFilm);
        commands.put("add_seance", new AdminCommand()::addSeance);
        commands.put("schedule_page", new AdminCommand()::getSchedule);
        commands.put("admin_users_page", new AdminCommand()::getAllUsers);
        commands.put("user_info", new AdminCommand()::getUserInfo);



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
