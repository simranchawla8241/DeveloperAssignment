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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainClass
{
    public static void main(String[] args) 
    {
        ReadAndStore();
    }
    
    //Function that reads from file & stores in list & returns that list
    public static List<Transaction> ReadAndStore()
    {
        List<Transaction> list=new ArrayList<Transaction>();
        try  
        {  
            File file=new File("/Users/simranchawla/Documents/Data/TransactionData.txt");    //creates a new file instance  
            FileReader fr=new FileReader(file);   //reads the file  
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
            StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters  
            String line;  
            while((line=br.readLine())!=null)  
            {  
                sb.append(line);      //appends line to string buffer  
                sb.append("\n"); 
                
            }  
            fr.close();    //closes the stream and release the resources  
            //System.out.println("Contents of File: ");  
            //System.out.println(sb.toString());   //returns a string that textually represents the object  
        }  
        catch(IOException e)  
        {  
            e.printStackTrace();  
        }  
        return list;
    }
}
