package src.main;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TestInsert {
    public static void main(String[] args) {
        // 指定要操作的文件路径
        String filePath = "example.md";
        int targetLineNumber = 3; // 你要在第几行末尾添加新内容
        String textToAdd = "This is the new content.";

        try {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            long position = 0;
            int lineNumber = 0;
            boolean lineAppended = false;

            while (true) {
                position = file.getFilePointer();
                String line = file.readLine();

                if (line == null) {
                    // 文件末尾
                    break;
                }

                lineNumber++;

                if (lineNumber == targetLineNumber) {
                    // 在目标行末尾添加新的内容
                    file.seek(position);
                    file.writeBytes(textToAdd);
                    file.writeBytes(System.lineSeparator()); // 换行
                    lineAppended = true;
                    break;
                }
            }

            if (!lineAppended) {
                // 如果目标行不存在，将新内容添加到文件末尾
                file.seek(file.length());
                file.writeBytes(System.lineSeparator()); // 换行
                file.writeBytes(textToAdd);
                file.writeBytes(System.lineSeparator()); // 换行
            }

            file.close();
            System.out.println("内容已成功添加到文件的第 " + targetLineNumber + " 行末尾。");
        } catch (IOException e) {
            System.err.println("操作文件时出现错误：" + e.getMessage());
        }
    }
}
