import java.io.File;
import java.io.IOException;
import java.util.Arrays;
public class Driver {
	public static void main(String [] args) throws IOException {
		Polynomial p = new Polynomial();
		System.out.println(Arrays.toString(p.coefficients));
		System.out.println(Arrays.toString(p.exponents));
		double [] coefs = new double[]{3,2};
		int [] exps = new int[]{0,1};
		Polynomial p2 = new Polynomial(coefs, exps);
		System.out.println(Arrays.toString(p2.coefficients));
		System.out.println(Arrays.toString(p2.exponents));
		File f = new File("C:\\Users\\suraj\\OneDrive\\Desktop\\TBH\\b07_test.txt");
		Polynomial p3 = new Polynomial(f);
		System.out.println(Arrays.toString(p3.coefficients));
		System.out.println(Arrays.toString(p3.exponents));
		Polynomial p4 = p3.add(p2);
		System.out.println(Arrays.toString(p4.coefficients));
		System.out.println(Arrays.toString(p4.exponents));
		double x = p2.evaluate(0);
		System.out.println(x);
		boolean y = p2.hasRoot(0);
		boolean t = p2.hasRoot(-1);
		System.out.println(y);
		System.out.println(t);
		Polynomial p5 = p2.multiply(p3);
		System.out.println(Arrays.toString(p5.coefficients));
		System.out.println(Arrays.toString(p5.exponents));
		p5.saveToFile("C:\\Users\\suraj\\OneDrive\\Desktop\\TBH\\b07_test.txt");
	}
}
