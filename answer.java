package GIS3;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class question2 {
    public static void main(String[] args) throws Exception{

        //definition and declarations
        String csvFile1="/Users/Moumita/Downloads/GIS-3/Question 2/categories.csv";
        String csvFile2="/Users/Moumita/Downloads/GIS-3/Question 2/expenses.csv";
        Set<String> expensibleItems=new HashSet<>();
        List<Expense> expensesList=new ArrayList<>(); //for holding objects of expense table
        List<String> result=new ArrayList<>(); //for output
        Map<String,Double> seen=new LinkedHashMap<>(); //for cost calculation with same location and date


        expensibleItems=readCategoriesFile(csvFile1);
        expensesList=readExpensesFile(csvFile2,expensibleItems);

        Collections.sort(expensesList, new Comparator<Expense>(){
            @Override
            public int compare(Expense exp1,Expense exp2){
                if(exp1.date.compareTo(exp2.date) ==0){
                    //if dates are same
                    String loc1= exp1.location;
                    String loc2= exp2.location;

                    return loc1.compareTo(loc2); //lexicographically sorted location
                }

                return exp1.date.compareTo(exp2.date);
            }
        }); //for sorting the list with location and date

        calculateCost(expensesList,seen); //gives us the map
        //System.out.println("Map : "+ seen);
        //writing to a file and saving it.
		//Also, adding the output to a list
        FileWriter fw=new FileWriter("/Users/Moumita/Downloads/GIS-3/Question 2/output.txt");

        for(String key:seen.keySet()){
            String output=key+" - $"+ String.valueOf(seen.get(key));
            fw.write(output); //writing to an output file
            fw.write("\n");
            result.add(output);
        }
        fw.close();
        //System.out.println("Result : "+ result);

    }

    private static void calculateCost(List<Expense> expensesList,Map<String,Double> seen) {
        double newCost=0.0;
        String newDate="";
        String newLoc="";
        SimpleDateFormat formatter=new SimpleDateFormat("MM/dd/yy");
        for(int i=0;i<expensesList.size();i++){
            newDate=formatter.format(expensesList.get(i).date);
            newLoc=expensesList.get(i).location;
            String id=newDate+": "+newLoc;
            newCost=expensesList.get(i).cost;
            //System.out.println("List members -  " + newLoc+".."+newDate+".."+newCost);
            if(seen.containsKey(id)){
                seen.put(id,(seen.get(id)+newCost));

            }
            else{
                seen.put(id,newCost);
            }


        }
        return;
    }

    private static List<Expense> readExpensesFile(String csvFile2, Set<String> expensibleItems) throws Exception {
        List<Expense> expList=new ArrayList<>(); //for holding objects of expense table
        BufferedReader br = new BufferedReader(new FileReader(csvFile2));
        String line="";
        br = new BufferedReader(new FileReader(csvFile2));
        while ((line = br.readLine()) != null){
            String[] expense= line.split(",");
            String l=expense[0];
            Date d=new SimpleDateFormat("MM/dd/yy").parse(expense[1]);

            Double cost=Double.parseDouble(expense[3]);
            String id=expense[4];
            if(expensibleItems.contains(id)){
                Expense exp=new Expense(l,d,cost); //creating an object for expense table
                expList.add(exp);
                //System.out.println("Date: "+ exp.)
            }
        }

        return expList;

    }

    private static Set<String> readCategoriesFile(String csvFile1) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(csvFile1));
        Set<String> idSet=new HashSet<>(); //set for ids of expensible items
        String line="";
        while ( (line=br.readLine()) != null) {
            // comma as separator
            String[] category= line.split(",");
            if(category[2].equals("Y")){
                idSet.add(category[0]);
            }
        }
        return  idSet;

    }
}
class Expense{
    String location ;
    Date date;
    Double cost;

    Expense(String loc, Date d, double c){
        location=loc;
        date=d;
        cost=c;

    }

}
