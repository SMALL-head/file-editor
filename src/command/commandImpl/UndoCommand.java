package src.command.commandImpl;

import src.context.FileEditorContext;

import java.util.Deque;

public class UndoCommand extends AbstractCommand {
    FileEditorContext ctx;

    public UndoCommand(FileEditorContext ctx, String originCommand) {
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
        Deque<AbstractCommand> executeStack = this.ctx.getExecuteStack();
        Deque<AbstractCommand> undoStack = this.ctx.getUndoStack();

        if (executeStack.size() > 0) {
            // 弹栈上一个操作
            AbstractCommand lastCommand = executeStack.removeLast();

            // 获取其逆操作
            AbstractCommand reversedCommand = lastCommand.reverseOperator();

            System.out.println("执行撤销操作：");

            // 执行
            reversedCommand.execute();

            // 把弹栈的操作入栈undoStack
            undoStack.addLast(lastCommand);
        }
        else {
            System.out.println("没有操作可以撤销。");
        }

        super.execute();
    }
}
