package src.command.commandImpl;

import src.context.FileEditorContext;
import src.utils.TreeNode;
import src.utils.TreeUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ListTreeCommand extends AbstractCommand{

    public ListTreeCommand(FileEditorContext ctx, String originCommand) {
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
        ArrayList<TreeNode> forest = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(tmpFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // 1. build the tree
            TreeUtils.buildTree(forest, bufferedReader);

            // 2. print the forest
            for (TreeNode treeRoot : forest) {
                treeRoot.printWholeTree();
            }
        } catch (IOException e) {
            System.err.println("无法读取文件: " + e.getMessage());
        }

        super.execute();
    }
}
