package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.sql.*;

class ServerThread extends Thread {
	private Socket sk;

	ServerThread(Socket sk) {
		this.sk = sk;
	}

	// 线程运行实体
	public void run() {

		// 连接数据库的五大参数
		String driverClass = "com.mysql.jdbc.Driver";// 加载数据库驱动
		String databaseName = "BMS";// 连接到哪一个数据库
		String serverIp = "127.0.0.1";// 服务器IP地址
		String username = "root";// 用户名
		String password = "314159";// 密码
		// 拼凑成一个完整的URL地址jdbcUrl
		String jdbcUrl = "jdbc:mysql://" + serverIp + ":3306/" + databaseName
				+ "?useUnicode=true&characterEncoding=utf-8&useSSL=false";

		BufferedReader in = null;
		PrintWriter out = null;
		try {
			InputStreamReader isr;
			isr = new InputStreamReader(sk.getInputStream());
			in = new BufferedReader(isr);
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())), true);
			while (true) {
				// 接收来自客户端的请求，根据不同的命令返回不同的信息
				String cmd = in.readLine();
				System.out.println(cmd);
				switch (cmd) {
				case "1":
//					System.out.println("插入信息。");
					out.println("命令："); // 服务器返回提示信息
					BufferedReader in1 = null;
					PrintWriter out1 = null;
					try {
						InputStreamReader isr1;
						isr1 = new InputStreamReader(sk.getInputStream());
						in1 = new BufferedReader(isr1);
						out1 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())), true);
						// 获取了插入命令
						// 例子：INSERT INTO access VALUES('6','2019-2-14','取款','362.09','李华','赵六');
						String insertInfo = in1.readLine();
						System.out.println(insertInfo);

						Connection conn = null;
						PreparedStatement preStmt = null;
						Statement stmt = null;
						try {
							// 1.注册数据库的驱动
							Class.forName(driverClass);
							// 2.通过Connection对象获取Statement对象
							conn = DriverManager.getConnection(jdbcUrl, username, password);
							// 3.创建statement类对象，用来执行SQL语句
							stmt = conn.createStatement();
							// 执行的SQL语句
							String sql = insertInfo;
							preStmt = conn.prepareStatement(sql);
							int i = stmt.executeUpdate(sql);// 执行，如果i>=1，成功，否则更新失败
							if (i >= 1) {
								out.println("插入成功！");
							} else {
								out.println("插入失败！");
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (preStmt != null) {
								try {
									preStmt.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								preStmt = null;
							}
							if (conn != null) {
								try {
									conn.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								conn = null;
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				case "2":
//					System.out.println("查询个人。");
//					out.println("姓名："); // 服务器返回提示信息
					BufferedReader in2 = null;
					PrintWriter out2 = null;
					try {
						InputStreamReader isr2;
						isr2 = new InputStreamReader(sk.getInputStream());
						in2 = new BufferedReader(isr2);
						out2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())), true);
						String searchSql = in2.readLine();
//						System.out.println(searchName);

						Connection conn = null;
						PreparedStatement stmt = null;
						ResultSet rs = null;
						try {
							Class.forName(driverClass);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							conn = DriverManager.getConnection(jdbcUrl, username, password);
							String sql = searchSql;
							stmt = conn.prepareStatement(sql);
							rs = stmt.executeQuery();
							out2.println("=============================================");
							out.println("个人存取款信息列表");
							out.println("编号  储户    时间          类别  存取数目  经办人");
							while (rs.next()) {
								int access_id = rs.getInt("access_id");
								String access_name = rs.getString("access_name");
								Date access_date = rs.getDate("access_date");
								String access_class = rs.getString("access_class");
								Float access_num = rs.getFloat("access_num");
								String access_operator = rs.getString("access_operator");
								out.println("" + access_id + "    " + access_name + "    " + access_date + "    "
										+ access_class + "  " + access_num + "   " + access_operator + "     ");
							}
							out2.println("=============================================");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							if (rs != null) {
								rs.close();
							}
							if (stmt != null) {
								stmt.close();
							}
							if (conn != null) {
								conn.close();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case "3":
//					System.out.println("查询总表。");
					BufferedReader in3 = null;
					PrintWriter out3 = null;
					try {
						InputStreamReader isr3;
						isr3 = new InputStreamReader(sk.getInputStream());
						out3 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())), true);

						Statement stmt = null;
						ResultSet rs = null;
						Connection conn = null;
						try {
							// 1.注册数据库的驱动
							Class.forName(driverClass);
							// 2.通过Connection对象获取Statement对象
							conn = DriverManager.getConnection(jdbcUrl, username, password);

							stmt = conn.createStatement();
							String sql = "select * from access";
							rs = stmt.executeQuery(sql);
							out3.println("=============================================");
							out3.println("总表：");
							out3.println("编号  储户    时间          类别  存取数目  经办人");
							while (rs.next()) {
								int access_id = rs.getInt("access_id");
								String access_name = rs.getString("access_name");
								Date access_date = rs.getDate("access_date");
								String access_class = rs.getString("access_class");
								Float access_num = rs.getFloat("access_num");
								String access_operator = rs.getString("access_operator");
								out3.println("" + access_id + "    " + access_name + "    " + access_date + "    "
										+ access_class + "  " + access_num + "   " + access_operator + "     ");
							}
							out3.println("=============================================");
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (rs != null) {
								try {
									rs.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								rs = null;
							}
							if (stmt != null) {
								try {
									stmt.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								stmt = null;
							}
							if (conn != null) {
								try {
									conn.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								conn = null;
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case "4":
//					System.out.println("修改数据。");
//					out.println("命令："); // 服务器返回提示信息
					BufferedReader in4 = null;
					PrintWriter out4 = null;
					try {
						InputStreamReader isr4;
						isr4 = new InputStreamReader(sk.getInputStream());
						in4 = new BufferedReader(isr4);
						out4 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())), true);
						// 获取命令
						// 例子：
						String updatetInfo = in4.readLine();
//						System.out.println(updatetInfo);

						Connection conn = null;
						PreparedStatement preStmt = null;
						Statement stmt = null;
						try {
							// 1.注册数据库的驱动
							Class.forName(driverClass);
							// 2.通过Connection对象获取Statement对象
							conn = DriverManager.getConnection(jdbcUrl, username, password);
							// 3.创建statement类对象，用来执行SQL语句
							stmt = conn.createStatement();
							// 执行的SQL语句
							String sql = updatetInfo;
							preStmt = conn.prepareStatement(sql);
							int i = stmt.executeUpdate(sql);// 执行，如果i>=1，成功，否则更新失败
							if (i >= 1) {
								out.println("修改成功！");
							} else {
								out.println("修改失败！");
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (preStmt != null) {
								try {
									preStmt.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								preStmt = null;
							}
							if (conn != null) {
								try {
									conn.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								conn = null;
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case "5":
//					System.out.println("删除数据。");
//					out.println("命令："); // 服务器返回提示信息
					BufferedReader in5 = null;
					PrintWriter out5 = null;
					try {
						InputStreamReader isr5;
						isr5 = new InputStreamReader(sk.getInputStream());
						in5 = new BufferedReader(isr5);
						out5 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())), true);
						// 获取命令
						// 例子：DELETE FROM access WHERE access_ '';
						String delectInfo = in5.readLine();
//						System.out.println(delectInfo);

						Connection conn = null;
						PreparedStatement preStmt = null;
						Statement stmt = null;
						try {
							// 1.注册数据库的驱动
							Class.forName(driverClass);
							// 2.通过Connection对象获取Statement对象
							conn = DriverManager.getConnection(jdbcUrl, username, password);
							// 3.创建statement类对象，用来执行SQL语句
							stmt = conn.createStatement();
							// 执行的SQL语句
							String sql = delectInfo;
							preStmt = conn.prepareStatement(sql);
							int i = stmt.executeUpdate(sql);// 执行，如果i>=1，成功，否则更新失败
							if (i >= 1) {
								out.println("删除成功！");
							} else {
								out.println("删除失败！");
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (preStmt != null) {
								try {
									preStmt.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								preStmt = null;
							}
							if (conn != null) {
								try {
									conn.close();
								} catch (SQLException e) {
									e.printStackTrace();
								}
								conn = null;
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case "6":
					out.println("再会！");
					return;
//					System.exit(0);
				default:
					out.println("请输入正确的指令！");
					break;
				}
//				out.println(new Date().toString() + ":" + cmd); // 服务器返回时间和客户发送来的消息
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (sk != null) {
					sk.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}