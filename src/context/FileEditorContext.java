package src.context;

import src.command.commandImpl.AbstractCommand;
import src.log.CommandLogger;

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

    Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    private CommandLogger commandLogger = new CommandLogger();
    public CommandLogger getCommandLogger() {
        return commandLogger;
    }
}
