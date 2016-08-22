/*
 * Programmer: Ahmed Aldhaheri
 * Course: 	   COSC314
 * Term:	   Summer 2016
 * Date:	   6/8/2016
 * Description: Program reads boolean matrices relations from 
 * 				file and checks if relations is reflexive, symmetric
 * 				,anti-symmetric, transitive, and if relation is 
 * 				equivalence relation(reflexive, symmetric, transitive)
 */

import java.util.*;
import java.io.*;

public class RelationsDemo {
	
	private static int columns = 0;
	private static int rows = 0;
	
	public static void main(String[] args) {
		
		//decalre objects and variables
		Scanner inFile = null;
		File inFileName = new File("src\\Relations.txt");
		String relation = "", str = "", name = "";
		int i = 0;
		int[][] booleanMatrix = null;
		boolean isReflex, isSymmetric, isAntiSymmetric, isTransitive;
		
		
		//assign file stream to scanner 
		try{
			inFile = new Scanner(inFileName);
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		//read from file 
		while(inFile.hasNextLine()){
			//check if data is integer type or string
			if(inFile.hasNextInt()){
				str += inFile.nextLine();
				str += "\n";
				i = 0;		//keeps track of empty lines on file
			}//end if statement
			else{
				relation = inFile.nextLine();
				if(!relation.isEmpty())
					name = relation;
			}
			
			if(relation.isEmpty()){
				i++;
				if(i == 1){
					display(str, name);
					booleanMatrix = convertStrToArray(str);
					isReflex = isReflexive(booleanMatrix);
					isSymmetric = isSymmetry(booleanMatrix);
					isAntiSymmetric = isAntiSymmetry(booleanMatrix);
					isTransitive = isTransitivity(booleanMatrix);
					
					/*check if relation is an equivalence relation
					 * In general to check if relation is an equivalence
					 * relation, relation has to be reflexive, symmetric,
					 * and antisymmetric
					 */
					if(isReflex && isSymmetric && isTransitive){
						//if true get equivalence classes
						System.out.println("is an equivalence relation with equivalence classes");
						equivalenceClasses(booleanMatrix);
					}
					else
						System.out.println("is not an equivalence relation\n\n");
					str = "";
				}
			}//end if statement
			
		}//end while loop
		inFile.close();
		
	}//end main method
	
	//this method display relation number and its boolean matrix
	public static void display(String matrix, String name){
		System.out.println("\n" + name + " with matrix");
		System.out.println(matrix);
	}//end display method
	
	//this method converts string matrix to array of integers
	public static int[][] convertStrToArray(String matrix){
		
		String[] row = matrix.split("\n");	//get number of rows on matrix
		rows = row.length;
		String[][] tempArray2 = new String [row.length][];
		int i = 0;
		for(String str: row){
			tempArray2[i++] = str.split(" ");
			columns = str.split(" ").length;	//get number of columns on matrix
		}
		
		//int array matrix will be saved in
		int [][] booleanMatrix = new int [rows][columns];
		for(int j = 0; j < row.length; j++){
			for(int z = 0; z < columns; z++)
				booleanMatrix[j][z] = Integer.valueOf(tempArray2[j][z]);
		}
		
		return booleanMatrix;
	}//end convertStrToArray method
	
	/*method to check if relation is reflexive we need to 
	 * make sure that the main diagonal contains 
	 * all ones.
	 */
	public static boolean isReflexive(int[][] matrix){
		boolean isReflex = false;
		
		//check if boolean matrix is reflexive
		outerloop:
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				//check if index the same
				if(i == j){
					//check main diagonal if all ones its reflexive
					if(matrix[i][j] == 1)
						isReflex = true;
					else{
						isReflex = false;
						break outerloop;
					}
				}//end if statement
			}//end inner loop
		}//end outer loop
			
