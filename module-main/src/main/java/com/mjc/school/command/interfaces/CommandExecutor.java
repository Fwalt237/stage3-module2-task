package com.mjc.school.command.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommandExecutor {
    Optional<Command> findCommand(String commandId);
    void executeCommand(String commandId, Map<String, Object> parameters);
    List<Command> getAllCommands();
}
