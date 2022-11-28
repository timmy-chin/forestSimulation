import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{

    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        PriorityQueue<Tuple> pqueue = new PriorityQueue<>();
        List<Point> path = new LinkedList<>();
        Map<Point, Point> originMap = new HashMap<>();
        Map<Point, Integer> distanceMap = new HashMap<>();


        pqueue.add(new Tuple(0, start));
        originMap.put(start, start);
        distanceMap.put(start, 0);

        while (pqueue.size() != 0){
            Tuple t = pqueue.remove();
            Point current = t.point;
            int actualWeight = t.weight;

            if (originMap.containsKey(current) && actualWeight < distanceMap.get(current)){
                continue;
            }


            if (withinReach.test(current, end)){
                path.add(current);
                Point prev = originMap.get(current);
                while (!prev.equals(start)){
                    path.add(0, prev);
                    prev = originMap.get(prev);


                }
                return path;

            }

            else{
                List<Point> neighbors = potentialNeighbors.apply(current)
                        .filter(canPassThrough)
                        .collect(Collectors.toList());

                for (Point neighbor:neighbors){
                    if (!originMap.containsKey(neighbor) || actualWeight + heuristic(neighbor, end) + 1 < distanceMap.get(neighbor)){
                        distanceMap.put(neighbor, actualWeight + heuristic(neighbor, end) + 1);
                        originMap.put(neighbor, current);
                        pqueue.add(new Tuple( actualWeight + heuristic(neighbor, end) + 1, neighbor));

                    }

                }
            }
        }

        return path;
    }

    private int heuristic(Point p1, Point p2){
        return (int) Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(),2));
    }

    class Tuple implements Comparable<Tuple>{
        private int weight;
        private Point point;

        public Tuple(int i, Point p){
            this.weight = i;
            this.point = p;
        }


        public int compareTo(Tuple other){
            return this.weight - other.weight;
        }

    }




}

