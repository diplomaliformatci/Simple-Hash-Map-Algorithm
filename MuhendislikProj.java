package MuhendislikProj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MuhendislikProj {
	public static void main(String[] args) {
		String[] HashTable = new String[200]; 
		String[] wordlist;
		MuhendislikProj functionSelector = new MuhendislikProj();
		Scanner reader = new Scanner(System.in);
		String word;
		boolean controller;
		try {
			File file = new File("/Users/cankincal/Desktop/words.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				
				HashTable = functionSelector.InsertIntoHash(HashTable , line);
				//System.out.println(line);
				stringBuffer.append(line);
				stringBuffer.append("\n");
			}
			fileReader.close();
			System.out.println("Contents of file:");
			//System.out.println(stringBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String tempWord : HashTable) {
			System.out.println(tempWord);
		}
		
		
		while (true) {
			word = reader.nextLine();
			controller = functionSelector.searchForWord(HashTable , word);
			if (controller) {
				break;
			} else {
				wordlist = functionSelector.wordList(word);
				for (String words : wordlist) {
					System.out.println(words +  " Icin arama yapiliyor");
					functionSelector.searchForWord(HashTable, words);
				}
				
			}
			
			
			
		}
		
		
		
		
		
		
	}
	public String[] wordList(String word) {
		String[] wordList = new String[word.length() / 2   + word.length() - 1 ];
		char[] wordToCharachter = word.toCharArray();
		char tmp;
		String substring;
		int counter = 0;
		for (int i = 0 ; i < word.length() - 1 ; i+=2) {
			tmp = wordToCharachter[i];
			wordToCharachter[i] = wordToCharachter[i + 1];
			wordToCharachter[i+1] = tmp;
			wordList[counter] = new String(wordToCharachter);
			wordToCharachter = word.toCharArray();
			counter++;
		}
		
		wordToCharachter = word.toCharArray();
		
		for (int i = 0 ; i < word.length() - 1 ; i++) {
			substring = word.substring(i, word.length());
			wordList[counter] = substring;
			counter++;
		}
		return wordList;
	}
	
	public  int getAsciValue(String word) {
		int asciValue = 0;
		int multiplier = word.length();
		for (int i = 0 ; i < word.length() - 1  ; i++) {
				asciValue += multiplier * (int) word.charAt(i);
				multiplier = multiplier - 1;
		}
		return asciValue;
	}
	
	public  String[] InsertIntoHash(String[] HashTable ,String word) {
		int asciValue = getAsciValue(word);
		int indexValue = asciValue % 200;
		if (HashTable[indexValue] == null) {
			HashTable[indexValue] = word;
			return HashTable;
		}
		
		indexValue = quadraticInsertion(HashTable , asciValue);
		
		HashTable[indexValue] = word;
		return HashTable;
		
		
	}
	
	
	public int quadraticInsertion(String[] HashTable , int asciValue) {
		int indexValue = 0;
		for (int i = 0 ; ; i++) {
			asciValue = asciValue + i * i ;
			indexValue = asciValue % 200;
			if (HashTable[indexValue] == null) {
				return indexValue;
			}
		}
	}
	
	
	
	public boolean searchForWord(String[] HashTable , String word) {
		int asciValueRHS = getAsciValue(word);
		int indexValue = asciValueRHS % 200;
		
		if (HashTable[indexValue] == null) {
			return false;
		}
		
		int asciValueLHS = getAsciValue(HashTable[indexValue]);
		if (asciValueRHS == asciValueLHS) {
			System.out.println("Aradiginiz Kelime " + word + "IndexDegeri " + indexValue + "Bulunmustur");
			return true;
		} else {
			for (int i = 0 ; i < 200 ; i ++) {
				indexValue = (asciValueRHS + i * i)  % 200;
				if (HashTable[indexValue] == null) {
					return false;
				}
				asciValueLHS = getAsciValue(HashTable[indexValue]);
				if (asciValueRHS == asciValueLHS) {
					System.out.println("Aradiginiz Kelime" + word + "IndexDegeri" + indexValue + "Bulunmustur");
					return true;
				}
			}
		}
		
		return false;
		
		
		
	}
}
