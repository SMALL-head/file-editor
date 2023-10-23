package src.command.commandImpl;

import src.command.Operator;
import src.command.RecordManner;
import src.context.FileEditorContext;
import src.utils.FileEditorConstants;

import java.util.Deque;

/**
 * 集成Operator与RecordManner两个接口
 * （为啥不把这两个接口里的方法全部合为一体，成为一个接口？因为我觉得这两者具有逻辑上的不同性质，所以就拆开了，而AbstractCommand就是这两个逻辑的结合体）
 * @author zyc
 * @version 1.0
 */
public abstract class AbstractCommand implements Operator, RecordManner {

    String originCommand;

    public AbstractCommand(String originCommand) {
        this.originCommand = originCommand;
    }

    public String getOriginCommand() {
        return originCommand;
    }

    public void setOriginCommand(String originCommand) {
        this.originCommand = originCommand;
    }
    // todo： 通过观察者模式的方式触发日志记录

    @Override
    public void execute() throws Exception {
        if (isRecordable()) {
            Deque<AbstractCommand> executeStack = FileEditorContext.getContext().getExecuteStack();
            executeStack.addLast(this);
        }
    }
}
