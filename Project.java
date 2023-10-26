import java.util.*;
import java.io.*;
import java.sql.*;

public class Project {
    public static void main(String[] args) throws Exception {
        try {
            Scanner sc = new Scanner(System.in);
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println();
            System.out.println("Drivers load success.");

            System.out.println();
            String url = "jdbc:mysql://localhost/Project";
            String user = "root";
            String pass = "";

            Connection con = DriverManager.getConnection(url, user, pass);
            if (con != null)
                System.out.println("Connected to the database.");
            else
                System.out.println("Connection failed.");
            System.out.println();

            System.out.println("----------------------------------------------------------------------------------");
            System.out.println("                 WELCOME TO THE CONTACT MANAGEMENT SYSTEM                         ");
            System.out.println("                          JAVA INDIVISUAL PROJECT                                 ");
            System.out.println("----------------------------------------------------------------------------------");

            int choice = 0;

            HashMap<String, String> hm = new HashMap<>();
            Set s;

            do {
                System.out.println();
                System.out.println("Press 1 for add the new contact :");
                System.out.println("Press 2 for edit the contact :");
                System.out.println("Press 3 for delete the contact :");
                System.out.println("Press 4 for search the contact :");
                System.out.println("Press 5 to see all the contacts :");
                System.out.println("Press 6 for Export the contacts :");
                System.out.println("Press 7 for Truncate all the data of the database  :");
                System.out.println("Press 8 for Fvourites :");
                System.out.println("Press 9 for create the Group of the Contacts :");
                System.out.println("Press 10 to sea all the Groups and Export the Groups :");
                System.out.println("Press 11 for exit :");
                System.out.println();

                System.out.println("Enter your choice :");
                choice = sc.nextInt();
                System.out.println();
                switch (choice) {
                    case 1:
                        try {
                            System.out.println("Enter the required data related to the Contact information :");
                            System.out.println();

                            System.out.println("Enter the first name :");
                            String firstName = sc.next();
                            System.out.println();

                            System.out.println("Enter the last name :");
                            String lastName = sc.next();
                            System.out.println();

                            System.out.println("Enter the country code of the mobile number :");
                            String countryCode = sc.next();
                            System.out.println();

                            String mobile = "";
                            boolean check = false;
                            while (mobile.length() != 10 || check != true) {
                                System.out.println("Enter the mobile number ( MUST BE OF 10 DIGITS ) :");
                                mobile = sc.next();
                                System.out.println();

                                if (isValidMobileNumber(mobile)) {
                                    System.out.println("Valid mobile number.");
                                    check = true;
                                } else {
                                    System.out.println("Invalid mobile number.");
                                }
                                System.out.println();
                            }

                            System.out.println("Do you want to add the profile picture of the contact :");
                            String photocheck = sc.next();
                            System.out.println();

                            String photopath = "C://NA.jpg";

                            if (photocheck.equalsIgnoreCase("yes")) {
                                System.out.println("Enter the path of the profile picture :");
                                photopath = sc.next();
                                System.out.println();
                            }

                            File f1 = new File(photopath);
                            FileInputStream fis = new FileInputStream(f1);

                            if (!f1.exists())
                                f1.createNewFile();

                            System.out.println("Do you want to add the custom details :");
                            String custom = sc.next();
                            System.out.println();

                            String email = "NA";
                            String address = "NA";
                            String label = "NA";
                            String relation = "NA";

                            if (custom.equalsIgnoreCase("yes")) {
                                System.out.println("Write NA if not applicable :");
                                System.out.println();

                                System.out.println("Enter email :");
                                email = sc.next();
                                System.out.println();

                                System.out.println("Enter residental address :");
                                address = sc.next();
                                System.out.println();

                                System.out.println("Enter Label :");
                                label = sc.next();
                                System.out.println();

                                System.out.println("Enter relation :");
                                relation = sc.next();
                                System.out.println();
                            }

                            String sql1 = "insert into java values(?,?,?,?,?,?,?,?,?) ";
                            PreparedStatement pst1 = con.prepareStatement(sql1);

                            pst1.setString(1, firstName);
                            pst1.setString(2, lastName);
                            pst1.setString(3, countryCode);
                            pst1.setString(4, mobile);

                            if (f1.exists()) {
                                pst1.setBinaryStream(5, fis);
                            }
                            pst1.setString(6, email);
                            pst1.setString(7, address);
                            pst1.setString(8, label);
                            pst1.setString(9, relation);

                            int r1 = pst1.executeUpdate();

                            if (r1 > 0)
                                System.out.println("New contact added.");
                            else
                                System.out.println("Contact not added , try again.");

                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        System.out.println();
                        break;

                    case 2:
                        System.out.println();
                        System.out.println("Case 1 for changing the First and Last name ( NEED MOBILE ) : ");
                        System.out.println("Case 2 for changing the Mobile ( NEED FIRST AND LAST NAME ) :");
                        System.out.println("Case 3 for Return :");
                        System.out.println();

                        System.out.println("Enter your choice of editing :");
                        int edit = sc.nextInt();
                        System.out.println();

                        switch (edit) {
                            case 1:
                                System.out.println("Enter mobile :");
                                String mobile_1 = sc.next();
                                System.out.println();

                                System.out.println("Enter new first name :");
                                String new_firstName = sc.next();
                                System.out.println();

                                System.out.println("Enter new Last name :");
                                String new_lastName = sc.next();
                                System.out.println();

                                String sql_1 = "update java set firstName=(?),lastName=(?) where mobile=(?)";
                                PreparedStatement pst_1 = con.prepareStatement(sql_1);

                                pst_1.setString(1, new_firstName);
                                pst_1.setString(2, new_lastName);
                                pst_1.setString(3, mobile_1);
                                int update_r1 = pst_1.executeUpdate();

                                if (update_r1 > 0)
                                    System.out.println("Contact edited.");
                                else
                                    System.out.println("Contact not edited , Please try again.");

                                System.out.println();
                                break;

                            case 2:
                                System.out.println("Enter the First name :");
                                String firstName1 = sc.next();
                                System.out.println();

                                System.out.println("Enter Last name :");
                                String lastName1 = sc.next();
                                System.out.println();

                                System.out.println("Enter the new Mobile :");
                                String new_mobile = sc.next();
                                System.out.println();

                                String sql_2 = "update java set mobile =(?) where firstName=(?) and lastName=(?)";
                                PreparedStatement pst_2 = con.prepareStatement(sql_2);
                                pst_2.setString(1, new_mobile);
                                pst_2.setString(2, firstName1);
                                pst_2.setString(3, lastName1);

                                int update_r2 = pst_2.executeUpdate();
                                if (update_r2 > 0)
                                    System.out.println("Contact updated.");
                                else
                                    System.out.println("Contact not edited , Please try again.");

                                System.out.println();
                                break;

                            case 3:
                                System.out.println("Returning...");
                                break;

                            default:
                                System.out.println("Enter valid choice for editing :");
                                break;
                        }

                        break;

                    case 3:
                        System.out.println("Enter the mobile you want to delete :");
                        String delete = sc.next();
                        System.out.println();

                        String sql3 = "delete from java where mobile=(?)";
                        PreparedStatement pst3 = con.prepareStatement(sql3);
                        pst3.setString(1, delete);

                        int r = pst3.executeUpdate();

                        if (r > 0)
                            System.out.println("Contact deleted.");
                        else
                            System.out.println("Contact not deleted , Please try again.");
                        System.out.println();
                        break;

                    case 4:
                        System.out.println("To search the contact details :");
                        System.out.println("Enter the first name / last name / mobile / email of the contact :");
                        System.out.println();

                        String search = sc.next();

                        String sql4 = "select * from java where firstName=(?) or lastName=(?) or mobile=(?) or email=(?)";
                        PreparedStatement pst4 = con.prepareStatement(sql4);
                        pst4.setString(1, search);
                        pst4.setString(2, search);
                        pst4.setString(3, search);
                        pst4.setString(4, search);

                        ResultSet rs4 = pst4.executeQuery();
                        while (rs4.next()) {
                            System.out.println();
                            System.out.println("First name      : " + rs4.getString(1));
                            System.out.println("Last name       : " + rs4.getString(2));
                            System.out.println("Country code    : " + rs4.getString(3));
                            System.out.println("Mobile          : " + rs4.getString(4));
                            System.out.println("Email           : " + rs4.getString(6));
                            System.out.println("Address         : " + rs4.getString(7));
                            System.out.println("Label           : " + rs4.getString(8));
                            System.out.println("Relation        : " + rs4.getString(9));
                            System.out.println();
                        }
                        break;

                    case 5:
                        String sql5 = "select * from java";
                        PreparedStatement pst5 = con.prepareStatement(sql5);

                        ResultSet rs = pst5.executeQuery();

                        while (rs.next()) {
                            System.out.println();
                            System.out.println("First name      : " + rs.getString(1));
                            System.out.println("Last name       : " + rs.getString(2));
                            System.out.println("Country code    : " + rs.getString(3));
                            System.out.println("Mobile          : " + rs.getString(4));
                            System.out.println("Email           : " + rs.getString(6));
                            System.out.println("Address         : " + rs.getString(7));
                            System.out.println("Label           : " + rs.getString(8));
                            System.out.println("Relation        : " + rs.getString(9));
                            System.out.println();
                        }
                        break;

                    case 6:
                        File contactFile = new File("Contacts.txt");
                        FileWriter fw = new FileWriter(contactFile);
                        BufferedWriter bw = new BufferedWriter(fw);

                        String sql6 = "select * from java";
                        PreparedStatement pst6 = con.prepareStatement(sql6);
                        ResultSet rSet = pst6.executeQuery();

                        while (rSet.next()) {
                            bw.newLine();
                            bw.write("First name      : " + rSet.getString(1));
                            bw.newLine();
                            bw.write("Last name       : " + rSet.getString(2));
                            bw.newLine();
                            bw.write("Country code    : " + rSet.getString(3));
                            bw.newLine();
                            bw.write("Mobile          : " + rSet.getString(4));
                            bw.newLine();
                            bw.write("Email           : " + rSet.getString(6));
                            bw.newLine();
                            bw.write("Address         : " + rSet.getString(7));
                            bw.newLine();
                            bw.write("Label           : " + rSet.getString(8));
                            bw.newLine();
                            bw.write("Relation        : " + rSet.getString(9));
                            bw.newLine();
                            bw.flush();

                        }

                        System.out.println("Contacts.txt File is generated.");
                        System.out.println();
                        break;

                    case 7:
                        String sql7 = "Truncate table java";
                        PreparedStatement pst7 = con.prepareStatement(sql7);
                        pst7.executeUpdate();

                        System.out.println("Truncate operation success.");
                        System.out.println();
                        break;

                    case 8:
                        System.out.println("Enter your contacts to the Favourites :");
                        System.out.println();

                        System.out.println("How many contacts do you want to enter in favourites :");
                        int loop = sc.nextInt();
                        System.out.println();

                        class Favourites {
                            String fname;
                            String lname;
                            String mobile;

                            public Favourites(String fname, String lname, String mobile) {
                                this.fname = fname;
                                this.lname = lname;
                                this.mobile = mobile;
                            }

                            public String getFname() {
                                return fname;
                            }

                            public String getLname() {
                                return lname;
                            }

                            public String getMobile() {
                                return mobile;
                            }

                        }

                        ArrayList<Favourites> al = new ArrayList<>();
                        Favourites f;

                        for (int i = 1; i <= loop; i++) {

                            System.out.println("Enter first name of contact " + i);
                            String fn = sc.next();
                            System.out.println();

                            System.out.println("Enter last name of contact " + i);
                            String ln = sc.next();
                            System.out.println();

                            System.out.println("Enter mobile :");
                            String mobile = sc.next();
                            System.out.println();

                            f = new Favourites(fn, ln, mobile);
                            al.add(f);
                        }

                        System.out.println("Favourites added.");

                        break;

                    case 9:
                        System.out.println("Enter the Group name :");
                        String groupName = sc.next();
                        System.out.println();

                        System.out.println("How many contacts do you want to enter in your Group :");
                        int counter = sc.nextInt();
                        System.out.println();

                        for (int i = 1; i <= counter; i++) {

                            System.out.println("Enter the Full name :");
                            String GRP_FullName = sc.next();
                            System.out.println();

                            String GRP_mobile = "";
                            boolean check2 = false;
                            while (GRP_mobile.length() != 10 || check2 != true) {
                                System.out.println("Enter the mobile number ( MUST BE OF 10 DIGITS ) :");
                                GRP_mobile = sc.next();
                                System.out.println();

                                if (isValidMobileNumber(GRP_mobile)) {
                                    System.out.println("Valid mobile number.");
                                    check2 = true;
                                } else {
                                    System.out.println("Invalid mobile number.");
                                }
                                System.out.println();
                            }
                            hm.put(GRP_mobile, GRP_FullName);
                            System.out.println("Contact added into the Group.");
                            System.out.println();
                        }
                        break;

                    case 10:
                        File file = new File("Groups.txt");
                        BufferedWriter bw_GRP = new BufferedWriter(new FileWriter(file));

                        for (String object : hm.keySet()) {
                            System.out.println(object + " : " + hm.get(object));

                            bw_GRP.write("Mobile     : " + object);
                            bw_GRP.newLine();
                            bw_GRP.write("Full name  : " + hm.get(object));
                            bw_GRP.newLine();
                            bw_GRP.newLine();
                            bw_GRP.flush();
                        }
                        System.out.println();
                        System.out.println("Groups.txt File is generated.");
                        System.out.println();
                        break;

                    case 11:
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Enter valid choice :");
                        break;

                }
            } while (choice != 11);
        } catch (

        Exception e) {
            System.out.println(e);
        }
    }

    public static boolean isValidMobileNumber(String mobileNumber) {

        for (char c : mobileNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
