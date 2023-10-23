package src.command.factory;

import src.command.commandImpl.*;
import src.context.FileEditorContext;
import src.utils.StringUtils;


/**
 * @author zyc
 * @version 1.0
 */
public class CommandFactory {
    /**
     * 该方法需要严格校验命令合法性，一旦出现一场就反悔IllegalCommand就好，然后在实际执行的时候去报错
     * @param stringCommand 来自System.in的字符串
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
                yield new SaveCommand(stringCommand);
            }
            case "insert" -> {
                // todo: 生成插入指令
                yield new UnfinishedCommand(stringCommand);
            }
            case "append-head" -> {
                // todo:
                yield new UnfinishedCommand(stringCommand);
            }
            case "append-tail" -> {
                // todo
                yield new UnfinishedCommand(stringCommand);
            }
            case "delete" -> {
                // todo
                yield new UnfinishedCommand(stringCommand);
            }
            case "undo" -> {
                // todo
                yield new UnfinishedCommand(stringCommand);
            }
            case "redo" -> {
                // todo
                yield new UnfinishedCommand(stringCommand);
            }
            case "history" -> {
                // todo
                yield new UnfinishedCommand(stringCommand);
            }
            case "stat" -> {
                // todo
                yield new UnfinishedCommand(stringCommand);
            }
            default -> new IllegalCommand(stringCommand);
        };
    }
}
