package src.command.commandImpl;

import src.command.Operator;
import src.context.FileEditorContext;
import src.utils.FileEditorConstants;
import src.utils.FileUtils;

import java.io.File;

/**
 * @author zyc
 * @version 1.0
 */
public class SaveCommand extends AbstractCommand {
    public SaveCommand(FileEditorContext ctx, String originCommand) {
        super(originCommand);
        this.ctx = ctx;
        subject.addObserver(FileEditorContext.getContext().getCommandLogger());
    }

    @Override
    public void execute() throws Exception {
        FileEditorContext context = FileEditorContext.getContext();
        String activeFile = context.getActiveFile();
        String tmpFile = activeFile + FileEditorConstants.TMP_SUFFIX;
        // 将临时文件内容全部拷贝入真实文件中
        context.getFile().close();
        FileUtils.copyThenDelete(tmpFile, activeFile);
        super.execute();
    }

    @Override
    public boolean isRecordable() {
        return true;
    }

    @Override
    public Operator reverseOperator() {
        return super.reverseOperator();
    }
}
