package Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.simple.JSONObject;

//import org.json.simple.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonValidator {

	String jsonPath = "";

	public static void main(String args[]) {
		String currentDir = System.getProperty("user.dir");
		String validJson = currentDir + "/Sample.json";
		String inValidJson = currentDir + "/Sample2.json";
		JsonValidator jsonValidator = new JsonValidator();

		System.out.println("Schema validation  ");
		System.out.println("Correct Json  " + validJson);
		JsonObject jsonObj = jsonValidator.validateJSON(validJson);
		System.out.println("InCorrect Json  " + inValidJson);
		jsonValidator.validateJSON(inValidJson);

		System.out.println("Data Type validation  ");
		jsonValidator.validateDataType(jsonObj, "firstname", "String");

		System.out.println("Key validation  ");

		jsonValidator.validateKeyValue(jsonObj, "firstname", "test");

		System.out.println("Key validation  ");

		jsonValidator.validateKeyExistance(jsonObj, "firstname");
		jsonValidator.validateKeyExistance(jsonObj, "first");

	}

	public boolean validateDataType(JsonObject jsonObject, String key, String dataType) {
		try {
			if (validateKeyExistance(jsonObject, key)) {
				Object obj = jsonObject.get(key);
				if (dataType.equalsIgnoreCase("Integer")) {
					Integer.parseInt((String) obj);
				} else if (dataType.equalsIgnoreCase("Double")) {
					Double.parseDouble((String) obj);
				} else if (dataType.equalsIgnoreCase("String")) {
					String str = (String) obj;
				}
				System.out.println("Data type validation pass");
			}
		} catch (Exception e) {
			System.out.println("Data type validation fail");
			return false;
		}
		return true;
	}

	public boolean validateKeyValue(JsonObject jsonObject, String key, Object value) {
		try {
			if (validateKeyExistance(jsonObject, key)) {
				Object obj = jsonObject.get(key);
				String temp = (String) obj;
				String temp2 = (String) value;
				if (temp.equals(temp2)) {
					System.out.println("Key Value found");
					return true;

				}
			}
			System.out.println("Key Value not found");
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	public JsonObject validateJSON(String path) {
		if (readJSON(path) == null) {
			System.out.println("This is Invalid json");
			return null;
		} else {
			System.out.println("This is valid json");
			return readJSON(path);
		}
	}

	public boolean validateKeyExistance(JsonObject jsonObject, String key) {
		try {
			if (jsonObject.has(key)) {
				System.out.println("Key Present in json");
				return true;
			} else {
				System.out.println("Key Not Present in json");
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	private JsonObject readJSON(String filePath) {
		JsonObject jsonObject;
		JsonParser parser = new JsonParser();
		try {
			String text = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
			jsonObject = new JsonParser().parse(text).getAsJsonObject();
			return jsonObject;
		} catch (IOException e) {
			System.out.println("File not found");
			return null;
		} catch (Exception e) {
			System.out.println("incorrect format");
			return null;
		}
	}

}
