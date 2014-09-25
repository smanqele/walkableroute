package za.co.model.route;

import java.util.Comparator;

public class RouteNodeComparator implements Comparator<RouteNode> {

	public int compare(RouteNode first, RouteNode second) {
        if(first.getFullCost() < second.getFullCost()){
            return -1;
        }else if(first.getFullCost() > second.getFullCost()){
            return 1;
        }else{
            return 0;
        }
	}

}
