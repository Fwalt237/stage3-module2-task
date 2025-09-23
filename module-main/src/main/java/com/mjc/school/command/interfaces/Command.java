package com.mjc.school.command.interfaces;

import java.util.Map;

public interface Command {
    String getCommandId();
    Object execute(Map<String, Object> parameters);
    String getDescription();
}
