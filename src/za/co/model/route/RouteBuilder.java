package za.co.model.route;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

import za.co.app.logger.AppLogger;

import za.co.graph.source.GraphRouteWriter;
import za.co.model.entities.Position;
import za.co.model.entities.Tile;

public class RouteBuilder {

	private static AppLogger logger = AppLogger.getLogger("RouteBuilder");

	static java.util.Set<Tile> buildRoute(RouteNode source, RouteNode target) {
		Map<Position, RouteNode> openSet = new java.util.HashMap<>();
		PriorityQueue<RouteNode> pQueue = new PriorityQueue<>(100, new RouteNodeComparator());
		Map<Position, RouteNode> closeSet = new java.util.HashMap<>();
		RouteNode start = new RouteNode(source, 0, RouteGraph.calcTotalMoveCost(
				source.getNode(), target.getNode()));
		openSet.put(source.getPosition(), start);
		pQueue.add(start);
		RouteNode goal = null;
		while (openSet.size() > 0) {
			RouteNode x = pQueue.poll();
			openSet.remove(x.getPosition());
			if (x.equals(target)) {
				// found
				logger.log("Found target node " + x);
				goal = x;
				break;
			} else {

				logger.log("Search for node " + x);

				closeSet.put(x.getPosition(), x);
				Collection<RouteNode> neighbors = RouteGraph.getNeighbours(x);
				for (RouteNode neighbor : neighbors) {
					RouteNode visited = closeSet.get(neighbor.getPosition());
					if (visited == null) {
						Integer currentCost = x.getCurrentCost()
								+ RouteGraph.calcTotalMoveCost(x.getNode(),neighbor.getNode());
						RouteNode n = openSet.get(neighbor.getPosition());

						if (n == null) {
							// not in the open set
							n = new RouteNode(neighbor, currentCost, RouteGraph.calcTotalMoveCost(neighbor.getNode(),target.getNode()));
							n.setCameFrom(x);
							openSet.put(neighbor.getPosition(), n);
							pQueue.add(n);
						} else if (currentCost < n.getCurrentCost()) {
							// Have a better route to the current node, change
							// its parent
							n.setCameFrom(x);
							n.setCurrentCost(currentCost);
							n.setTargetCost(RouteGraph.calcTotalMoveCost(neighbor.getNode(), target.getNode()));
						}
					}
				}
			}
		}

		// the target has been identified, so traverse the path
		if (goal != null) {
			Stack<Tile> stack = new Stack<Tile>();
			java.util.Set<Tile> set = new java.util.HashSet<>();
			stack.push(goal.getNode());
			RouteNode parent = goal.getCameFrom();
			while (parent != null) {
				stack.push(parent.getNode());
				parent = parent.getCameFrom();
			}

			logger.log("Constructing search path: ");

			while (stack.size() > 0) {
				logger.log("\t" + stack.peek());
				set.add(stack.pop());
			}
			return set;
		}

		return null;
	}
	
	static void start(){
		RouteGraph graph = new RouteGraph();
		java.util.Set<Tile> set = buildRoute(graph.getStartNode(), graph.getTargetNode());
		writeResult(set, graph);
	}
	
	static void writeResult(java.util.Set<Tile> set, RouteGraph graph){
		if (set == null || set.size() < 1){
			return;
		}
		try {
			new GraphRouteWriter().write(set, graph);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		start();
	}

}
