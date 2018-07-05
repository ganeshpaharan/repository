/**
 * 
 */
package com.ganesh.movies.details;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;

import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
/**
 * @author Ganesh Arasapaharan
 *
 */
public class ImdbDetails {
	   public static void main(String args[]) throws FileCompressionException, DatatypeConfigurationException, ParseException, HttpException, IOException, InterruptedException, JSONException {
	     
		   String FILE_HEADER = "Title,Year,IMDBRating,Genre,Director,Actors"; 
		   String COMMA_DELIMITER = ",";
		   String NEW_LINE_SEPARATOR = "\n";

		   FileInputStream file = new FileInputStream(new File("E:/Don/MovieDetails/names.xls"));
	       int loopCount = 0;
	       String quotes = "\"";
		   HSSFWorkbook workbook = new HSSFWorkbook(file);
		   HSSFSheet sheet = workbook.getSheetAt(0);
		   Iterator rowIterator = sheet.iterator();
		   String key = null;
		   HttpClient httpclient = new HttpClient();
	       FileWriter fileWriter = null;
	       fileWriter = new FileWriter("E:\\Don\\MovieDetails\\details.csv");
	       fileWriter.append(FILE_HEADER.toString());
	       fileWriter.append(NEW_LINE_SEPARATOR);
		   while(rowIterator.hasNext()) {
		   Row row = (Row) rowIterator.next();
		   Iterator<Cell> cellIterator = row.cellIterator();
	       while (cellIterator.hasNext()) {
	           Cell cell = cellIterator.next();
	           if(row.getRowNum() > 0){
	            	   loopCount++;
	            	   cell = row.getCell(0);
	                	   int cellType = cell.getCellType();
	                       if(cellType == 0) {
	                       Double numericKey = cell.getNumericCellValue();
	                       key = numericKey.toString();
	                       }else if(cellType == 1) {
	                       key = cell.getStringCellValue();
	                       }else if(cellType == 2){
	                       Byte errorKey = cell.getErrorCellValue();
	                       key = errorKey.toString();
	                       }
		                   String[] arr = key.split(" ");    
		                   int length = arr.length;
		                   StringBuffer query2 = new StringBuffer();
		                   int count = 0;
		                   for ( String ss : arr) {
		                     count++;
		                     query2.append(ss);
		                     if(length != count){
		                    	query2.append("+");
		                       }
		                   }
	                    
	                     String url = "http://www.omdbapi.com/?t=".concat(query2.toString()).concat("&y=&plot=short&r=json=");
	                     //System.out.println(url);
					       GetMethod get = new GetMethod(url);
					       get.addRequestHeader("Content-Type", "application/json");
					       //System.out.println(loopCount);
					       //System.out.println("method call");
					       httpclient.executeMethod(get);
					       String response=get.getResponseBodyAsString();
					       System.out.println(response);
					       if((!response.contains("Movie not found"))&&(!response.contains("False"))){
					       JSONObject object = (JSONObject) JSONValue.parse(response);
					       String title = object.get("Title").toString();
					           fileWriter.append(quotes.concat(object.get("Title").toString().replace("\"", "")).concat(quotes));
					           fileWriter.append(COMMA_DELIMITER);
					           fileWriter.append(quotes.concat(object.get("Year").toString().replace("\"", "")).concat(quotes));
					           fileWriter.append(COMMA_DELIMITER);
					           fileWriter.append(quotes.concat(object.get("imdbRating").toString().replace("\"", "")).concat(quotes));
					           fileWriter.append(COMMA_DELIMITER);
					           fileWriter.append(quotes.concat(object.get("Genre").toString().replace("\"", "")).concat(quotes));
					           fileWriter.append(COMMA_DELIMITER);
					           fileWriter.append(quotes.concat(object.get("Director").toString().replace("\"", "")).concat(quotes));
					           fileWriter.append(COMMA_DELIMITER);
					           fileWriter.append(quotes.concat(object.get("Actors").toString().replace("\"", "")).concat(quotes));
					           fileWriter.append(COMMA_DELIMITER);
					           fileWriter.append(NEW_LINE_SEPARATOR);
					       }else{
					    	   System.out.println("Error"+url);
					           fileWriter.append(quotes.concat(url).concat(quotes));
					           fileWriter.append(COMMA_DELIMITER);
					           fileWriter.append(NEW_LINE_SEPARATOR);
	                      }
	                }
	            }
		   } 
		   fileWriter.flush();
		   fileWriter.close();
		   System.out.println("Done");
	    }

}
