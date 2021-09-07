package com.inventorymanagement;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

	static Map<String, Double> map = new HashMap<>();
	public static void main(String[] args) {
		JSONParser jsonParser = new JSONParser();
		try {
			Reader reader = new FileReader("/Users/anirudhasm/Desktop/eclipse-yml_training_workspace/InventoryManagement/data/inventory.json");
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			JSONArray array = (JSONArray) jsonObject.get("inventory");
			Iterator<JSONObject> iterator = array.iterator();
			
			while(iterator.hasNext()) {
				JSONObject jsonObject2 = iterator.next();
				String name = (String) jsonObject2.get("name");
				double price = (double) jsonObject2.get("priceperKg");
				double weight = (double) jsonObject2.get("weight");
				System.out.println("Name : " + name + " Price : " + price + "Weight :"+ weight);
				
				map.put(name, (price*weight));
			}

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		writeJson(map);
	}

	public static void writeJson(final Map<String,Double> map) {
		JSONArray array = new JSONArray();

		for (Entry<String, Double> entry : map.entrySet()) {
			JSONObject object = new JSONObject();
			object.put("name", entry.getKey());
			object.put("totalPrice", entry.getValue());

			array.add(object);

		}

		JSONObject mainObject =  new JSONObject();
		mainObject.put("results", array);


		FileWriter writer;
		try {
			writer = new FileWriter("/Users/anirudhasm/Desktop/eclipse-yml_training_workspace/InventoryManagement/data/result.json");
			writer.write(mainObject.toJSONString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(mainObject);
		

	}
}	
