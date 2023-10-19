package src.command.factory;

import src.command.commandImpl.AbstractCommand;
import src.command.commandImpl.IllegalCommand;
import src.command.commandImpl.LoadCommand;
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
            return new IllegalCommand();
        }
        // todo: 增加可以支持的command
        String[] split = stringCommand.split("\\s+");
        String operation = split[0];
        switch (operation) {
            case "load":
                if (split.length != 2) {
                    return new IllegalCommand();
                }
                return new LoadCommand(FileEditorContext.getContext(), split[1]);
        }
        return new IllegalCommand();
    }
}
