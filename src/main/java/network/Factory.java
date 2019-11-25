package network;

public class Factory {
    public static Node generateNode(int id, boolean isBackBone, boolean isLeaf, int distFromBB) {
        return new Node(id, isBackBone, isLeaf, distFromBB);
    }

}
