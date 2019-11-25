package network;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private int id;
    private boolean isBackBone;
    private boolean isLeaf;
    private int distFromBB;
    private List<Node> nbs;
    Node BackBone;

    public Node(int id, boolean isBackBone, boolean isLeaf, int distFromBB) {
        this.id = id;
        this.isBackBone = isBackBone;
        this.isLeaf = isLeaf;
        this.distFromBB = distFromBB;
        this.nbs = new ArrayList<Node>();
    }

    public void setBackBone(Node backBone) {
        BackBone = backBone;
    }

    public int getId() {
        return id;
    }

    public boolean isBackBone() {
        return isBackBone;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public int getDistFromBB() {
        return distFromBB;
    }

    public List<Node> getNb() {
        return nbs;
    }

    public void addNeighborNode(Node nb) {
        this.nbs.add(nb);
    }
}
