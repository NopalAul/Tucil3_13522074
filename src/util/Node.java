package util;

public class Node  {
    private String word;
    private Node parent;
    private int fn;

    public Node(String word, Node parent, int fn) {
        this.word = word;
        this.parent = parent;
        this.fn = fn;
    }

    public String getWord() {
        return word;
    }

    public Node getParent() {
        return parent;
    }

    public int getFn() {
        return fn;
    }

    public void setFn(int fn) {
        this.fn = fn;
    }

    public int countCost() {
        int cost = 0;
        Node current = this;
        while (current.getParent() != null) {
            cost++;
            current = current.getParent();
        }
        return cost;
    }
}
