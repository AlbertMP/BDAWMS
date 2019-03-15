package TCP;

import java.util.Scanner;

public class editOne {
	public String combine() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入修改前的姓名：");
		String edit_before_name = sc.next();
		System.out.println("请输入修改后的姓名：");
		String edit_after_name = sc.next();
		String sql = "UPDATE access SET access_name = '"+edit_after_name+"' WHERE access_name = '"+edit_before_name+"';";
		return sql;
	}
}
