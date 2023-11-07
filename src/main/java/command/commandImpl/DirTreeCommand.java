package command.commandImpl;

import context.FileEditorContext;
import utils.SoutUtils;
import utils.TreeNode;
import utils.TreeUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DirTreeCommand extends AbstractCommand{

    public DirTreeCommand(FileEditorContext ctx, String originCommand) {
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
        // 1. parse the target dir
        String[] split = originCommand.split(" ");
        String targetDir = split[1];

        String tmpFilePath = this.ctx.getActiveFile() + ".tmp";
        ArrayList<TreeNode> forest = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(tmpFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // 2. build the tree
            TreeUtils.buildTree(forest, bufferedReader);

            // 3. find the target dir
            TreeNode subTree = null;
            for (TreeNode treeNode : forest) {
                subTree = treeNode.findSubTree(targetDir);
                if (subTree != null) {
                    break;
                }
            }

            // 4. print it if found
            if (subTree == null) {
                SoutUtils.sout("无法找到目标文件夹");
            }
            else {
                subTree.printSubTree();
            }

        } catch (IOException e) {
            SoutUtils.sout("无法读取文件: " + e.getMessage());
        }


        super.execute();
    }
}
