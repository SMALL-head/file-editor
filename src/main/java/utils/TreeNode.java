package utils;

public class TreeNode {
    String text;
    int level;
    TreeNode nextSibling;
    TreeNode firstChildren;

    public TreeNode(int level, String text) {
        this.level = level;
        this.text = text;
        this.nextSibling = null;
        this.firstChildren = null;
    }

    public int getLevel() {
        return level;
    }

    public void setNextSibling(TreeNode nextSibling) {
        this.nextSibling = nextSibling;
    }

    public void setFirstChildren(TreeNode firstChildren) {
        this.firstChildren = firstChildren;
    }

    public void printWholeTree() {
        if (this.firstChildren == null) {
            if (this.level != 0) {
                StringUtils.printSpace(this.level - 1);
                if (this.level != 6) {
                    SoutUtils.sout("├── " + text);
                }
                else {
                    SoutUtils.sout("└── " + text);
                }

            }

            // no child, print the sibling tree
            if (this.nextSibling != null) {
                this.nextSibling.printWholeTree();
            }
        }
        else {
            if (this.level != 0) {
                StringUtils.printSpace(this.level - 1);
                SoutUtils.sout("└── " + text);
            }

            // print the child
            this.firstChildren.printWholeTree();
        }
    }

    public void printSubTree() {
        if (this.firstChildren == null) {
            SoutUtils.sout("├── " + text);
        }
        else {
            SoutUtils.sout("└── " + text);

            // print the child
            StringUtils.printSpace(this.level - 1);
            this.firstChildren.printWholeTree();
        }
    }

    public TreeNode findSubTree(String target) {
        if (this.text.equals(target)) {
            return this;
        }

        if (this.firstChildren != null) {
            TreeNode resultInChild = this.firstChildren.findSubTree(target);
            if (resultInChild != null) {
                return resultInChild;
            }
        }

        if (this.nextSibling != null) {
            return this.nextSibling.findSubTree(target);
        }

        return null;
    }
}
