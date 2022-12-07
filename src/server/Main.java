package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.*;

import org.json.simple.parser.ParseException;

import encryption.DeeSee_Chiffre;

/**
 * This Class is used to print the values of superheroes into the console in the format of
 * [name, firstName, lastName, birthday, superpowers[]]
 * External classes used are: "JsonProvider", "DeeSee_Chiffre"
 * @author Andrew Belmonte Sato
 *
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException { 
		start();
	}
		
	/**
	 * This method starts a program that offers command options to allow the user to select
	 * superheroes and encrypt their names. Use the console to input the commands.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void start() throws FileNotFoundException, IOException, ParseException {
		
		//call JsonProvider to convert the file to interactable java code
		JsonProvider[] jpa = JsonProvider.convertJSON("superheroes.json");
		
		//call the encryption class
		DeeSee_Chiffre e = new DeeSee_Chiffre();
		
		//show command options
		System.out.println("Please enter a command:");
		System.out.println("0. \"get all\" to get all superheroes");		
		System.out.println("1. \"encrypt [key]\" to encrypt all superheroes with a given key");
		System.out.println("2. \"get [hero name e.g. superman]\" to retrieve the values of a single superhereo");
		System.out.println("3. \"get [superpower]\" to retrieve all superheroes with the given superpower");
		System.out.println("4. \"encrypt [key] [superpower]\" to retrieve superheroes that"
				+ "match given the given superpower with encrypted identities");
		
		//call Scanner class for command inputs
		do {
			Scanner scanner = new Scanner(System.in);		
			String command = scanner.nextLine();
			System.out.println(command);
			
			if(command.contentEquals("get all")) {
				for(JsonProvider temp : jpa) {
					System.out.println(temp.name + " " + temp.firstName + " " + temp.lastName + " " 
							+ " " + temp.birthday + " " + Arrays.toString(temp.superpowers));
				}
			}else if(command.contains("encrypt")) {
				String substring = command.substring(8).stripLeading();
				//if a whitespace follows then there must be another string
				if(substring.contains(" ")) {
					String[] key_superpower = substring.split(" ");
					int key = Integer.parseInt(key_superpower[0]);
					for(JsonProvider temp : jpa) {
						if(Arrays.toString(temp.superpowers).contains(key_superpower[1])) {
							System.out.println(temp.name + " " + e.encrypt(temp.firstName, key) + " " + e.encrypt(temp.lastName, key) + " " 
									+ " " + temp.birthday + " " + Arrays.toString(temp.superpowers));
						}
					}
				}else{
					//get key value and parse it to an integer
					int key = Integer.parseInt(substring);
					
					for(JsonProvider temp : jpa) {
						System.out.println(temp.name + " " + e.encrypt(temp.firstName, key) + " " + e.encrypt(temp.lastName, key) + " " 
								+ " " + temp.birthday + " " + Arrays.toString(temp.superpowers));
					}
				}
			}else if(command.contains("get")) {
				String substring = command.substring(4);
				for(JsonProvider temp : jpa) {
					//if the command contains a superheroes' name then print that hero
					if(substring.contains(temp.name)) {
						System.out.println(temp.name + " " + temp.firstName + " " + temp.lastName + " " 
								+ " " + temp.birthday + " " + Arrays.toString(temp.superpowers));
					}else if(Arrays.toString(temp.superpowers).contains(substring)) {
						System.out.println(temp.name + " " + temp.firstName + " " + temp.lastName + " " 
								+ " " + temp.birthday + " " + Arrays.toString(temp.superpowers));	
					}
					
				}
			}else {
				System.out.println("Not a valid command!");
			}
		}while(true);
	}
}
	



