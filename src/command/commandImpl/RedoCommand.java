package src.command.commandImpl;

import src.context.FileEditorContext;

import java.util.Deque;

public class RedoCommand extends AbstractCommand{
    FileEditorContext ctx;

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
        Deque<AbstractCommand> executeStack = this.ctx.getExecuteStack();

        if (undoStack.size() > 0) {
            // 弹栈上一个操作
            AbstractCommand lastCommand = undoStack.removeLast();

            System.out.println("恢复操作：");

            // 执行 (执行时已经重新进入executeStack)
            lastCommand.execute();
        }
        else {
            System.out.println("没有操作可以恢复。");
        }

        super.execute();
    }
}
