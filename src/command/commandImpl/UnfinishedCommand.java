package src.command.commandImpl;

import src.command.Operator;

/**
 * @author zyc
 * @version 1.0
 */
public class UnfinishedCommand extends AbstractCommand{

    public UnfinishedCommand(String originCommand) {
        super(originCommand);
    }

    @Override
    public void execute() throws Exception {
        System.out.println("尚未完成该指令");
    }

    @Override
    public boolean isRecordable() {
        return false;
    }

    @Override
    public Operator reverseOperator() {
        return super.reverseOperator();
    }
}
