public class Polynomial {

	public double [] coefficients;
	
	public Polynomial(){
		this.coefficients = new double [1];
		this.coefficients[0] = 0;
	}
	public Polynomial(double [] coefficients){
		int len = coefficients.length;
		this.coefficients = new double[len];
		for (int i = 0; i < len; i++){
			this.coefficients[i] = coefficients[i];
		}
	}
	public Polynomial add(Polynomial poly){
		int len = this.coefficients.length;
		int len2 = poly.coefficients.length;
		Polynomial new_poly = new Polynomial();
		if (len == 0 && len2 == 0) return new_poly;
		double [] results = new double [Math.max(len,len2)];
		for (int i = 0; i < Math.min(len, len2); i++){
			results[i] = this.coefficients[i] + poly.coefficients[i];
		}
		if (len > len2){
			for (int j = Math.min(len, len2); j < len; j++){
				results[j] = this.coefficients[j];
			}
			return new Polynomial(results);
		}
		for (int j = Math.min(len, len2); j < len2; j++){
			results[j] = poly.coefficients[j];
		}
		return new Polynomial(results);

	}
	public double evaluate(double num){
		double result = 0;
		for (int k = 0; k < coefficients.length; k++){
			if (k == 0){
				result = result + coefficients[k];
			}
			else{
				result = result + (coefficients[k] * (Math.pow(num, k)));
			}
			 
		}
		return result;
	}
	public boolean hasRoot(double num){
		double result = evaluate(num);
		if (result == 0) return true;
		return false;
	}

}