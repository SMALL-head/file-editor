package command.factory;

import command.commandImpl.*;
import context.FileEditorContext;
import utils.StringUtils;


/**
 * @author zyc
 * @version 1.0
 */
public class CommandFactory {
    /**
     * 该方法需要严格校验命令合法性，一旦出现异常就返回IllegalCommand就好，在实际执行的时候会进行错误处理
     * @param stringCommand 来自System.in或者其他InputStream中的字符串
     * @return 根据字符串生成的具体Command
     */
    public static AbstractCommand generateCommand(String stringCommand) {
        if (StringUtils.isEmpty(stringCommand)) {
            return new EmptyCommand("空指令");
        }
        String[] split = stringCommand.split("\\s+");
        String operation = split[0];

        return switch (operation) {
            case "load" -> {
                if (split.length != 2) {
                    yield new IllegalCommand(stringCommand);
                }
                // 请一定把stringCommand传入，方便日志模块重新获取到这个值
                yield new LoadCommand(FileEditorContext.getContext(), split[1], stringCommand);
            }
            case "save" -> {
                if (split.length != 1) {
                    yield new IllegalCommand(stringCommand);
                }
                yield new SaveCommand(FileEditorContext.getContext(), stringCommand);
            }
            case "insert" -> {
                // 如果只有 insert 行号 ，认为插入一个空行
                if (split.length < 2) {
                    yield new IllegalCommand(stringCommand);
                }
                yield new InsertCommand(FileEditorContext.getContext(), stringCommand, true);
            }
            case "append-head" -> {
                if (split.length < 2) {
                    yield new IllegalCommand(stringCommand);
                }
                yield new AppendHeadCommand(FileEditorContext.getContext(), stringCommand, true);
            }
            case "append-tail" -> {
                if (split.length < 2) {
                    yield new IllegalCommand(stringCommand);
                }
                yield new AppendTailCommand(FileEditorContext.getContext(), stringCommand, true);
            }
            case "delete" -> {
                if (split.length < 2) {
                    yield new IllegalCommand(stringCommand);
                }
                yield new DeleteCommand(FileEditorContext.getContext(), stringCommand, true);
            }
            case "undo" -> {
                if (split.length != 1) {
                    yield new IllegalCommand(stringCommand);
                }
                yield new UndoCommand(FileEditorContext.getContext(), stringCommand);
            }
            case "redo" -> {
                if (split.length != 1) {
                    yield new IllegalCommand(stringCommand);
                }
                yield new RedoCommand(FileEditorContext.getContext(), stringCommand);
            }
            case "list" -> {
                if (split.length != 1) {
                    yield new IllegalCommand(stringCommand);
                }
                yield new ListCommand(FileEditorContext.getContext(), stringCommand);
            }
            case "list-tree" -> {
                if (split.length != 1) {
                    yield new IllegalCommand(stringCommand);
                }
                yield new ListTreeCommand(FileEditorContext.getContext(), stringCommand);
            }
            case "dir-tree" -> {
                if (split.length == 1) {
                    yield new ListTreeCommand(FileEditorContext.getContext(), stringCommand);
                }
                else if (split.length == 2) {
                    yield new DirTreeCommand(FileEditorContext.getContext(), stringCommand);
                }
                else {
                    yield new IllegalCommand(stringCommand);
                }
            }
            case "history" -> {
                if (split.length > 2) {
                    yield new IllegalCommand(stringCommand);
                }
                yield new HistoryCommand(FileEditorContext.getContext(), stringCommand);
            }
            case "stats" -> {
                if (split.length > 2) {
                    yield new IllegalCommand(stringCommand);
                }
                if (split[1].equals("q")) {
                    yield new IllegalCommand(stringCommand);
                }
                yield new StatsCommand(FileEditorContext.getContext(), stringCommand);
            }
            default -> new IllegalCommand(stringCommand);
        };
    }
}
