# 一、模块划分
## 1.1 基本功能模块
包含输入parsing，各个Command功能实现等，放置在src/command中

## 1.2 日志模块
放置在src/log目录下

## 1.3 统计模块
放置在src/status目录下

## 1.4 其他目录说明
log目录存放日志模块中生成数据文件  
status目录中存放统计模块中的生成的数据文件

# 二、开发人员必读
1. 任何一个指令都需要实现或者重写三个方法: 
   - `public boolean isRecordable()`
   - `public void execute()`
   - `public Operator reverseOperator()`   
2. 请各位在编写自己的Command时，最后一行一定加上`super.execute()`，这条命令会根据`isRecordable()`来自动判断是否需要放进执行栈中 
3. FileEditorContext.getContext()可以不用作为参数传递，在你需要的时候调用这个方法就好
4. 每个指令创建的时候都把`stringCommand`传入构造函数里，这样方便日志模块记录(参照我的LoadCommand和SaveCommand)
5. 新指令的增加流程
   - 在src/command/commandImpl中完成实现类，这个过程需要注意构造函数的编写
   - 在src/command/factory中完成工厂函数
   - 记录测试的输入，之后zyc来汇总
6. FileEditorContext的每个属性我都设置了get和set方法，放心取用！！

关于日志模块，拟采用观察者模式。fu同学和zyc交流一下。
关于stat命令应该需要修改我的load和save命令。有问题来和zyc交流。我在LoadCommand中的第59行设置了文件打开的时候的日期，因此在save的时候可以用这个来进行计算(gpt一下如何计算两个Date对象的差值)

关于insert方法: 我在context中的存放了tmp文件的`AccessRandomFile file`这个对象，可以仿照下面的案例来实现他的要求。append-head\append-tail这些方法有很多地方的代码可以复用，我们可以把这些代码封装一下作为static方法写在util目录下的FileUtils的方法里
```java
import java.io.IOException;
import java.io.RandomAccessFile;

public class AppendTextToFileWithCursor {
    public static void main(String[] args) {
        // 指定要操作的文件路径
        String filePath = "path_to_file";
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
```
