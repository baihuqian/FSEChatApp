package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtil implements DBControl{
	private Connection connection; //contain connection that can instantiate Statement object

	public DBUtil() {	}
	public DBUtil(Connection connection) {
		this.connection = connection;
		try {
			DatabaseMetaData dbm = connection.getMetaData();
			// check if "employee" table is there
			ResultSet tables = dbm.getTables(null, null, "chat", null);
			Statement myStatement = connection.createStatement();
			if (tables.next()) {
				// Table exists

				myStatement.executeUpdate("DROP TABLE chat;");
			}
			myStatement.executeUpdate("CREATE TABLE chat " +
					"(id INTEGER not NULL AUTO_INCREMENT, " +
					" user VARCHAR(255), " + 
					" time VARCHAR(255), " + 
					" content VARCHAR(255), " + 
					" PRIMARY KEY ( id ));");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void executeSQLFile(String SQLFILE) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(SQLFILE)); // open file
			if(VERBOSE) {
				System.out.println("Reading SQL File...");
			}
			String line = "";
			StringBuilder sb = new StringBuilder();

			while( (line = br.readLine()) != null) {
				if(line.length() == 0 || line.startsWith("--")) { // empty line or comment line
					continue;
				} else {
					sb.append(line); // valid line
				} 

				if(line.trim().endsWith(";")) { //one command end
					Statement myStatement = connection.createStatement();
					myStatement.execute(sb.toString()); // execute command
					sb = new StringBuilder(); //flush command buffer
					myStatement.close(); // close Statement
				}
			}
			br.close(); //done
			if(VERBOSE) {
				System.out.println("SQL File Done");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public void addPost(String userName, String time, String content) {
		String command = "INSERT INTO `chat` (`user`, `time`, `content`) VALUES ('" + userName + "','" + time + "','" + content + "');";
		try {
			Statement myStatement = connection.createStatement();
			myStatement.executeUpdate(command);
			if(VERBOSE) {
				System.out.println(command + "... Done");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void retrievePosts(int n) {
		String command = "SELECT * FROM `chat` ORDER BY `key` DESC LIMIT " + n;
		try {
			Statement myStatement = connection.createStatement();
			ResultSet rs = myStatement.executeQuery(command);

			while(rs.next()) {
				String name = rs.getNString(2);
				String time = rs.getNString(3);
				String content = rs.getNString(4);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
