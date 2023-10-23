package src.command.commandImpl;

import src.command.Operator;

/**
 * @author zyc
 * @version 1.0
 */
public class EmptyCommand extends AbstractCommand{

    public EmptyCommand(String originCommand) {
        super(originCommand);
    }

    @Override
    public boolean isRecordable() {
        return false;
    }

}
