import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {

	public double [] coefficients;
	public int [] exponents;
	
	public Polynomial(){
		this.coefficients = new double [1];
		this.exponents = new int [1];
		this.coefficients[0] = 0;
		this.exponents[0] = 0;
	}
	public Polynomial(double [] coefficients, int [] exponents){
		int len = coefficients.length;
		int len2 = exponents.length;
		this.coefficients = new double[len];
		this.exponents = new int[len2];
		for (int i = 0; i < len; i++){
			this.coefficients[i] = coefficients[i];
		}
		for (int j = 0; j < len2; j++){
			this.exponents[j] = exponents[j];
		}

	}

	public Polynomial(File input) throws IOException{
		BufferedReader inp = new BufferedReader(new FileReader(input));
		String line = inp.readLine();
		inp.close();
		String [] terms = line.split("(?=[+-])");
		this.coefficients = new double[terms.length];
		this.exponents = new int[terms.length];
		for (int i = 0; i < terms.length; i++){
			String [] ind = terms[i].split("x");
			if (terms[i].contains("x") == false){
				this.coefficients[i] = Double.parseDouble(ind[0]);
				this.exponents[i] = 0;
			}
			else if (ind[0].equals("") || ind[0].equals("+")){
				this.coefficients[i] = 1.0;
				if (ind.length == 1){
					this.exponents[i] = 1;
				}
				else {this.exponents[i] = Integer.parseInt(ind[1]);}
			}
			else if (ind[0].equals("-")){
				this.coefficients[i] = -1.0;
				if (ind.length == 1){
					this.exponents[i] = 1;
				}
				else {this.exponents[i] = Integer.parseInt(ind[1]);}
			}
			else{
				this.coefficients[i] = Double.parseDouble(ind[0]);
				if (ind.length == 1){
					this.exponents[i] = 1;
				}
				else{
					this.exponents[i] = Integer.parseInt(ind[1]);
				}
			}	
			
		}
	}
	
	public Polynomial add(Polynomial poly){
		
		int len1 = this.coefficients.length;
		int len2 = poly.coefficients.length;
		//worst case is when none of the coefficients can be added because of different exponents, so need length of new array to be the sum of the length of both
		double [] new_coef =  new double[len1+len2];
		int [] new_exp = new int[len1+len2];
		int k = 0;
		//this gets adds everything from the first array in
		for (int i = 0; i < len1; i++){
			boolean added = false;
			for (int j = 0; j < len2; j++){
				if (this.exponents[i] == poly.exponents[j]){
					new_coef[k] = this.coefficients[i] + poly.coefficients[j];
					new_exp[k] = this.exponents[i];	
					added = true;
					
				}
			}
			if (added == false){
				new_coef[k] = this.coefficients[i];
				new_exp[k] = this.exponents[i];
			}
			k++;
		}
		//now we need to add everything left over from the second array
		for (int j = 0; j < len2; j++){
			boolean exists = false;
			for (int z  = 0; z < k; z++){
				if (poly.exponents[j] == new_exp[z]){
					exists = true;
				}
			}
			if (exists == false){
				new_coef[k] = poly.coefficients[j];
				new_exp[k] = poly.exponents[j];
				k++;
			}

		}
		//get rid of 0 terms if present
		int new_length = 0;
		for (int i = 0; i < k; i++){
			if (new_coef[i] != 0){
				new_length++;
			}
		}
		double[] final_coef = new double[new_length];
    		int[] final_exp = new int[new_length];
		int current = 0;
    		for (int i = 0; i < k; i++) {
			if (new_coef[i] != 0){
        			final_coef[current] = new_coef[i];
        			final_exp[current] = new_exp[i];
				current++;
			}
    		}
		
		return new Polynomial(final_coef, final_exp);
		

	}
	public double evaluate(double num){
		double result = 0;
		for (int k = 0; k < coefficients.length; k++){
			if (exponents[k] == 0){
				result = result + coefficients[k];
			}
			else{
				result = result + (coefficients[k] * (Math.pow(num, exponents[k])));
			}
			 
		}
		return result;
	}
	public boolean hasRoot(double num){
		double result = evaluate(num);
		if (result == 0) return true;
		return false;
	}

	public Polynomial multiply(Polynomial poly){
	
		int len1 = this.coefficients.length;
		int len2 = poly.coefficients.length;
		double [] new_coeff = new double[len1*len2];
		int [] new_exp = new int[len1*len2];
		int k = 0;
		for (int i = 0; i < len1; i++){
			for (int j = 0; j < len2; j++){
				//check if that particular exponent already exists
				boolean exists = false;
				for (int z = 0; z < k; z++){
					if((poly.exponents[j]+this.exponents[i]) == new_exp[z]){
						new_coeff[z] = new_coeff[z] + this.coefficients[i] * poly.coefficients[j];
						exists = true;
					}
				} 
				
				if (exists == false){
					new_coeff[k] = this.coefficients[i] * poly.coefficients[j];
					new_exp[k] = this.exponents[i] + poly.exponents[j];
					k++;
				}
				
			}
		}
		//get rid of zero coefficients
		int non_zero_length = 0;
		for (int i = 0; i < k; i++){
			if (new_coeff[i] !=0){
				non_zero_length++;
			}
		}
		double [] final_coeff = new double[non_zero_length];
		int [] final_exp = new int[non_zero_length];
		int current = 0;
    		for (int i = 0; i < k; i++) {
			if (new_coeff[i] != 0){
        			final_coeff[current] = new_coeff[i];
        			final_exp[current] = new_exp[i];
				current++;
			}
    		}

		return new Polynomial(final_coeff, final_exp);

	}

	public void saveToFile(String file_path) throws IOException {
		FileWriter output = new FileWriter(file_path, true);
		for (int i = 0; i < this.coefficients.length; i++){
			if (i> 0 && this.coefficients[i] >= 0){
				output.write("+");
			}
			output.write(Double.toString(this.coefficients[i]));
			if (this.exponents[i] != 0){
				output.write("x");
				if (this.exponents[i]!=1){
				output.write(Integer.toString(this.exponents[i]));
				}
			}
		}
		
		output.close();

	}

}