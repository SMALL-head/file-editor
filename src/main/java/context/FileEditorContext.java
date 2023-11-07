package context;

import command.commandImpl.AbstractCommand;
import log.CommandLogger;
import stats.SessionStats;

import java.io.RandomAccessFile;
import java.util.Date;
import java.util.Deque;

/**
 * @author zyc
 * @version 1.0
 */
public class FileEditorContext {
    final static FileEditorContext singleton = new FileEditorContext();

    private FileEditorContext() {
    }

    public static FileEditorContext getContext() {
        return singleton;
    }

    RandomAccessFile file;
    /**
     * 该文件记录的路径名是不带有.tmp的
     * 若有activeFile，那么executeStack也应该存在。
     */
    String activeFile;

    /**
     * 上一次Command操作的时间
     */
    Date lastCommandExecuteDate;

    Deque<AbstractCommand> executeStack;
    Deque<AbstractCommand> undoStack;

    public RandomAccessFile getFile() {
        return file;
    }

    public void setFile(RandomAccessFile file) {
        this.file = file;
    }

    public String getActiveFile() {
        return activeFile;
    }

    public void setActiveFile(String activeFile) {
        this.activeFile = activeFile;
    }

    public Deque<AbstractCommand> getExecuteStack() {
        return executeStack;
    }

    public void setExecuteStack(Deque<AbstractCommand> executeStack) {
        this.executeStack = executeStack;
    }

    public Deque<AbstractCommand> getUndoStack() { return undoStack; }

    public void setUndoStack(Deque<AbstractCommand> undoStack) { this.undoStack = undoStack; }

    public Date getLastCommandExecuteDate() {
        return lastCommandExecuteDate;
    }

    public void setLastCommandExecuteDate(Date lastCommandExecuteDate) {
        this.lastCommandExecuteDate = lastCommandExecuteDate;
    }

    private final CommandLogger commandLogger = new CommandLogger();
    public CommandLogger getCommandLogger() {
        return commandLogger;
    }

    private final SessionStats sessionStats = new SessionStats();
    public SessionStats getSessionStats() {
        return sessionStats;
    }
}
