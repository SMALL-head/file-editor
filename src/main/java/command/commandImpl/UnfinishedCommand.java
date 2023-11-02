package command.commandImpl;

import utils.SoutUtils;

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
        SoutUtils.sout("尚未完成该指令");
    }

    @Override
    public boolean isRecordable() {
        return false;
    }

    @Override
    public AbstractCommand reverseOperator() {
        return super.reverseOperator();
    }
}
