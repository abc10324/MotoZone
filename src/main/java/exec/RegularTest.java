package exec;

public class RegularTest {
	
	
	public static void main(String[] args) {
//		// ID Test
//		String str = "Yixuan888";
//		boolean flag = str.matches("^[a-zA-Z][a-zA-Z0-9]{7,14}$");
		
//		// Password Test
//		String str = "aa11W111";
//		boolean flag = str.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z\\d]{7,14}$");
		
//		// Ch Name Test
//		String str = "施懿萱";
//		boolean flag = str.matches("^([\\u4E00-\\u9FA5]{2,7})$");
		
		// En Name Test
		String str = "YIXUANSHI";
		boolean flag = str.matches("^[A-Z]+\\s[A-Z]+\\s{0,1}[A-Z]+{3,15}$");
		
//		// Email Test
//		String str = "abc10324@hotmail.com";
//		boolean flag = str.matches("^[a-zA-Z]{1}[\\w-]+@[a-z0-9]+\\.[a-z]+$");
		
		System.out.println(str);
		System.out.println(flag);
	}
}
