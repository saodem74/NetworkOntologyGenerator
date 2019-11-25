package network;

import java.util.*;

public class Network {
    List<Node> backBoneNodes;
    Random rd;
    Set<Integer> mark;
    Map<Node, Integer> numOfNodeInBackBoneNode;
    List<Node> allNodes;
    Map<Integer, Set<Integer>> neighbors;

    public Network() {
        backBoneNodes = new ArrayList<Node>();
        rd = new Random();
        mark  = new HashSet<Integer>();
        numOfNodeInBackBoneNode = new HashMap<Node, Integer>();
        neighbors = new HashMap<Integer, Set<Integer>>();
        allNodes = new ArrayList<>();
    }

    private int nextID() {
        int tmpID = (rd.nextInt(Config.NumOfNode)) + 1;
        while (mark.contains(tmpID))  tmpID = (rd.nextInt(Config.NumOfNode)) + 1;
        return tmpID;
    }

    private int getNumOfDegree() {
        return (rd.nextInt(Config.MaxDegreeOfNode)) + 1;
    }

    private int getRandomNum(int bound) {
        return (rd.nextInt(bound));
    }

    private void generateBackBoneNode() {
        for (int i = 0; i < Config.NumOfBackBoneNode; ++i) {
            int tmpID = nextID();
            mark.add(tmpID);
            Node newBB = Factory.generateNode(tmpID, true, true, 0);
            newBB.setBackBone(newBB);

            allNodes.add(newBB);
            backBoneNodes.add(newBB);
            neighbors.put(tmpID, new HashSet<>());
        }
    }

    private boolean shouldBeConnected(Node n1, Node n2) {
        if (n1.isBackBone()) {
            if (n2.isBackBone()) {
                return getRandomNum(2) == 0;
            } else {
                return getRandomNum(10) == 0;
            }
        } else {
            if (n2.isBackBone()) {
                return getRandomNum(2) == 0;
            } else {
                return getRandomNum(20) == 0;
            }
        }
    }
    private void generateCrossBackBone() {
        for (int i = 0; i < allNodes.size(); ++i) {
            Node ni = allNodes.get(i);
            for (int j = i + 1; j < allNodes.size(); ++j) {
                Node nj = allNodes.get(j);
                if (neighbors.get(ni.getId()).contains(nj.getId())) continue;
                boolean isConnected = shouldBeConnected(ni, nj);
                if (isConnected) {
                    ni.addNeighborNode(nj);
                    nj.addNeighborNode(ni);

                    neighbors.get(ni.getId()).add(nj.getId());
                    neighbors.get(nj.getId()).add(ni.getId());
                }
            }
        }

    }

    private void calculateNumOfNodeInBackBoneNode() {
        int num = Config.NumOfNode / Config.NumOfBackBoneNode;
        int sum = 0;

        for (int i = 0; i < backBoneNodes.size(); ++i) {
            if (i < backBoneNodes.size() - 1) {
                numOfNodeInBackBoneNode.put(backBoneNodes.get(i), num);
                sum += num;
            }
            else {
                numOfNodeInBackBoneNode.put(backBoneNodes.get(i), backBoneNodes.size() - sum);
            }

        }
    }

    private void generateInternalNode() {
        for (Node bb : backBoneNodes) {
            int cnt = 1;
            Queue<Node> queue = new LinkedList<>();
            queue.offer(bb);
            while (!queue.isEmpty()) {
                Node curr = queue.poll();
                if (curr.getDistFromBB() == Config.MaxDepthFromBackBoneNode) continue;

                int cNB = getNumOfDegree();
                while (cNB-- > 0) {
                    int tmpID = nextID();
                    mark.add(tmpID);
                    Node newNode = Factory.generateNode(tmpID, false, true, curr.getDistFromBB() + 1);
                    curr.addNeighborNode(newNode);
                    newNode.addNeighborNode(curr);
                    newNode.setBackBone(bb);

                    allNodes.add(newNode);
                    neighbors.put(tmpID, new HashSet<>());
                    neighbors.get(tmpID).add(curr.getId());
                    neighbors.get(curr.getId()).add(tmpID);

                    queue.offer(newNode);

                    ++cnt;
                    if (cnt >= numOfNodeInBackBoneNode.get(bb)) break;
                }

                if (cnt >= numOfNodeInBackBoneNode.get(bb)) break;
            }
        }
    }

    public void generate() {
        generateBackBoneNode();
        calculateNumOfNodeInBackBoneNode();
        generateInternalNode();
        generateCrossBackBone();
    }

    public void printNetworkToFile(String file) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= Config.NumOfNode; ++i) {
            Set<Integer> tmp = neighbors.getOrDefault(i, new HashSet<Integer>());
            if (tmp.size() == 0) continue;

            sb.append(i).append(" [");

            int cc = 0;
            for (int j : tmp) {
                sb.append(j);
                if (++cc!= tmp.size())
                    sb.append(", ");
            }

            sb.append("]\n");
        }

        sb.append("BackBoneNode = ");
        int cc = 0;
        for (Node bb : backBoneNodes) {
            sb.append(bb.getId());
            if (++cc != backBoneNodes.size())
                sb.append(", ");
        }

        FileWriter.writeStringToFile(file, sb.toString());
    }
}
