package com.pascoal.transformer;

import java.util.Scanner;

public class PigLatinTransformer {

	public String toPigLatin(String inString) {
		String word;
		String latin = "";
		StringBuilder latinPhrase = new StringBuilder();
		char first;
		boolean cap = false;
		Scanner pig = new Scanner(inString);

		// loop through all the words in the line
		while (pig.hasNext()) // is there another word?
		{
			word = pig.next();
			first = word.charAt(0);
			if ('A' <= first && first <= 'Z') // first is capital letter
			{
				first = Character.toLowerCase(first);
				cap = true;
			} else
				cap = false;

			// test if first letter is a vowel
			if (first == 'a' || first == 'e' || first == 'i' || first == 'o'
					|| first == 'u')
				latin = word + "hay";
			else // not a vowel
			{
				if (cap) {
					latin = "" + Character.toUpperCase(word.charAt(1));
					latin = latin + word.substring(2) + first + "ay";
				} else
					latin = word.substring(1) + first + "ay";
			}
			latinPhrase.append(latin + " ");

		}
		pig.close();
		return latinPhrase.toString();
	}
}
