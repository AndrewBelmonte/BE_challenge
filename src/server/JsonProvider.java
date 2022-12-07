package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


/**
 * The JsonProvider class is used to convert a json file into
 * java objects that can be worked with. This opens up the possiblity
 * to display, manipulate and change data of a json file. 
 * @author Andrew Belmonte Sato
 *
 */

public class JsonProvider {
	
	/*
	 * This is used to store json file values
	 */
	String fileName;
	String name;
	String firstName;
	String lastName;
	String birthday;
	String[] superpowers;
	
	
	/*
	 * This constructor is used to save the values of a json file object into 
	 * a JsonProvider object
	 */
    JsonProvider(String name, String firstName, String lastName, 
    		String birthday, String[] superpowers)
    {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.superpowers = superpowers;
    }
    /*
     * Default constructor
     */
    JsonProvider(){
    	
    }

	
	/**
	 * This method converts a json file containing superhero objects into a java object array of
	 * the class JsonProvider which stores the needed values. Each object within the array 
	 * is assigned to a hero.
	 * <p>
	 * Example: A json file with five superhero objects will be transformed in a JsonProvider 
	 * array of length five. 
	 * @param fileName The filename of a json file with the format {name, identity{firstName,LastName},
	 * birthday,superpowers[]}
	 * @return An object array containing all superheroes and their corresponding values 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static JsonProvider[] convertJSON(String fileName) throws FileNotFoundException, IOException, ParseException {
		
		//parse the external json file into this program
		JSONParser jsonP = new JSONParser();
		JSONArray heroes= (JSONArray) jsonP.parse(new FileReader(fileName));

		//create JsonProvider object array to store each individual heroe
		JsonProvider[] jp = new JsonProvider[heroes.size()];
		
		//make a JSONObject array that is equal to the amount of heroes in the json file
		JSONObject[] JSONObjectArray = new JSONObject[heroes.size()];
		
		//since the json file array only has 5 objects lets do it like this
		for(int i = 0; i<JSONObjectArray.length; i++) {
			
			JSONObject ob = (JSONObject)heroes.get(i);
			
			//get the name of the object and store it 
			String name =  (String) ob.get("name");
			
			//get the identity 		
			JSONObject identityObject = (JSONObject) ob.get("identity");
	        String firstName = (String) identityObject.get("firstName");
	        String lastName = (String) identityObject.get("lastName");
	        
	        //get the birthday
	        String birthday = (String)ob.get("birthday");
	        
			//get all superpowers
			JSONArray superpowersAr = (JSONArray) ob.get("superpowers");
			String[] superpowers = new String[superpowersAr.size()];
			if(!(superpowersAr.isEmpty())){
				//String[] superpowersArray = new String[superpowersAr.size()];
				for (int z = 0; z < superpowersAr.size(); z++) {
		            superpowers[z] = (String) superpowersAr.get(z);
		        }
			}
			
			//store all the json objects into an object array
			jp[i] = new JsonProvider(name, firstName, lastName, birthday, superpowers);
			
			//print the values of all heroes if needed
			/*
			System.out.println(jp[i].name + " " +jp[i].firstName + " " + jp[i].lastName + " " + jp[i].birthday);
			for (String s : superpowers) {
				System.out.println(s);
			}
			*/
		}
		//remove unwanted superheroes
		jp = removeUnwantedHeroes(jp);
		//remove null pointers that occur after removal
		jp = removeNullObjects(jp);
		
		//return the result
		return jp;
	}
	
	
	/**
	 * This method removes all object elements of a JsonProvider array that do not contain
	 * the superpowers "strength", "speed", "flight", "invulnerability" or "healing" 
	 * @param jp a JsonProvider array containing all superheroes regardless of superpowers
	 * @return The input array but only with the superhereos that have the superpowers 
	 * strength, speed, flight, invulnerability and healing 
	 */
	public static JsonProvider[] removeUnwantedHeroes(JsonProvider[] jp) {
		JsonProvider[] newJp = new JsonProvider[jp.length];
		for(int i = 0; i<jp.length; i++) {
			if(Arrays.toString(jp[i].superpowers).contains("strength") 
					|| Arrays.toString(jp[i].superpowers).contains("speed")
					|| Arrays.toString(jp[i].superpowers).contains("flight")
					|| Arrays.toString(jp[i].superpowers).contains("invulnerability")
					|| Arrays.toString(jp[i].superpowers).contains("healing")) {					
					newJp[i] = jp[i];
				}
			}
		return newJp;
	}
	
	/**
	 * This method removes empty JsonProvider objects from an array.
	 * @param jp a JsonProvider array that holds "null" objects 
	 * @return The input array without "null" objects
	 */
	public static JsonProvider[] removeNullObjects(JsonProvider[] jp) {
		//remove null
		List<JsonProvider> heroes = new ArrayList<>(Arrays.asList(jp));
		heroes.removeIf(Objects::isNull);
		
		//save arraylist into jsonprovider array and return it
		JsonProvider[] temp = new JsonProvider[heroes.size()];
		for(int i = 0; i<heroes.size(); i++) {
			temp[i] = heroes.get(i);
		}
		return temp;	
	}
		
}