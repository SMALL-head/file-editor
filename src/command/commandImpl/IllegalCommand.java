package src.command.commandImpl;

/**
 * @author zyc
 * @version 1.0
 */
public class IllegalCommand extends AbstractCommand {

    public IllegalCommand(String originCommand) {
        super(originCommand);
    }

    @Override
    public void execute() throws Exception {
        System.out.println("非法指令，无法运行");
    }

    @Override
    public boolean isRecordable() {
        return false;
    }
}
