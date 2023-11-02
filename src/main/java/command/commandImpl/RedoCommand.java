package command.commandImpl;

import context.FileEditorContext;
import utils.SoutUtils;

import java.util.Deque;

public class RedoCommand extends AbstractCommand{

    public RedoCommand(FileEditorContext ctx, String originCommand) {
        super(originCommand);
        this.ctx = ctx;
    }

    @Override
    public boolean isRecordable() {
        return false;
    }

    @Override
    public AbstractCommand reverseOperator() {
        return super.reverseOperator();
    }

    @Override
    public void execute() throws Exception {
        Deque<AbstractCommand> undoStack = this.ctx.getUndoStack();

        if (!undoStack.isEmpty()) {
            // 弹栈上一个操作
            AbstractCommand lastCommand = undoStack.removeLast();

            SoutUtils.sout("恢复操作：");

            // 执行 (执行时已经重新进入executeStack)
            lastCommand.setLoggable(false);
            lastCommand.execute();
        }
        else {
            SoutUtils.sout("没有操作可以恢复。");
        }

        super.execute();
    }
}
