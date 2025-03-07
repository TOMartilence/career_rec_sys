import java.sql.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
abstract class User {
    protected String name;
    protected String password;
    protected String email;
    protected String phoneNumber;

    public User(String name, String password, String email, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public static void menu(String name,String password,String userType){}
    public User(){

    }

    abstract void insertIntoDatabase() ;
}

class Student extends User {
    private int standard;

    public Student(String name, String password, String email, String phoneNumber) {
        super(name, password, email, phoneNumber);
    }

    @Override
    public void insertIntoDatabase() {
        Scanner scanner = new Scanner(System.in);
        String insertQuery = "INSERT INTO student (name, password, email, phoneNumber, standard) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oops", "root", "akhil@5635");
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phoneNumber);

            System.out.println("Enter standard:");
            int standard = scanner.nextInt();
            preparedStatement.setInt(5, standard);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registration Successful!, student information inserted into the respective table.");
            } else {
                System.out.println("Registration Failed! ,student information not inserted into the respective table.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class Employee extends User {
    private double salary;

    public Employee(String name, String password, String email, String phoneNumber) {
        super(name, password, email, phoneNumber);
    }
    public Employee(){
        super();
    }

    public String retrieveStudentEmail(String studentId) {
        String selectQuery = "SELECT email FROM student WHERE id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oops", "root", "akhil@5635");
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, studentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("email");
                } else {
                    return "Student not found";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error occurred while retrieving email";
        }
    }
    public void sending(String studentId) {
        String studentEmail = retrieveStudentEmail(studentId);
        System.out.println("Sending email to student: " + studentEmail);
        openDefaultMailClient(studentEmail);

    }
    private void openDefaultMailClient(String mailId) {
        try {
            Desktop desktop = Desktop.getDesktop();
            URI mailtoURI = new URI("mailto:" + encodeURI(mailId));
            desktop.mail(mailtoURI);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String encodeURI(String uri) {
        try {
            return new URI(null, null, uri, null).toASCIIString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return uri;
        }
    }


    @Override
    public void insertIntoDatabase() {
        Scanner scanner = new Scanner(System.in);
        String insertQuery = "INSERT INTO employee (name, password, email, phoneNumber, salary) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oops", "root", "akhil@5635");
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phoneNumber);

            System.out.println("Enter salary:");
            double salary = scanner.nextDouble();
            preparedStatement.setDouble(5, salary);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registration Successful! ,Employee information inserted into the respective table.");
            } else {
                System.out.println("Registration Failed! ,Employee information not inserted into the respective table.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class Counselor extends User {
    private String educationQualification;
    private int experience;

    public Counselor(String name, String password, String email, String phoneNumber) {
        super(name, password, email, phoneNumber);
    }

    @Override
    public void insertIntoDatabase() {
        Scanner scanner = new Scanner(System.in);
        String insertQuery = "INSERT INTO counselor (name, password, email, phoneNumber, educationQualification, experience) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oops", "root", "akhil@5635");
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phoneNumber);

            scanner.nextLine(); // Consume the newline character
            System.out.println("Enter education qualification:");
            String educationQualification = scanner.nextLine();
            System.out.println("Enter experience (in years):");
            int experience = scanner.nextInt();

            preparedStatement.setString(5, educationQualification);
            preparedStatement.setInt(6, experience);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registration Successful! ,Counselor information inserted into the respective table.");
            } else {
                System.out.println("Registration Failed! ,Counselor information not inserted into the respective table.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void editCoursesTable() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the course to edit:");
        String courseName = scanner.nextLine();

        System.out.println("Enter the new information for the course:");

        System.out.print("New Name: ");
        String newName = scanner.nextLine();

        // Add more fields as needed

        String updateQuery = "UPDATE courses SET name=? WHERE name=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oops", "root", "akhil@5635");
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, courseName);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course information updated successfully!");
            } else {
                System.out.println("Course not found or update failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void editUniversityTable() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the university to edit:");
        String universityName = scanner.nextLine();

        System.out.println("Enter the new information for the university:");

        System.out.print("New Name: ");
        String newName = scanner.nextLine();

        System.out.print("New Location: ");
        String newLocation = scanner.nextLine();

        // Add more fields as needed

        String updateQuery = "UPDATE university SET name=?, location=? WHERE name=?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oops", "root", "akhil@5635");
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newLocation);
            preparedStatement.setString(3, universityName);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("University information updated successfully!");
            } else {
                System.out.println("University not found or update failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

class Administrator extends User {
    private double salary;

    public Administrator(String name, String password, String email, String phoneNumber) {
        super(name, password, email, phoneNumber);
    }

    @Override
    public void insertIntoDatabase() {
        Scanner scanner = new Scanner(System.in);
        String insertQuery = "INSERT INTO administrator (name, password, email, phoneNumber, salary) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oops", "root", "akhil@5635");
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phoneNumber);

            System.out.println("Enter salary:");
            double adminSalary = scanner.nextDouble();
            preparedStatement.setDouble(5, adminSalary);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registration Successful! ,Administrator information inserted into the respective table.");
            } else {
                System.out.println("Registration Failed! ,Administrator information not inserted into the respective table.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
interface UserAuthentication {
    boolean authenticateUser();
}
class login implements UserAuthentication{
    private String name;
    private String password;
    private String userType;
    public login(String name,String password,String userType){
        this.name=name;
        this.password=password;
        this.userType=userType;
    }
    public login(){}

    @Override
    public boolean authenticateUser() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/oops", "root", "akhil@5635")) {
            String query;
            PreparedStatement statement;
            ResultSet resultSet;

            switch (userType) {
                case "student":
                    query = "SELECT * FROM student WHERE name = ? AND password = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setString(2, password);
                    resultSet = statement.executeQuery();
                    return resultSet.next();

                case "employee":
                    query = "SELECT * FROM employee WHERE name = ? AND password = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setString(2, password);
                    resultSet = statement.executeQuery();
                    return resultSet.next();

                case "counselor":
                    query = "SELECT * FROM counselor WHERE name = ? AND password = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setString(2, password);
                    resultSet = statement.executeQuery();
                    return resultSet.next();

                case "administrator":
                    query = "SELECT * FROM administrator WHERE name = ? AND password = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, name);
                    statement.setString(2, password);
                    resultSet = statement.executeQuery();
                    return resultSet.next();

                default:
                    System.out.println("Invalid user type!");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

class UniversityDisplayFrame extends JFrame {
    private final ArrayList<String> universities;

    public UniversityDisplayFrame(ArrayList<String> universities) {
        this.universities = universities;
        initializeUniversityUI();

    }

    private void initializeUniversityUI() {
        setTitle("Universities Display");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create a label to display a message
        JLabel label = new JLabel("List of Universities:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        // Create a list to display universities
        JList<String> universitiesList = new JList<>(universities.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(universitiesList);
        panel.add(scrollPane);

        // Create a button to close the frame
        JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> dispose()); // Close the frame when the button is clicked
        panel.add(closeButton);

        // Add the panel to the frame
        add(panel);

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }
}

class Coursesdisplay extends JFrame {
    private final ArrayList<String> courses;

    public Coursesdisplay(ArrayList<String> courses) {
        this.courses = courses;

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Courses Display");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create a label to display a message
        JLabel label = new JLabel("List of Famous Courses:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        // Create a list to display universities
        JList<String> coursesList = new JList<>(courses.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(coursesList);
        panel.add(scrollPane);

        // Create a button to close the frame
        JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> dispose()); // Close the frame when the button is clicked
        panel.add(closeButton);

        // Add the panel to the frame
        add(panel);

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }
}
class QuizApp extends JFrame {

    private int totalMarks = 0;
    private JLabel scoreLabel;

    public QuizApp(String name, String password, String userType) {
        setTitle("Quiz Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        // Create panel and set layout
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        addQuestions(panel);

        // Add panel to frame
        add(panel, BorderLayout.CENTER);

        // Add submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            totalMarks = calculateTotalMarks(panel);
            updateScoreLabel();
        });

        // Add show total marks button
        JButton showTotalMarksButton = new JButton("Show Total Marks");
        showTotalMarksButton.addActionListener(e -> showTotalMarks());

        // Add close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose()); // Close the frame when the button is clicked

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(submitButton);
        buttonPanel.add(showTotalMarksButton);
        buttonPanel.add(closeButton);

        // Add button panel to frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Add score label
        scoreLabel = new JLabel("ALL THE BEST ");
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        add(scoreLabel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void addQuestions(JPanel panel) {
        // Sample questions and answers
        addQuestion(panel, "1. What is the capital of France?", "a. Paris Correct", "b. Rome", "c. Berlin", "d. Madrid");
        addQuestion(panel, "2. Which planet is known as the Red Planet?", "a. Earth", "b. Mars Correct", "c. Jupiter", "d. Venus");
        addQuestion(panel, "3. What is the largest mammal?", "a. Elephant", "b. Blue Whale Correct", "c. Giraffe", "d. Lion");
        addQuestion(panel, "4. Who wrote 'Romeo and Juliet'?", "a. Charles Dickens", "b. William Shakespeare Correct", "c. Jane Austen", "d. Mark Twain");
        addQuestion(panel, "5. What is the capital of Japan?", "a. Beijing", "b. Seoul", "c. Tokyo Correct", "d. Bangkok");
    }

    private void addQuestion(JPanel panel, String question, String... options) {
        panel.add(new JLabel(question));
        ButtonGroup group = new ButtonGroup();
        for (String option : options) {
            JRadioButton radioButton = new JRadioButton(option);
            group.add(radioButton);
            panel.add(radioButton);
        }
    }

    private int calculateTotalMarks(JPanel panel) {
        int total = 0;
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JRadioButton radioButton) {
                if (radioButton.isSelected() && radioButton.getText().endsWith("Correct")) {
                    total++;
                }
            }
        }
        return total;
    }

    private void updateScoreLabel() {
        scoreLabel.setText("ALL THE BEST " );
    }

    private void showTotalMarks() {
        JOptionPane.showMessageDialog(this, "Total Marks: " + tot
