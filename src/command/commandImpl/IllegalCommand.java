package src.command.commandImpl;

/**
 * @author zyc
 * @version 1.0
 */
public class IllegalCommand extends AbstractCommand{

    @Override
    public void execute() throws Exception {

    }

    @Override
    public boolean isRecordable() {
        return false;
    }
}
