package mastermind.util;

/**
 * Utility class for the Mastermind game
 * 
 * @author Marcelo
 *
 */
public class GameHelper {

	/**
	 * Generates a new secret for the session game using the quantity
	 * of colors positions. The range of characters envolved is its double. 
	 * 
	 * @param size number of colors or positions
	 * @return new secret
	 */
	public static String generateSecret(int size){
		StringBuilder ret = new StringBuilder("");
		char let;
		for (int i=0;i<size;i++) {
			let = randChar(size*2);
			while (ret.toString().indexOf(let)>=0){
				let = randChar(size*2);
			}
			ret.append(let);
		}
		return ret.toString();
	}
	
	/**
	 * Randomize one character in range
	 * @param range quantity of characters to consider
	 * @return one character in range
	 */
	private static char randChar(int range){
		return (char)(Math.random()*range+65);
	}
}
