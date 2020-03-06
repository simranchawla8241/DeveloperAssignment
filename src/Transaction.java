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
import java.util.Date;

public class Transaction 
{
    Date d=new Date();
    String SKU="";
    int Unit_Price;int Quantity;int Total_Price;
             
   Transaction(Date d,String SKU,int Unit_Price,int Quantity,int Total_Price)
   {
       this.d=d;
       this.SKU=SKU;
       this.Total_Price=Total_Price;
       this.Quantity=Quantity;
       this.Unit_Price=Unit_Price;
   }
    
}
