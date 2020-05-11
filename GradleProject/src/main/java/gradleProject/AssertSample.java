package gradleProject;

import lombok.val;

public class AssertSample {

	public static void main(String[] args) {
		val price = Integer.parseInt(args[0]);
		val num = Integer.parseInt(args[1]);

		if(price > 0 || num > 0) {
			int sum = sum(price,num);
			System.out.printf("%d + %d = %d%n", price, num, sum);
		} else {
			System.out.println("価格と数量には正の値を指定してください");
		}
	}

	private static int sum(int price, int num) {
		assert price > 0 : "price :" + price;
		assert num > 0 : "num :" + num;

		return price * num;
	}
}
