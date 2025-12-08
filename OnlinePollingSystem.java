import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        }
    }
    
    public int getVotes(int optionIndex) {
        return votes.get(optionIndex);
    }
    
    public int getTotalVotes() {
        int total = 0;
        for (int vote : votes) {
            total += vote;
        }
        return total;
    }
}

// Main GUI Application
public class OnlinePollingSystem extends JFrame {
    private ArrayList<Poll> polls;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    public OnlinePollingSystem() {
        polls = new ArrayList<>();
        
        setTitle("Online Polling System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        mainPanel.add(createHomePanel(), "HOME");
        mainPanel.add(createCreatePollPanel(), "CREATE");
        mainPanel.add(createVotePanel(), "VOTE");
        mainPanel.add(createResultsPanel(), "RESULTS");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "HOME");
    }
    
    // Home Panel
    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 250));
        
        JLabel titleLabel = new JLabel("Online Polling System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(new Color(50, 50, 150));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 20, 20));
        buttonPanel.setBackground(new Color(240, 240, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 100, 150));
        
        JButton createBtn = createStyledButton("Create New Poll", new Color(70, 130, 180));
        JButton voteBtn = createStyledButton("Vote on a Poll", new Color(60, 179, 113));
        JButton resultsBtn = createStyledButton("View Results", new Color(255, 140, 0));
        JButton exitBtn = createStyledButton("Exit", new Color(220, 60, 60));
        
        createBtn.addActionListener(e -> cardLayout.show(mainPanel, "CREATE"));
        voteBtn.addActionListener(e -> {
            refreshVotePanel();
            cardLayout.show(mainPanel, "VOTE");
        });
        resultsBtn.addActionListener(e -> {
            refreshResultsPanel();
            cardLayout.show(mainPanel, "RESULTS");
        });
        exitBtn.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(createBtn);
        buttonPanel.add(voteBtn);
        buttonPanel.add(resultsBtn);
        buttonPanel.add(exitBtn);
        
        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }
    
    // Create Poll Panel
    private JPanel createCreatePollPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Create New Poll", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        JLabel questionLabel = new JLabel("Poll Question:");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField questionField = new JTextField();
        questionField.setMaximumSize(new Dimension(600, 30));
        
        formPanel.add(questionLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(questionField);
        formPanel.add(Box.createVerticalStrut(20));
        
        JLabel optionsLabel = new JLabel("Options (one per line, minimum 2):");
        optionsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextArea optionsArea = new JTextArea(8, 40);
        optionsArea.setLineWrap(true);
        optionsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(optionsArea);
        scrollPane.setMaximumSize(new Dimension(600, 150));
        
        formPanel.add(optionsLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(scrollPane);
        formPanel.add(Box.createVerticalStrut(20));
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton createBtn = new JButton("Create Poll");
        createBtn.setBackground(new Color(70, 130, 180));
        createBtn.setForeground(Color.WHITE);
        createBtn.setFont(new Font("Arial", Font.BOLD, 14));
        createBtn.setFocusPainted(false);
        
        JButton backBtn = new JButton("Back to Home");
        backBtn.setBackground(new Color(150, 150, 150));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        
        createBtn.addActionListener(e -> {
            String question = questionField.getText().trim();
            String optionsText = optionsArea.getText().trim();
            
            if (question.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a question!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String[] optionLines = optionsText.split("\n");
            ArrayList<String> options = new ArrayList<>();
            for (String line : optionLines) {
                if (!line.trim().isEmpty()) {
                    options.add(line.trim());
                }
            }
            
            if (options.size() < 2) {
                JOptionPane.showMessageDialog(this, "Please enter at least 2 options!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Poll newPoll = new Poll(question, options);
            polls.add(newPoll);
            
            JOptionPane.showMessageDialog(this, "Poll created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            questionField.setText("");
            optionsArea.setText("");
            cardLayout.show(mainPanel, "HOME");
        });
        
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        
        buttonPanel.add(createBtn);
        buttonPanel.add(backBtn);
        formPanel.add(buttonPanel);
        
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }
    
    // Vote Panel
    private JPanel createVotePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setName("VOTE_PANEL");
        
        JLabel titleLabel = new JLabel("Vote on a Poll", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JButton backBtn = new JButton("Back to Home");
        backBtn.setBackground(new Color(150, 150, 150));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(backBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshVotePanel() {
        Component[] components = mainPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel && "VOTE_PANEL".equals(comp.getName())) {
                JPanel votePanel = (JPanel) comp;
                votePanel.removeAll();
                
                JLabel titleLabel = new JLabel("Vote on a Poll", SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
                titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                votePanel.add(titleLabel, BorderLayout.NORTH);
                
                JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                contentPanel.setBackground(Color.WHITE);
                contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
                
                if (polls.isEmpty()) {
                    JLabel noPolls = new JLabel("No polls available. Create one first!");
                    noPolls.setFont(new Font("Arial", Font.PLAIN, 16));
                    contentPanel.add(noPolls);
                } else {
                    for (int i = 0; i < polls.size(); i++) {
                        final int pollIndex = i;
                        Poll poll = polls.get(i);
                        
                        JPanel pollPanel = new JPanel();
                        pollPanel.setLayout(new BoxLayout(pollPanel, BoxLayout.Y_AXIS));
                        pollPanel.setBackground(new Color(245, 245, 255));
                        pollPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                            BorderFactory.createEmptyBorder(15, 15, 15, 15)
                        ));
                        
                        JLabel questionLabel = new JLabel((i + 1) + ". " + poll.getQuestion());
                        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
                        pollPanel.add(questionLabel);
                        pollPanel.add(Box.createVerticalStrut(10));
                        
                        ButtonGroup buttonGroup = new ButtonGroup();
                        ArrayList<JRadioButton> radioButtons = new ArrayList<>();
                        
                        for (int j = 0; j < poll.getOptions().size(); j++) {
                            JRadioButton radioButton = new JRadioButton(poll.getOptions().get(j));
                            radioButton.setBackground(new Color(245, 245, 255));
                            radioButton.setFont(new Font("Arial", Font.PLAIN, 14));
                            buttonGroup.add(radioButton);
                            radioButtons.add(radioButton);
                            pollPanel.add(radioButton);
                        }
                        
                        pollPanel.add(Box.createVerticalStrut(10));
                        
                        JButton voteBtn = new JButton("Submit Vote");
                        voteBtn.setBackground(new Color(60, 179, 113));
                        voteBtn.setForeground(Color.WHITE);
                        voteBtn.setFont(new Font("Arial", Font.BOLD, 12));
                        voteBtn.setFocusPainted(false);
                        voteBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
                        
                        voteBtn.addActionListener(e -> {
                            int selectedOption = -1;
                            for (int k = 0; k < radioButtons.size(); k++) {
                                if (radioButtons.get(k).isSelected()) {
                                    selectedOption = k;
                                    break;
                                }
                            }
                            
                            if (selectedOption != -1) {
                                polls.get(pollIndex).vote(selectedOption);
                                JOptionPane.showMessageDialog(this, "Vote recorded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                buttonGroup.clearSelection();
                            } else {
                                JOptionPane.showMessageDialog(this, "Please select an option!", "Error", JOptionPane.WARNING_MESSAGE);
                            }
                        });
                        
                        pollPanel.add(voteBtn);
                        contentPanel.add(pollPanel);
                        contentPanel.add(Box.createVerticalStrut(15));
                    }
                }
                
                JScrollPane scrollPane = new JScrollPane(contentPanel);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                votePanel.add(scrollPane, BorderLayout.CENTER);
                
                JButton backBtn = new JButton("Back to Home");
                backBtn.setBackground(new Color(150, 150, 150));
                backBtn.setForeground(Color.WHITE);
                backBtn.setFont(new Font("Arial", Font.BOLD, 14));
                backBtn.setFocusPainted(false);
                backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
                
                JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                bottomPanel.setBackground(Color.WHITE);
                bottomPanel.add(backBtn);
                votePanel.add(bottomPanel, BorderLayout.SOUTH);
                
                votePanel.revalidate();
                votePanel.repaint();
                break;
            }
        }
    }
    
    // Results Panel
    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setName("RESULTS_PANEL");
        
        JLabel titleLabel = new JLabel("Poll Results", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JButton backBtn = new JButton("Back to Home");
        backBtn.setBackground(new Color(150, 150, 150));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(backBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshResultsPanel() {
        Component[] components = mainPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel && "RESULTS_PANEL".equals(comp.getName())) {
                JPanel resultsPanel = (JPanel) comp;
                resultsPanel.removeAll();
                
                JLabel titleLabel = new JLabel("Poll Results", SwingConstants.CENTER);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
                titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                resultsPanel.add(titleLabel, BorderLayout.NORTH);
                
                JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
                contentPanel.setBackground(Color.WHITE);
                contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
                
                if (polls.isEmpty()) {
                    JLabel noPolls = new JLabel("No polls available.");
                    noPolls.setFont(new Font("Arial", Font.PLAIN, 16));
                    contentPanel.add(noPolls);
                } else {
                    for (int i = 0; i < polls.size(); i++) {
                        Poll poll = polls.get(i);
                        
                        JPanel pollPanel = new JPanel();
                        pollPanel.setLayout(new BoxLayout(pollPanel, BoxLayout.Y_AXIS));
                        pollPanel.setBackground(new Color(255, 250, 240));
                        pollPanel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(255, 140, 0), 2),
                            BorderFactory.createEmptyBorder(15, 15, 15, 15)
                        ));
                        
                        JLabel questionLabel = new JLabel((i + 1) + ". " + poll.getQuestion());
                        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
                        pollPanel.add(questionLabel);
                        pollPanel.add(Box.createVerticalStrut(10));
                        
                        int totalVotes = poll.getTotalVotes();
                        JLabel totalLabel = new JLabel("Total Votes: " + totalVotes);
                        totalLabel.setFont(new Font("Arial", Font.ITALIC, 12));
                        pollPanel.add(totalLabel);
                        pollPanel.add(Box.createVerticalStrut(10));
                        
                        for (int j = 0; j < poll.getOptions().size(); j++) {
                            int votes = poll.getVotes(j);
                            double percentage = totalVotes > 0 ? (votes * 100.0 / totalVotes) : 0;
                            
                            JLabel optionLabel = new JLabel(poll.getOptions().get(j));
                            optionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                            pollPanel.add(optionLabel);
                            
                            JPanel barPanel = new JPanel();
                            barPanel.setLayout(new BoxLayout(barPanel, BoxLayout.X_AXIS));
                            barPanel.setBackground(new Color(255, 250, 240));
                            
                            JProgressBar progressBar = new JProgressBar(0, 100);
                            progressBar.setValue((int) percentage);
                            progressBar.setStringPainted(true);
                            progressBar.setString(votes + " votes (" + String.format("%.1f", percentage) + "%)");
                            progressBar.setPreferredSize(new Dimension(400, 25));
                            progressBar.setMaximumSize(new Dimension(400, 25));
                            progressBar.setForeground(new Color(60, 179, 113));
                            
                            barPanel.add(progressBar);
                            barPanel.add(Box.createHorizontalGlue());
                            pollPanel.add(barPanel);
                            pollPanel.add(Box.createVerticalStrut(5));
                        }
                        
                        contentPanel.add(pollPanel);
                        contentPanel.add(Box.createVerticalStrut(15));
                    }
                }
                
                JScrollPane scrollPane = new JScrollPane(contentPanel);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                resultsPanel.add(scrollPane, BorderLayout.CENTER);
                
                JButton backBtn = new JButton("Back to Home");
                backBtn.setBackground(new Color(150, 150, 150));
                backBtn.setForeground(Color.WHITE);
                backBtn.setFont(new Font("Arial", Font.BOLD, 14));
                backBtn.setFocusPainted(false);
                backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
                
                JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                bottomPanel.setBackground(Color.WHITE);
                bottomPanel.add(backBtn);
                resultsPanel.add(bottomPanel, BorderLayout.SOUTH);
                
                resultsPanel.revalidate();
                resultsPanel.repaint();
                break;
            }
        }
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OnlinePollingSystem app = new OnlinePollingSystem();
            app.setVisible(true);
        });
    }
}
