package util;

public class EditDistance {
	private int insertCost = 1;
	private int deleteCost = 1;
	private int substituteCost = 2;

	public EditDistance() {

	}

	public EditDistance(int insertCost, int deleteCost, int substituteCost) {
		this.insertCost = insertCost;
		this.deleteCost = deleteCost;
		this.substituteCost = substituteCost;
	}

	protected byte toLowerCase(byte a) {
		if (a >= 'A' && a <= 'Z')
			a += 'a' - 'A';
		return a;
	}

	protected boolean isSame(byte a, byte b) {
		return a == b;
	}

	private int minOfThree(int x, int y, int z) {
		return ((x < y) ? ((x < z) ? x : z) : ((y < z) ? y : z));
	}

	public int editDistance(String aStr, String bStr) // compare target &&
								// src
	{
		byte[] b = bStr.getBytes();
		byte[] a = aStr.getBytes();
		int m = b.length + 1; // length of dest + 1 for i in 0,1,2...n
		int n = a.length + 1; // length of source + 1 for j in 0,1,2...m
		int[][] matrix = new int[m][n];// distance matrix
		for (int i = 0; i < m; i++) {
			for (int jt = 1; jt < n; jt++)
				matrix[i][jt] = 0;
			matrix[i][0] = i;
		}
		for (int i = 0; i < n; i++)
			matrix[0][i] = i;
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				if (isSame(b[i - 1], a[j - 1])) {
					matrix[i][j] = matrix[i - 1][j - 1];
				} else {
					matrix[i][j] = this
							.minOfThree(matrix[i][j - 1]
									+ insertCost, // insert
											// cost
									matrix[i - 1][j]
											+ deleteCost, // delete
													// cost
									matrix[i - 1][j - 1]
											+ substituteCost); // substitute
														// cost
				}
			}
		}
		return matrix[m - 1][n - 1];
	}

	private int minOfTwo(int a, int b) {
		return a < b ? a : b;
	}

	private String shortString(String s) {
		return s.replaceAll(String.valueOf((char) 160), "")
				.replaceAll("[\\pP‘’“”\\s]", "").toLowerCase();
	}

	public double editDistanceScore(String aStr, String bStr) {
		aStr = shortString(aStr);
		bStr = shortString(bStr);
		double len = minOfTwo(aStr.length(), bStr.length());
		if (len == 0)
			return -1;
		// System.out.print("[>"+aStr+"<vs.>"+bStr+"<]");
		return editDistance(aStr, bStr) / len;
	}

	// public static void main(String [] args){
	// String a =
	// "John Leslie King, Centralized versus decentralized computing: organizational considerations and management options, ACM Computing Surveys (CSUR), v.15 n.4, p.319-349, December 1983";
	// String b =
	// "Centralized versus decentralized computing: organizational considerations and management options";
	// EditDistance ed = new EditDistance(){
	// protected boolean isSame(byte a , byte b){
	// return toLowerCase(a) == toLowerCase(b);
	// }
	// };
	// System.out.println("ed : " + ed.editDistance(a, b));
	// System.out.println("edscore : " + ed.editDistanceScore(a, b));
	// }

}
