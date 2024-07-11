import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	
		// TODO Auto-generated method stub
		private static final String url = "jdbc:mysql://localhost:3306/?user=root";
		private static final String username = "root";
		private static final String password = "4092";
		 
		public static void main(String[] args) {
		    try {
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        System.out.println("Driver loaded Successfully..!");
		    } catch (ClassNotFoundException e) {
		        System.out.println("Driver Load Fail..!");
		    }

		    try (Connection conn = DriverManager.getConnection(url, username, password);
		         Scanner scanner = new Scanner(System.in)) {

		        User user = new User(conn, scanner);
		        Accounts accounts = new Accounts(conn, scanner);
		        AccountsManager accountsManager = new AccountsManager(conn, scanner);

		        String email;
		        long account_number;

		        while (true) {
		            System.out.println("*** WELCOME TO BANKING SYSTEM ***");
		            System.out.println();
		            System.out.println("1. Register");
		            System.out.println("2. Login");
		            System.out.println("3. Exit");

		            System.out.println("Enter Your Choice");
		            int choice = scanner.nextInt();

		            switch (choice) {
		                case 1:
		                    user.register();
		                    break;
		                case 2:
		                    email = user.login();
		                    if (email != null) {
		                        System.out.println();
		                        System.out.println("User Logged In!");
		                        if (!accounts.account_exist(email)) {
		                            System.out.println();
		                            System.out.println("1. Open Bank Account");
		                            System.out.println("2. Exit");
		                            if (scanner.nextInt() == 1) {
		                                account_number = accounts.open_account(email);
		                                System.out.println("Account Created Successfully");
		                            } else {
		                                break;
		                            }
		                        }
		                        account_number = accounts.get_Accountnumber(email);
		                        int choice2 = 0;
		                        while (choice2 != 5) {
		                            System.out.println("1. Debit Money");
		                            System.out.println("2. Credit Money");
		                            System.out.println("3. Transfer money");
		                            System.out.println("4. Check Balance");
		                            System.out.println("5. Logout");

		                            choice2 = scanner.nextInt();
		                            switch (choice2) {
		                                case 1:
		                                    accountsManager.debit_money(account_number);
		                                    break;
		                                case 2:
		                                    accountsManager.credit_money(account_number);
		                                    break;
		                                case 3:
		                                    accountsManager.transfer_money(account_number);
		                                    break;
		                                case 4:
		                                    accountsManager.check_balance(account_number);
		                                    break;
		                                case 5:
		                                    break;
		                                default:
		                                    System.out.println("Enter Valid Choice!");
		                                    break;
		                            }
		                        }
		                    } else {
		                        System.out.println("Incorrect Email or password");
		                    }
		                    break;
		                case 3:
		                    System.out.println("THANK YOU FOR USING BANKING SYSTEM");
		                    return;
		                default:
		                    System.out.println("Enter Valid Choice!");
		                    break;
		            }
		        }
		    } catch (SQLException e) {
		        System.out.println("Connection Problem!");
		        e.printStackTrace();
		    }
		}


}
