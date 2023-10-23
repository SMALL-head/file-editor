package src.command;

/**
 * 注意到某些操作需要记录在执行栈中，某些操作无需记录，因此我们创建了这个接口
 * @author zyc
 * @version 1.0
 */
public interface RecordManner {
    /**
     *
     * @return 若该操作需要记录在redo/undo log中则返回true；否则返回false
     */
    boolean isRecordable();
    /**
     * 获得撤销操作时该操作对应的相反操作，比如insert命令对应的reverse操作为delete。
     * 默认操作为返回
     * @return reversed Operator
     */
    default Operator reverseOperator() {
        return null;
    };
}
