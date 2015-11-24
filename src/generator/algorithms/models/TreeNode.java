package generator.algorithms.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class TreeNode {
	public enum State {
		EMPTY, PARTIAL, FULL
	}

	private class TreeNodeComparator implements Comparator<TreeNode> {

		private HeightInfo e;

		public TreeNodeComparator(HeightInfo e) {
			this.e = e;
		}

		@Override
		public int compare(TreeNode node, TreeNode node2) {
			if (node2 == null) {
				return 1;
			}
			if (node == null) {
				return -1;
			}
			double range = getLength2D(e.getX(), node.mid[0], e.getZ(), node.mid[1]) + e.getRange();
			double range2 = getLength2D(e.getX(), node2.mid[0], e.getZ(), node2.mid[1]) + e.getRange();
			return Double.compare(range, range2);
		}
	}

	private State state = State.EMPTY;
	private double range;
	private static final short NE = 0;
	private static final short NW = 1;
	private static final short SE = 2;
	private static final short SW = 3;
	private double[] mid = new double[2];
	private TreeNode[] children = new TreeNode[4];
	private TreeNode parent = null;
	private static short LEVELS;
	private int level;
	private double[] ne = new double[2];
	private double[] nw = new double[2];
	private double[] se = new double[2];
	private double[] sw = new double[2];

	private static void setPoint(double[] tab, double x, double z) {
		tab[0] = x;
		tab[1] = z;
	}

	public static double getLength2D(double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	private boolean shouldBeInNextNode(TreeNode node, HeightInfo e) {
		return node != null && node.state != State.FULL && node.level < LEVELS && e.getRange() < node.range;
	}

	private boolean shouldBeInNextNodeWithPosition(TreeNode node, HeightInfo e) {
		return node != null && node.state != State.FULL && node.level < LEVELS && e.getRange() < node.range
				&& getLength2D(e.getX(), node.mid[0], e.getZ(), node.mid[1]) + e.getRange() < node.range;
	}

	public static TreeNode createTree(double width, double height, short depth) {
		TreeNode n = initRoot(width, height, depth);
		fill(n);
		return n;
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

	private List<TreeNode> getChildren(HeightInfo e) {
		List<TreeNode> list = Arrays.asList(children);
		list.sort(new TreeNodeComparator(e));
		return list;
	}

	public TreeNode findPlace(HeightInfo e) {
		TreeNode node = this;
		Stack<TreeNode> stack = new Stack<>();
		stack.push(node);
		while (shouldBeInNextNodeWithPosition(node, e)) {
			node = node.getChild(e);
			if (isNotFull(node)) {
				stack.push(node);
			}
		}
		if (!isExcellentNode(e, node)) {
			node = findAny(stack, e);
		}
		return node;
	}

	private static boolean isNotFull(TreeNode node) {
		return node != null && node.state != State.FULL;
	}

	private static boolean isExcellentNode(HeightInfo e, TreeNode n) {
		return n != null && n.getRange() / 2 <= e.getRange() && n.getRange() >= e.getRange() && n.state == State.EMPTY;
	}

	private TreeNode findFirst(HeightInfo e) {
		Stack<TreeNode> stack = new Stack<>();
		stack.push(this);
		do {
			TreeNode node = stack.pop();
			if (!isNotFull(node)) {
				continue;
			}
			List<TreeNode> nodes = node.getChildren(e);
			for (TreeNode n : nodes) {
				if (!isNotFull(n)) {
					continue;
				}
				if (isExcellentNode(e, n)) {
					return n;
				}
				if (shouldBeInNextNode(n, e)) {
					stack.push(n);
				}
			}
		} while (!stack.empty());

		return null;
	}

	private TreeNode findAny(Stack<TreeNode> stack, HeightInfo e) {
		TreeNode node = null;
		List<TreeNode> reserve = new ArrayList<>();
		do {
			node = stack.pop();
			if (!isNotFull(node)) {
				continue;
			}
			List<TreeNode> list = node.getChildren(e);
			for (TreeNode tmp : list) {
				if (isExcellentNode(e, tmp)) {
					return tmp;
				}
				TreeNode result = tmp.findFirst(e);
				if (isExcellentNode(e, result)) {
					return result;
				} else if (isNotFull(result)) {
					reserve.add(result);
				}
			}
		} while (!stack.isEmpty());
		if (!reserve.isEmpty()) {
			reserve.sort(new TreeNodeComparator(e));
			System.out.println("reserve");
			return reserve.get(0);
		}
		return null;
	}

	public static void mark(TreeNode node) {
		if (node == null) {
			return;
		}
		node.state = State.FULL;
		node.markParent();
	}

	private void markParent() {
		if (parent == null) {
			return;
		}
		parent.state = State.PARTIAL;
		parent.markParent();

	}

	private TreeNode(TreeNode node, short part) {
		double w = getLength2D(node.ne, node.nw) / 4.0;
		double h = getLength2D(node.ne, node.se) / 4.0;
		range = node.range / 2.0;
		parent = node;

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
		str.append("[X: ").append(mid[0]).append(", Z: ").append(mid[1]).append(", range: ").append(range).append(", level: ")
				.append(level).append(", state: ").append(state).append("]");
		return str.toString();
	}

	public State getState() {
		return state;
	}
}
