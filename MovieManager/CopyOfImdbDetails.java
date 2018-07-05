package com.ganesh.project.imdb.details;

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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class CopyOfImdbDetails {

	public static void main(String args[]) throws FileCompressionException,
			DatatypeConfigurationException, ParseException, HttpException,
			IOException, InterruptedException {

		FileInputStream file = new FileInputStream(new File("D:/GaneshArasapaharan/Docs/imdb/Movie Details/Animation.xls"));
		int loopCount = 0;
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Iterator rowIterator = sheet.iterator();
		String key = null;
		HttpClient httpclient = new HttpClient();	
		JSONArray objectsArray = new JSONArray();
		while (rowIterator.hasNext()) {
			JSONObject object = new JSONObject();
			Row row = (Row) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (row.getRowNum() > 0) {
					loopCount++;
					cell = row.getCell(0);
					int cellType = cell.getCellType();
					if (cellType == 0) {
						Double numericKey = cell.getNumericCellValue();
						key = numericKey.toString();
					} else if (cellType == 1) {
						key = cell.getStringCellValue();
					} else if (cellType == 2) {
						Byte errorKey = cell.getErrorCellValue();
						key = errorKey.toString();
					}
					String[] arr = key.split(" ");
					int length = arr.length;
					StringBuffer query2 = new StringBuffer();
					int count = 0;
					for (String ss : arr) {
						count++;
						query2.append(ss);
						if (length != count) {
							query2.append("+");
						}
					}

					String url = "http://www.omdbapi.com/?t=".concat(query2.toString()).concat("&y=&plot=short&r=json=");
					GetMethod get = new GetMethod(url);
					get.addRequestHeader("Content-Type", "application/json");
					httpclient.executeMethod(get);
					String response = get.getResponseBodyAsString();
					if (!response.contains("Internal Server error")
							&& !response.contains("Please enter")
							&& !response.contains("Authentication parameters")
							&& !response.contains("invalid escape sequence")) {
						object = (JSONObject) JSONValue
								.parse(response);
						//System.out.println(object);
						/*sb.append(object);
						sb.append(",");*/
					}
					objectsArray.add(object);
				}
			}
		}
		/*sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		//Formatted JSON Array
		
		JSONObject formattedJsonObjects = new JSONObject();*/
		System.out.println(objectsArray);
		ActorData(objectsArray);
		
	}
	
	public static void ActorData(JSONArray inputArray){
		
		/*for (Object inputObject : inputArray) {
			String actors = inputObject.toString();
			System.out.println(actors);
		}*/
		for (int i=0; i<inputArray.size(); i++){
			 JSONObject oneObject = inputArray.getJSONObject(i);
		}
	}

}
