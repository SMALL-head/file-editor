package src.command.commandImpl;

import src.command.Operator;
import src.command.RecordManner;
import src.context.FileEditorContext;

/**
 * 集成Operator与RecordManner两个接口
 * （为啥不把这两个接口里的方法全部合为一体，成为一个接口？因为我觉得这两者具有逻辑上的不同性质，所以就拆开了，而AbstractCommand就是这两个逻辑的结合体）
 * @author zyc
 * @version 1.0
 */
public abstract class AbstractCommand implements Operator, RecordManner {
    FileEditorContext ctx = FileEditorContext.getContext();

    // todo： 通过观察者模式的方式触发日志记录
}
