package TCP;

import java.util.Scanner;

public class searchOne {
	public String combine() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入要查询的人名：");
		String search_name = sc.next();
		String sql = "SELECT * FROM access WHERE access_name = '"+search_name+"';";
		return sql;
	}
}
