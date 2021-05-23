package ui;

public class Main {
	
	
	public static void main(String[] args) {
		
		String pref = "../lab3_files/datasets/";
		String[] solArgs = new String[] {
				pref+"titanic_train_categorical.csv", 
				pref+"titanic_test_categorical.csv", 
				"2"};
		Solution.main(solArgs);
		
		
	}
	
}