		if(isReflex)
			System.out.println("is reflexive");
		else
			System.out.println("is not refelexive");
		return isReflex;
	}
	
	/*method to check if relation is symmetric we need 
	 * the transpose matrix of the relation
	 */
	public static boolean isSymmetry(int[][] matrix){
		boolean isSymmetric = false;
		
		//check if boolean matrix relation is symmetric
		int[][] transpose = new int[rows][columns];
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				transpose[j][i] = matrix[i][j];
		
		//check if transpose equals original relation
		outerloop:
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				if(transpose[i][j] == matrix[i][j])
					isSymmetric = true;
				else{
					isSymmetric = false;
					break outerloop;
				}
			}//end inner loop
		}//end outer loop
		
		if(isSymmetric)
			System.out.println("is symmetric,");
		else
			System.out.println("is not symmetric,");
		
		return isSymmetric;
	}//end isSymmetry method
	
	/*method to check if relation is antisymmetric
	 * we need to check for every pairs
	 * (a,b)(b,a) then a = b.
	 */
	public static boolean isAntiSymmetry(int[][] matrix){
		boolean isAntiSymmetric = true;
	
		//check if boolean matrix relation is antisymmetric
		outerloop:
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				if(matrix[i][j] == 1){
					//make sure a != b
					if(i != j){
						isAntiSymmetric = isAntiSymmetryHelper(matrix, i, j);
						if(!isAntiSymmetric)
							break outerloop;
					}
				}//end outer if statement
			}//end inner loop
		}//end outer loop
		
		if(isAntiSymmetric)
			System.out.println("is antisymmetric,");
		else
			System.out.println("is not antisymmetric,");
		
		return isAntiSymmetric;
	}
	
	/*this is a helper method for isAntiSymmetry method.
	 * Takes (a,b) position where a != b and checks (b,a) position
	 * if both positions equal to 1 then relation is not antisymmetric
	 */
	public static boolean isAntiSymmetryHelper(int[][] matrix,int a,int b){
		boolean isNotEqual = false;
		
		//check position (b,a)
		if(matrix[b][a] == 1)
			isNotEqual = false;
		else
			isNotEqual = true;
		
		return isNotEqual;
	}//end isAntiSymmetryHelper method 
	
	/*
	 * this method is to find if relation R is transitive or not.
	 *In general to check for transitivity you would multiply the 
	 *boolean matrix by itself n times(n^n). Once we multiply matrix, 
	 *we add Mr + Mr^2 + Mr^3 .........+ Mr^n. When we have result we
	 *compare the result with the original matrix and if result
	 *is less than or equal to original matrix than relation is transitive
	 *and not transitive otherwise. 
	 */
	public static boolean isTransitivity(int[][] matrix){
		boolean isTransitive = true;
		
		//matrix we want to store result 
		int[][] rResult = new int[rows][columns];
		//temporary matrix to store result of every element multiplication
		int[][]tempMatrix = new int[rows][columns];
		//matrix to multiply by original matrix
		int[][] multiplierMatrix = matrix;
		//counter to make sure matrix multiplied n times
		int counter = 1;		
		
		//perform multiplication 
		while(counter <= rows){
			
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < columns; j++){
					for(int k = 0; k < columns; k++){
						
						tempMatrix[i][j] += multiplierMatrix[i][k] * matrix[k][j];
						
					}//end third inner for loop
					
					if(tempMatrix[i][j] >= 1){
						rResult[i][j] = 1;
					}
					else
						rResult[i][j] = 0;
					
				}//end inner second for loop
				
				//add Mr^n + Mr^n+1
				for(int m = 0; m < rows; m++){
					for(int n = 0; n < columns; n++){
						if((rResult[m][n] + multiplierMatrix[m][n]) >= 1)
							rResult[m][n] = 1;
						else
							rResult[m][n] = 0;	
					}
				}//end for loop
				
			}//end outer for loop
			
			//change multiplier matrix to current result
			multiplierMatrix = rResult;
			tempMatrix = new int[rows][columns];
			counter++;
		}//end while loop
		//System.out.println("transitive result: ");
		outerloop:
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++)
				if(rResult[i][j] > matrix[i][j]){
					isTransitive = false;
					break outerloop;
				}
			//System.out.println();
		}
		
		if(isTransitive)
			System.out.println("Relation is transitive");
		else{
			System.out.println("Relation is not transitive");
			System.out.println("The matrix of its transitive closure is: ");
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < columns; j++)
					System.out.print(rResult[i][j] + " ");
				System.out.println();
			}//end for loop		
		}//end else statement
		
		return isTransitive;
	}
	
	/*
	 * this method is to find equivalence classes if relation
	 * is an equivalence relation(reflexive, symmetric, and transitive).
	 * In general to find an equivalence class, iterate through matrix 
	 * and find equivalent rows. Once equivalent rows are found find position
	 * of all the ones in these rows and put index positions of all the ones in 
	 * one class. 
	 */
	public static void equivalenceClasses(int[][] matrix){
		boolean isEqual = false;
		//array list to store equal row numbers
		ArrayList<Integer> equalRows = new ArrayList<Integer>();
		//saves previous class in this arraylist
		ArrayList<Integer> previousClass = new ArrayList<Integer>();
		//counter to count how many elements in each equivalent class
		int counter = 0;
		//counter2 to avoid previousClass arraylist outofbound exception
		int counter2 = 0; 
		
		outerloop1:
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				for(int k = 0; k < rows; k++){
					//check which row j equals to row i
					if(matrix[i][k] == matrix[j][k]){
						isEqual = true;
					}
					else{
						isEqual = false;
						break;
					}
					
				}//end inner third loop
				
				//assign equal rows number to arraylist
				if(isEqual)
					equalRows.add(j);
				
			}//end inner for loop
			
			System.out.print("{");
			
			/*
			 * iterate through one of the rows that are equal and 
			 * get all the indexes of ones in that row
			*/
			
			outerloop:
			for(int n = 0; n < rows; n++){
				//check if counter2 is zero to assign size of first class to counter
				if(counter2 == 0)
					counter += equalRows.size();
				else{
					//check make sure current class does not equal previous class
					if(equalRows.get(0) == previousClass.get(0))
						break outerloop;
					/*if current class is different than previous class
					 * check n(loop control) to make sure not to add same 
					 * class twice
					 */
					else if(n < 1)
						counter += equalRows.size();
				}
				counter2++;
				
				if(n == equalRows.get(0)){
					for(int m = 0; m < columns; m++){
						//check for all ones in row
						if(matrix[n][m] == 1){
							System.out.print((m+1));
							if(m < equalRows.get(equalRows.size() - 1))
								System.out.print(",");
						}//end if statement
						
					}//end inner loop
					System.out.print("},");
					break outerloop;
				}//end if statement
			}//end for loop
			
			previousClass = equalRows;
			
			equalRows = new ArrayList<Integer>();
			if(counter == rows)
				break outerloop1;
		
		}//end outer for loop
	
	}//end equivalenceClasses method
	
}//end class


