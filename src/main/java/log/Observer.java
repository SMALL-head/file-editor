package log;

import command.commandImpl.AbstractCommand;

public interface Observer {
    void update(AbstractCommand executedCommand);
}
