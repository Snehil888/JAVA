import java.util.*;

// Class to represent a Poll
class Poll {
    private String question;
    private ArrayList<String> options;
    private ArrayList<Integer> votes;
    
    public Poll(String question, ArrayList<String> options) {
        this.question = question;
        this.options = options;
        this.votes = new ArrayList<>();
        // Initialize votes to 0 for each option
        for (int i = 0; i < options.size(); i++) {
            votes.add(0);
        }
    }
    
    public String getQuestion() {
        return question;
    }
    
    public ArrayList<String> getOptions() {
        return options;
    }
    
    public void vote(int optionIndex) {
        if (optionIndex >= 0 && optionIndex < options.size()) {
            votes.set(optionIndex, votes.get(optionIndex) + 1);
            System.out.println("Vote recorded successfully!");
        } else {
            System.out.println("Invalid option!");
        }
    }
    
    public void displayResults() {
        System.out.println("\n--- Poll Results ---");
        System.out.println("Question: " + question);
        System.out.println("-------------------");
        
        int totalVotes = 0;
        for (int vote : votes) {
            totalVotes += vote;
        }
        
        for (int i = 0; i < options.size(); i++) {
            int voteCount = votes.get(i);
            double percentage = totalVotes > 0 ? (voteCount * 100.0 / totalVotes) : 0;
            System.out.printf("%d. %s: %d votes (%.1f%%)\n", 
                i + 1, options.get(i), voteCount, percentage);
        }
        System.out.println("Total votes: " + totalVotes);
        System.out.println("-------------------\n");
    }
}

// Main Polling System class
public class OnlinePollingSystem {
    private static ArrayList<Poll> polls = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("  Welcome to Online Polling System");
        System.out.println("=================================\n");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createPoll();
                    break;
                case 2:
                    viewPolls();
                    break;
                case 3:
                    votePoll();
                    break;
                case 4:
                    viewResults();
                    break;
                case 5:
                    System.out.println("\nThank you for using the Polling System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.\n");
            }
        }
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("--- Main Menu ---");
        System.out.println("1. Create a new poll");
        System.out.println("2. View all polls");
        System.out.println("3. Vote on a poll");
        System.out.println("4. View poll results");
        System.out.println("5. Exit");
        System.out.println("-----------------");
    }
    
    private static void createPoll() {
        System.out.println("\n--- Create New Poll ---");
        System.out.print("Enter poll question: ");
        String question = scanner.nextLine();
        
        int numOptions = getIntInput("How many options? (minimum 2): ");
        while (numOptions < 2) {
            System.out.println("Please enter at least 2 options.");
            numOptions = getIntInput("How many options? (minimum 2): ");
        }
        
        ArrayList<String> options = new ArrayList<>();
        for (int i = 1; i <= numOptions; i++) {
            System.out.print("Enter option " + i + ": ");
            options.add(scanner.nextLine());
        }
        
        Poll newPoll = new Poll(question, options);
        polls.add(newPoll);
        System.out.println("\nPoll created successfully!\n");
    }
    
    private static void viewPolls() {
        System.out.println("\n--- All Polls ---");
        if (polls.isEmpty()) {
            System.out.println("No polls available yet.\n");
            return;
        }
        
        for (int i = 0; i < polls.size(); i++) {
            System.out.println((i + 1) + ". " + polls.get(i).getQuestion());
        }
        System.out.println();
    }
    
    private static void votePoll() {
        if (polls.isEmpty()) {
            System.out.println("\nNo polls available to vote on.\n");
            return;
        }
        
        viewPolls();
        int pollIndex = getIntInput("Select poll number to vote: ") - 1;
        
        if (pollIndex < 0 || pollIndex >= polls.size()) {
            System.out.println("Invalid poll number!\n");
            return;
        }
        
        Poll selectedPoll = polls.get(pollIndex);
        System.out.println("\nQuestion: " + selectedPoll.getQuestion());
        ArrayList<String> options = selectedPoll.getOptions();
        
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        
        int optionChoice = getIntInput("\nEnter your choice (1-" + options.size() + "): ") - 1;
        selectedPoll.vote(optionChoice);
        System.out.println();
    }
    
    private static void viewResults() {
        if (polls.isEmpty()) {
            System.out.println("\nNo polls available.\n");
            return;
        }
        
        viewPolls();
        int pollIndex = getIntInput("Select poll number to view results: ") - 1;
        
        if (pollIndex < 0 || pollIndex >= polls.size()) {
            System.out.println("Invalid poll number!\n");
            return;
        }
        
        polls.get(pollIndex).displayResults();
    }
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Please enter a valid number: ");
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return input;
    }
}