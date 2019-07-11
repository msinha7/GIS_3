package GIS3;

import java.io.FileWriter;
import java.util.*;

public class Question1 {
    public static void main(String args[]){
        String outputFile="/Users/Moumita/Downloads/GIS-3/Question1/output.txt";
        Map<Integer,Point> points=new HashMap<>();
        points.put(1,new Point(0.0,0.0));
        points.put(2,new Point(10.1,-10.1));
        points.put(3,new Point(-12.2,12.2));
        points.put(4,new Point(38.3,38.3));
        points.put(5,new Point(79.99,179.99));
        // points.put(6,new Point(19.0,-19.0));
        List<Integer> closestPointsList=new ArrayList<>();
        //System.out.println("Map::"+ points);
        Solution sol=new Solution();
        try{
            FileWriter fw=new FileWriter(outputFile);
            for(int key1: points.keySet()){
                //System.out.println("Key:"+ key1);
                closestPointsList=sol.kClosestPoints(points,key1);
                sol.writeFile(closestPointsList,key1,fw);
                fw.write("\n");

            }
            fw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
class Solution {
    class Result{
        int id;
        double distance;
        Result(int id,double dis){
            this.id=id;
            distance=dis;
        }
    }
    public  void writeFile(List<Integer> inputList,Integer id,FileWriter fw) throws Exception{

        String output=id+" ";
        StringBuffer tempBuffer=new StringBuffer();
        for(Integer temp:inputList){
            tempBuffer.append(temp);
            tempBuffer.append(",");
        }
        tempBuffer.deleteCharAt(tempBuffer.length()-1);
        output=output+tempBuffer.toString();
        fw.write(output); //writing to an output file
       // System.out.println("OutPut String : " + output);
        //fw.write("\n");
        // fw.close();

    }
    public  List<Integer> kClosestPoints(Map<Integer,Point> points,Integer src){
        List<Integer> result=new ArrayList<>();
        PriorityQueue<Result> minHeap = new PriorityQueue<>(new Comparator<Result>(){

            @Override
            public int compare(Result r1,Result r2){
                return (int) ( r1.distance-r2.distance);

            }
        });


        for(int key : points.keySet()){
            if(src!=key){
                //distance between two points
                double distance=getDistance(points.get(src),points.get(key));
               // System.out.println("Distance from src " + src+ "is" + distance + "destination " +key );
                minHeap.offer(new Result(key,distance));
                //maxHeap.offer

            }

        }

        while(result.size()<3){
            result.add(minHeap.poll().id);
        }
       // System.out.println("Result List :" + result);
        return result;
        //}

    }
    public  double getDistance(Point p1,Point p2){
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

}class Point{
    double x;
    double y;
    Point(double x,double y){
        this.x=x;
        this.y=y;
    }
}