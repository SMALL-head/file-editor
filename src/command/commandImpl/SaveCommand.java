package src.command.commandImpl;

import src.context.FileEditorContext;
import src.utils.FileEditorConstants;
import src.utils.FileUtils;

/**
 * @author zyc
 * @version 1.0
 */
public class SaveCommand extends AbstractCommand {
    public SaveCommand(FileEditorContext ctx, String originCommand) {
        super(originCommand);
        this.ctx = ctx;
    }

    @Override
    public void execute() throws Exception {
        FileEditorContext context = FileEditorContext.getContext();
        String activeFile = context.getActiveFile();
        String tmpFile = activeFile + FileEditorConstants.TMP_SUFFIX;

        // 将临时文件内容全部拷贝入真实文件中
        context.getFile().close();
        FileUtils.copyThenDelete(tmpFile, activeFile);

        System.out.println("保存成功");

        context.setActiveFile(null);
        context.getExecuteStack().clear();
        context.getUndoStack().clear();
        context.setFile(null);

        // todo: 时间怎么算？？
        super.execute();
    }

    @Override
    public boolean isRecordable() {
        return true;
    }

    @Override
    public AbstractCommand reverseOperator() {
        return super.reverseOperator();
    }
}
