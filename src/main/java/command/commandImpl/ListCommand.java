package command.commandImpl;

import context.FileEditorContext;
import utils.SoutUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ListCommand extends AbstractCommand {
    public ListCommand(FileEditorContext ctx, String originCommand) {
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
        String tmpFilePath = this.ctx.getActiveFile() + ".tmp";
        try {
            FileReader fileReader = new FileReader(tmpFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                SoutUtils.sout(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            SoutUtils.sout("无法读取文件: " + e.getMessage());
        }

        super.execute();
    }
}