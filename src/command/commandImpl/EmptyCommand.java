package src.command.commandImpl;

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
