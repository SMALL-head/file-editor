package src.log;

import src.command.commandImpl.AbstractCommand;

public interface Observer {
    void update(AbstractCommand executedCommand);
}
