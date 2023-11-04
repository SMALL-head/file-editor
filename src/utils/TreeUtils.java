package src.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class TreeUtils {
    public static void buildTree(ArrayList<TreeNode> forest, BufferedReader bufferedReader) throws IOException {
        TreeNode root = new TreeNode(0, "root");
        TreeNode current = root;
        forest.add(root);

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            // 1. get title level
            int level = line.split(" ")[0].lastIndexOf("#") + 1;
            String text;

            // 2. title
            if (level >= 1 && level <= 5) {
                text = line.trim().substring(level).trim();
            }
            else { // 3. text
                String[] split = line.split(" ");
                level = 6;

                // 3.1. * text
                if (split[0].equals("*")) {
                    text = '·' + line.substring(2);
                }
                else { // 3.2. normal text
                    text = line;
                }
            }

            // 4. construct the tree
            TreeNode node = new TreeNode(level, text);
            if (current.getLevel() == node.getLevel()) { // 4.1. same level, new tree in forest
                current.setNextSibling(node);
            }
            else if (current.getLevel() < node.getLevel()) { // 4.2. children of the current node
                current.setFirstChildren(node);
            }
            else { // 4.3. another Tree
                TreeNode newRoot = new TreeNode(0, "root");
                forest.add(newRoot);
                newRoot.setFirstChildren(node);
            }
            current = node;
        }

        bufferedReader.close();
    }
}
