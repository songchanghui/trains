import com.thoughtworks.graphx.Graph;
import com.thoughtworks.graphx.factory.GraphProxyFactory;
import com.thoughtworks.graphx.trains.TrainsAPI;
import com.thoughtworks.graphx.trains.impl.TrainsImpl;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The local commuter railroad services a number of towns in Kiwiland.
 * Because of monetary concerns, all of the tracks are 'one-way.' That is,
 * a route from Kaitaia to Invercargill does not imply the existence of a route from Invercargill to Kaitaia.
 * In fact, even if both of these routes do happen to exist, they are distinct and are not necessarily the same distance!
 * The purpose of this problem is to help the railroad provide its customers with information about the routes.
 * In particular, you will compute the distance along a certain route, the number of different routes between two towns,
 * and the shortest route between two towns.
 * Input:  A directed graph where a node represents a town and an edge represents a route between two towns.
 *         The weighting of the edge represents the distance between the two towns.
 *         A given route will never appear more than once, and for a given route,
 *         he starting and ending town will not be the same town.
 * Output: For test input 1 through 5, if no such route exists, output 'NO SUCH ROUTE'.
 *         Otherwise, follow the route as given; do not make any extra stops!
 *         For example, the first problem means to start at city A, then travel directly to city B (a distance of 5), then directly to city C (a distance of 4).
 *
 * Created by songchanghui on 2019/2/13.
 */
public class MainTest {
    public static Logger logger = Logger.getLogger(MainTest.class.toString());
    /**
     * 主测试方法
     *
     * 1.The distance of the route A-B-C.
     * 2.The distance of the route A-D.
     * 3.The distance of the route A-D-C.
     * 4.The distance of the route A-E-B-C-D.
     * 5.The distance of the route A-E-D.
     * 6.The number of trips starting at C and ending at C with a maximum of 3 stops.  In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
     * 7.The number of trips starting at A and ending at C with exactly 4 stops.  In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
     * 8.The length of the shortest route (in terms of distance to travel) from A to C.
     * 9.The length of the shortest route (in terms of distance to travel) from B to B.
     * 10.The number of different routes from C to C with a distance of less than 30.  In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
     */
    @Test
    public void mtest(){
        logger.info("动态代理工厂->创建GraphLoader,切面构建Graph");
        List<String[]> input = new ArrayList<>();
        //1.The distance of the route A-B-C.
        input.add(new String[]{"A", "B", "C"});
        //2.The distance of the route A-D.
        input.add(new String[]{"A", "D"});
        //3.The distance of the route A-D-C.
        input.add(new String[]{"A", "D", "C"});
        //4.The distance of the route A-E-B-C-D.
        input.add(new String[]{"A", "E", "B" , "C" ,"D"});
        //5.The distance of the route A-E-D.
        input.add(new String[]{"A", "E", "D"});
        //6.The number of trips starting at C and ending at C with a maximum of 3 stops.
        input.add(new String[]{"C", "C", "3"});
        //7.The number of trips starting at A and ending at C with exactly 4 stops.
        input.add(new String[]{"A", "C", "4"});
        //8.The length of the shortest route (in terms of distance to travel) from A to C.
        input.add(new String[]{"A", "C"});
        //9.The length of the shortest route (in terms of distance to travel) from B to B.
        input.add(new String[]{"B", "B"});
        //The number of different routes from C to C with a distance of less than 30.
        input.add(new String[]{"C", "C" ,"30"});
        List<String> output = new ArrayList<>();
        try {
            logger.info("创建TrainsAPI 实例");
            TrainsAPI trainsAPI = new TrainsImpl();
            //1.The distance of the route A-B-C.
            output.add(trainsAPI.getDistance(input.get(0)));
            //2.The distance of the route A-D.
            output.add(trainsAPI.getDistance(input.get(1)));
            //3.The distance of the route A-D-C.
            output.add(trainsAPI.getDistance(input.get(2)));
            //4.The distance of the route A-E-B-C-D.
            output.add(trainsAPI.getDistance(input.get(3)));
            //5.The distance of the route A-E-D.
            output.add(trainsAPI.getDistance(input.get(4)));
            //6.The number of trips starting at C and ending at C with a maximum of 3 stops.  In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
            output.add(trainsAPI.getTripsMaxStops(input.get(5)[0],input.get(5)[1],Integer.parseInt(input.get(5)[2])));
            //7.The number of trips starting at A and ending at C with exactly 4 stops.  In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
            output.add(trainsAPI.getTripsExactlyStops(input.get(6)[0],input.get(7)[1],Integer.parseInt(input.get(6)[2])));
            //8.The length of the shortest route (in terms of distance to travel) from A to C.
            output.add(trainsAPI.getShortestDistance(input.get(7)[0],input.get(7)[1]));
            //9.The length of the shortest route (in terms of distance to travel) from B to B.
            output.add(trainsAPI.getShortestDistance(input.get(8)[0],input.get(8)[1]));
            //10.The number of different routes from C to C with a distance of less than 30.  In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
            output.add(trainsAPI.getTripsMaxDistance(input.get(9)[0],input.get(9)[1],Integer.parseInt(input.get(9)[2])));
            logger.info(input.toString());
            logger.info(output.toString());
        } catch (IllegalAccessException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } catch (URISyntaxException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }

    }
}
