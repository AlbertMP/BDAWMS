package TCP;

import java.util.Scanner;

public class insertOne {

	public String combine() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入编号：");
		String access_id = sc.next();
		System.out.println("请输入时间：");
		String access_date = sc.next();
		System.out.println("请输入操作：");
		String access_class = sc.next();
		System.out.println("请输入数额：");
		String access_num = sc.next();
		System.out.println("请输入经办人：");
		String access_operator = sc.next();
		System.out.println("请输入姓名：");
		String access_name = sc.next();
		String sql = "INSERT INTO access VALUES('" + access_id + "','" + access_date + "','" + access_class + "','"
				+ access_num + "','" + access_operator + "','" + access_name + "');";
		return sql;
	}
}
