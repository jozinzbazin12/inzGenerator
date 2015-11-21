package generator.algorithms.models;

import java.util.List;
import java.util.Stack;

public class TreeNode {
	private double range;
	private static final short NE = 0;
	private static final short NW = 1;
	private static final short SE = 2;
	private static final short SW = 3;
	private double[] mid;
	private TreeNode[] children;
	private static short LEVELS;
	private int level;
	private double[] ne;
	private double[] nw;
	private double[] se;
	private double[] sw;

	private static void setPoint(double[] tab, double x, double z) {
		tab[0] = x;
		tab[1] = z;
	}

	private static double getLength2D(double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	private void init() {
		ne = new double[2];
		nw = new double[2];
		se = new double[2];
		sw = new double[2];
		mid = new double[2];
		children = new TreeNode[4];
	}

	private boolean shouldBeInNextNode(TreeNode node, HeightInfo e) {
		return node != null && node.level < LEVELS && e.getRange() < node.range
				&& getLength2D(e.getX(), node.mid[0], e.getZ(), node.mid[1]) + e.getRange() < node.range;
	}

	private boolean shouldBeInAnyNode(TreeNode node, HeightInfo e) {
		return node != null && node.level < LEVELS && e.getRange() < node.range;
	}

	public static TreeNode createTree(double width, double height, short depth) {
		TreeNode n = initRoot(width, height, depth);
		fill(n);
		return n;
	}

	public static TreeNode createTree(double width, double height, List<HeightInfo> space, short depth) {
		TreeNode n = initRoot(width, height, depth);
		n.fill(space);
		return n;
	}

	private void fill(List<HeightInfo> space) {
		for (HeightInfo h : space) {
			addSpace(h);
		}
	}

	private void addSpace(HeightInfo height) {
		TreeNode node = this;
		while (shouldBeInNextNode(node, height)) {
			node = node.getOrCreateChild(height);
		}
	}

	private static TreeNode initRoot(double width, double height, short depth) {
		LEVELS = depth;
		TreeNode n = new TreeNode();
		double w = width / 2.0;
		double h = height / 2.0;
		setPoint(n.mid, 0, 0);
		setPoint(n.ne, w, h);
		setPoint(n.nw, -w, h);
		setPoint(n.se, w, -h);
		setPoint(n.sw, -w, -h);
		n.range = getLength2D(n.ne, n.mid);
		n.level = 0;
		return n;
	}

	private static void fill(TreeNode parent) {
		for (short i = 0; i < parent.children.length; i++) {
			TreeNode treeNode = new TreeNode(parent, i);
			parent.children[i] = treeNode;
			if (parent.level < LEVELS) {
				fill(treeNode);
			}
		}
	}

	private static double getLength2D(double[] p1, double[] p2) {
		return getLength2D(p1[0], p2[0], p1[1], p2[1]);
	}

	private TreeNode getOrCreateNode(short index) {
		if (children[index] != null) {
			children[index] = new TreeNode(this, index);
		}
		return children[index];
	}

	private TreeNode getOrCreateChild(HeightInfo e) {
		double midX = mid[0];
		double midZ = mid[1];
		double x = e.getX();
		double z = e.getZ();
		if (x >= midX && z >= midZ) {
			return getOrCreateNode(NE);
		}
		if (x <= midX && z > midZ) {
			return getOrCreateNode(NW);
		}
		if (x > midX && z <= midZ) {
			return getOrCreateNode(SE);
		}
		if (x <= midX && z < midZ) {
			return getOrCreateNode(SW);
		}
		return null;
	}

	private TreeNode getChild(HeightInfo e) {
		double midX = mid[0];
		double midZ = mid[1];
		double x = e.getX();
		double z = e.getZ();
		if (x >= midX && z >= midZ) {
			return children[NE];
		}
		if (x <= midX && z > midZ) {
			return children[NW];
		}
		if (x > midX && z <= midZ) {
			return children[SE];
		}
		if (x <= midX && z < midZ) {
			return children[SW];
		}
		return null;
	}

	private TreeNode getChild() {
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null) {
				return children[i];
			}
		}
		return null;
	}

	public TreeNode findPlace(HeightInfo e) {
		TreeNode node = this;
		TreeNode parent = this;
		Stack<TreeNode> stack = new Stack<>();
		stack.push(node);
		while (shouldBeInNextNode(node, e)) {
			parent = node;
			node = node.getChild(e);
			if (node != null) {
				stack.push(node);
			}
		}
		if (node == this || node == null) {
			node = findAny(stack, e);
		} else {
			parent.delete(node);
		}
		System.out.println("Found " + (node != null ? node.level : null));
		return node;
	}

	private TreeNode findFirst(TreeNode parent, HeightInfo e) {
		TreeNode node = this;
		TreeNode prev = parent;
		TreeNode prev2 = parent;
		while (shouldBeInAnyNode(node, e)) {
			prev2 = prev;
			prev = node;
			node = node.getChild();
		}
		if (node == null) {
			prev2.delete(prev);
			return prev;
		}
		prev.delete(node);
		return node;
	}

	private TreeNode findAny(Stack<TreeNode> stack, HeightInfo e) {
		TreeNode node = null;
		while (!stack.isEmpty()) {
			node = stack.pop();
			for (int i = 0; i < node.children.length; i++) {
				TreeNode tmp = node.children[i];
				if (tmp != null) {
					TreeNode result = tmp.findFirst(node, e);
					if (result != null && result != this) {
						return result;
					}
				}
			}
		}
		return null;
	}

	private void delete(TreeNode node) {
		if (node == null) {
			return;
		}
		for (int i = 0; i < children.length; i++) {
			if (node.equals(children[i])) {
				children[i] = null;
				return;
			}
		}
	}

	private TreeNode(TreeNode node, short part) {
		init();
		double w = getLength2D(node.ne, node.nw) / 4.0;
		double h = getLength2D(node.ne, node.se) / 4.0;
		range = node.range / 2.0;

		if (part == NE) {
			setPoint(mid, node.mid[0] + w, node.mid[1] + h);
		}
		if (part == NW) {
			setPoint(mid, node.mid[0] - w, node.mid[1] + h);
		}
		if (part == SE) {
			setPoint(mid, node.mid[0] + w, node.mid[1] - h);
		}
		if (part == SW) {
			setPoint(mid, node.mid[0] - w, node.mid[1] - h);
		}

		setPoint(ne, mid[0] + w, mid[1] + h);
		setPoint(nw, mid[0] - w, mid[1] + h);
		setPoint(se, mid[0] + w, mid[1] - h);
		setPoint(sw, mid[0] - w, mid[1] - h);

		level = node.level + 1;
	}

	private TreeNode() {
		init();
	}

	public int getLevel() {
		return level;
	}

	public double getRange() {
		return range;
	}

	public double[] getMid() {
		return mid;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[X: ").append(mid[0]).append(", Z: ").append(mid[1]).append(", range: ").append(range).append("]");
		return str.toString();
	}
}
