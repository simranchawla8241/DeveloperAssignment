/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author simranchawla
 */
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainClass
{
    static List<Transaction> list=new ArrayList<Transaction>();
    public static void main(String[] args) 
    {
        //Reads from txt file and stores in List
        ReadAndStore((ArrayList<Transaction>) list);
        
        int tot=TotalSales((ArrayList<Transaction>) list);
        System.out.println("Total Sales of store is: "+tot);
        
        
        MontlySales((ArrayList<Transaction>) list);
          
       //Items generating most revenue in each month.
        MonthWisePopularItem((ArrayList<Transaction>) list);
        System.out.println("");
        System.out.println("Items generating most revenue each month:");
        MonthWiseRevenueGeneratingItem((ArrayList<Transaction>) list);
        
        
        //For the most popular item, find the min, max and average number of orders each month.
        System.out.println("");
        System.out.println("For the most popular item, find the min, max and average number of orders each month:");
        MinMaxAverage((ArrayList<Transaction>) list);
      
    }
    
    //Function that reads from file & stores in list & returns that list
    public static List<Transaction> ReadAndStore(ArrayList<Transaction> list)
    { 
        try  
        {  
            File file=new File("/Users/simranchawla/Documents/Data/TransactionData.txt");    //creates a new file instance  
            FileReader fr=new FileReader(file);   //reads the file  
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
            StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters  
            String line; 
            int flag=0;
            
            while((line=br.readLine())!=null)  
            {  
                if(flag==1)//condition so that code in the if donot run for first line in the file
                {  
                    sb.append(line);      //appends line to string buffer  
                    sb.append("\n"); 
                    String[] temp=line.split(",");
                    String[] d=temp[0].split("-");
                    Date date=new Date(Integer.parseInt(d[0]),Integer.parseInt(d[1]),Integer.parseInt(d[2]));
                    Transaction t=new Transaction(date,temp[1],Integer.parseInt(temp[2]),Integer.parseInt(temp[3]), Integer.parseInt(temp[4]));
                    list.add(t);
                    
                }
                flag=1;//setting flag so that above code runs for all rows except first row
                
            }  
            fr.close();    //closes the stream and release the resources       
            //System.out.println(sb.toString());   //returns a string that textually represents the object  
        }  
        catch(IOException e)  
        {  
            e.printStackTrace();  
        }  
        return list;
    }//function end
    
    //function that computes total sales
    public static int TotalSales(ArrayList<Transaction> list)
    {
        int total=0;
        Iterator it = list.iterator(); 
        while (it.hasNext()) //iterating throught list of transaction objects
        {
            Transaction t=(Transaction)it.next();
            total=total+t.Total_Price;//adding each sales value
        } 
        return total;
    }
  
    //function that computes sales month wise
    public static void MontlySales(ArrayList<Transaction> list)
    {
        Iterator it = list.iterator();
        HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
        while (it.hasNext()) //iterating throught list of transaction objects
        {
            Transaction t=(Transaction)it.next();
            int key=t.d.getMonth();
            if(!map.containsKey(key))
            {
                map.put(key, 0);
            }
            else
            {
                int val=map.get(key);
                map.put(key, val+t.Total_Price);
            }
            
        }
        for (int k : map.keySet()) 
        {
            System.out.println("Month:"+k+"-> Total Sale:"+map.get(k));
        } 
    }
    
    public static void PopularItem(ArrayList<Transaction> list)
    {
        Iterator it = list.iterator();
        HashMap<String,Integer> map=new HashMap<String,Integer>();
        while (it.hasNext()) //iterating throught list of transaction objects
        {
            Transaction t=(Transaction)it.next();
            String key=t.SKU;
            //System.out.println("SKU:"+key);
            if(!map.containsKey(key))
            {
                map.put(key, 0);
            }
            else
            {
                int val=map.get(key);
                map.put(key, val+1);
            }
        }
        System.out.println("Sale per item:");
    }
    
    public static void MonthWisePopularItem(ArrayList<Transaction> list)
    {
        for(int i=1;i<13;i++)
        {
            Iterator it = list.iterator();
            HashMap<String,Integer> map=new HashMap<String,Integer>();
            
            while (it.hasNext()) //iterating throught list of transaction objects
            {
                Transaction t=(Transaction)it.next();
                String key=t.SKU;
                if(!map.containsKey(key)) //if item is not in map
                {
                    if(t.d.getMonth()==i)
                    {
                        map.put(key, t.Quantity);
                    }
                    
                }
                else  //if item is in map
                {
                    if(t.d.getMonth()==i) 
                    {
                        int val=map.get(key);
                        map.put(key, val+t.Quantity);
                    }
                    
                }
            }
            maxQuantity(map,i);    
        }  
    }
    public static void maxQuantity(HashMap<String,Integer> map,int i)
    {
        int mx=0;
        String output="";
        for (String k : map.keySet()) 
        {
            if(map.get(k)>mx)
            {
                mx=map.get(k);
                output=k;
            }
        }
        if(output!=""){
        System.out.println("In month,"+i+ " item :"+output+" was popular item & Quatity sold:"+mx);
        }
    }
    
    //calculates revenue for the popular item
    public static void MonthWiseRevenueGeneratingItem(ArrayList<Transaction> list)
    {
        for(int i=1;i<13;i++)//for month iteration
        {
            Iterator it = list.iterator();
            HashMap<String,Integer> map=new HashMap<String,Integer>();//hasmap to store total revenue generated by each item
            
            while (it.hasNext()) //iterating throught list of transaction objects
            {
                Transaction t=(Transaction)it.next();
                String key=t.SKU;
                if(!map.containsKey(key)) //if item is not in map
                {
                    if(t.d.getMonth()==i)//storing total price per month 
                    {
                        map.put(key, t.Total_Price);//if item is not present in hashmap
                    }
                    
                }
                else  //if item is in map already
                {
                    if(t.d.getMonth()==i) 
                    {
                        int val=map.get(key); //getting the value
                        map.put(key, val+t.Total_Price);//adding new value and storing in map
                    }
                    
                }
            }
            
            maxRevenue(map,i);//finds the max price 
            
        }   
    }
    
    
    //function to find the max price 
    public static void maxRevenue(HashMap<String,Integer> map,int i)
    {
        int mx=0;
        String output="";
        for (String k : map.keySet()) 
        {
            if(map.get(k)>mx)
            {
                mx=map.get(k);
                output=k;
            }
        }
        if(output!=""){
        System.out.println("In month,"+i+ " item :"+output+" generated revenue:"+mx);//prints the max value monthwise
        }   
    }
     
    public static void MinMaxAverage(ArrayList<Transaction> list)
    {
        for(int i=1;i<13;i++)
        {
            Iterator it = list.iterator();
            HashMap<String,Integer> map=new HashMap<String,Integer>();
            
            while (it.hasNext()) //iterating throught list of transaction objects
            {
                Transaction t=(Transaction)it.next();
                String key=t.SKU;
                //System.out.println("SKU:"+key);
                
                if(!map.containsKey(key)) //if item is not in map
                {
                    if(t.d.getMonth()==i)
                    {
                        map.put(key, t.Quantity);
                    }
                    
                }
                else  //if item is in map
                {
                    if(t.d.getMonth()==i) 
                    {
                        int val=map.get(key);
                        map.put(key, val+t.Quantity);
                    }
                    
                }
            }
            MinMaxAvgPerMonth(map,i);
            
        }
    }
    public static void MinMaxAvgPerMonth(HashMap<String,Integer> map,int i)
    {
        int mx=0;
        String popular_item="";
        for (String k : map.keySet()) //this loop gets the popuplar item each month
        {
            if(map.get(k)>mx)
            {
                mx=map.get(k);
                popular_item=k;
            }
        }
        if(popular_item!="")
        {
            System.out.println("In month,"+i+ " item :"+popular_item+" generated revenue:"+mx);
        }
        
        //Max,min avg monthly for most popular item
        
        int min=999;int max=0;int avg=0;
        int sum=0;int count=0;
        
        Iterator it = list.iterator();
        while (it.hasNext()) //iterating throught list of transaction objects
        {
            Transaction t=(Transaction)it.next();
            String key=t.SKU;
            if(t.SKU==popular_item)
            {
                if(t.Quantity>max)
                    max=t.Quantity;
                if(t.Quantity<min)
                    min=t.Quantity;
                sum=sum+t.Quantity;//for average
                count++;  //for finding average
            }
        }
        if(count>0)
        {
            avg=sum/count;
        }
          
        if(min!=999)
        {
            System.out.println("In month: "+i);
            System.out.println("Min orders of item "+popular_item+":"+min);
        }
        
        
        if(max!=0)
            System.out.println("Max orders of item "+popular_item+":"+max);
        
        if(avg!=0)
            System.out.println("Avg orders of item "+popular_item+":"+avg);
    }
    
}
