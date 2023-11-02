import context.FileEditorContext;
import utils.FileEditorConstants;
import utils.SoutUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 异常退出需要删除临时文件，这里使用钩子函数来帮忙删除
 * @author zyc
 * @version 1.0
 */
public class ShutdownHookThreadFunc implements Runnable {
    @Override
    public void run() {
        // 异常退出时删除临时文件
        String activeFile = FileEditorContext.getContext().getActiveFile();
        File file = new File(activeFile + FileEditorConstants.TMP_SUFFIX);
        if (file.exists()) {
            RandomAccessFile openFile = FileEditorContext.getContext().getFile();
            if (openFile != null) {
                try {
                    openFile.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            boolean delete = file.delete();
            if (delete) {
                SoutUtils.sout("FileEditor被关闭，但是有临时文件未保存...");
            }
        }

    }
}
