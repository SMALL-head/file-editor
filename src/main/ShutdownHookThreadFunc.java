package src.main;

import src.context.FileEditorContext;
import src.utils.FileEditorConstants;

import java.io.File;

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
            boolean delete = file.delete();
            if (delete) {
                System.out.println("FileEditor被关闭，但是有临时文件未保存...");
            }
        }

    }
}
