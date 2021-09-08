package com.inventorymanagement;

import java.io.File;
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

	public static void main(String[] args) {
		JSONObject jsonObject1 = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		JSONParser parser = new JSONParser();
		Map<String,Double> map = new HashMap<>();
		try {
			Reader reader = new FileReader("/Users/anirudhasm/Desktop/eclipse-yml_training_workspace/InventoryManagement/data/inventory.json");
			JSONArray array = new JSONArray();
			jsonObject1 = (JSONObject) parser.parse(reader);
			array =(JSONArray) jsonObject1.get("inventory");
			Iterator<JSONObject> iterator = array.iterator(); //iterate values in array

			while(iterator.hasNext()) {
				jsonObject2 = iterator.next();
				String name = (String) jsonObject2.get("name");
				String type=(String) jsonObject2.get("type");
				double price = (double) jsonObject2.get("priceperKg");
				double weight = (double) jsonObject2.get("weight");
				System.out.println("Name : " + name + " Price : " + price + "Weight :"+ weight);

				map.put(name, (price*weight)); //get total value
			}
			System.out.println(map);

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		writeJson(map);
	}

	/**
	 * @param map
	 * Write into file
	 */
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
		System.out.println(mainObject);


		FileWriter writer;
		try {
			File file = new File("/Users/anirudhasm/Desktop/eclipse-yml_training_workspace/InventoryManagement/data/result.json");
			if(file.createNewFile()) {

			}
			else {
				System.out.println("already file exits");
			}
			writer = new FileWriter("/Users/anirudhasm/Desktop/eclipse-yml_training_workspace/InventoryManagement/data/result.json");
			writer.write(mainObject.toJSONString());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}	
