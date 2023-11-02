package command.commandImpl;

import command.Operator;
import command.RecordManner;
import context.FileEditorContext;
import log.Subject;

import java.util.Date;
import java.util.Deque;

/**
 * 集成Operator与RecordManner两个接口
 * （为啥不把这两个接口里的方法全部合为一体，成为一个接口？因为我觉得这两者具有逻辑上的不同性质，所以就拆开了，而AbstractCommand就是这两个逻辑的结合体）
 * @author zyc
 * @version 1.0
 */
public abstract class AbstractCommand implements Operator, RecordManner {
    String originCommand;
    public FileEditorContext ctx;
    private boolean isLoggable = true;
    Subject subject = new Subject();
    public AbstractCommand(String originCommand) {
        this.originCommand = originCommand;
        subject.addObserver(FileEditorContext.getContext().getCommandLogger());
    }

    public void setLoggable(boolean loggable) {
        isLoggable = loggable;
    }

    public String getOriginCommand() {
        return originCommand;
    }

    public void setOriginCommand(String originCommand) {
        this.originCommand = originCommand;
    }

    @Override
    public void execute() throws Exception {
        if (isRecordable()) {
            Deque<AbstractCommand> executeStack = FileEditorContext.getContext().getExecuteStack();
            executeStack.addLast(this);
        }
        if (isLoggable && !this.getClass().equals(IllegalCommand.class)) {
            ctx.setLastCommandExecuteDate(new Date());
            subject.notifyObservers(this);
        }
    }

}
