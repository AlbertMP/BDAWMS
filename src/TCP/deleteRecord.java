package TCP;

import java.util.Scanner;

public class deleteRecord {
	public String combine() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入要删除的记录编号：");
		String delete_id = sc.next();
		String sql = "DELETE FROM access WHERE access_id = '"+delete_id+"';";
		return sql;
	}
}
