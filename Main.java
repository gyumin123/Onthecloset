package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ArrayList<HashMap<String, String>> users;

    // Constructor
    public Main() {
        users = new ArrayList<>();
        setTitle("회원가입 및 조회 시스템");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Adding panels to CardLayout
        mainPanel.add(createWelcomePanel(), "welcome");
        mainPanel.add(createRegisterPanel(), "register");
        mainPanel.add(createCheckVisitPanel(), "checkVisit");

        add(mainPanel);
        cardLayout.show(mainPanel, "welcome");
        setVisible(true);
    }

    // Welcome panel
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Setting font to Dialog
        Font font = new Font("Dialog", Font.BOLD, 20);

        JLabel welcomeLabel = new JLabel("회원가입 및 조회 시스템", SwingConstants.CENTER);
        welcomeLabel.setFont(font);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JLabel helloLabel = new JLabel("안녕하세요", SwingConstants.CENTER);
        helloLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        panel.add(helloLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton registerButton = new JButton("회원가입");
        JButton checkVisitButton = new JButton("방문횟수 확인");

        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "register"));
        checkVisitButton.addActionListener(e -> cardLayout.show(mainPanel, "checkVisit"));

        buttonPanel.add(registerButton);
        buttonPanel.add(checkVisitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Register panel
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(15);
        JLabel nameLabel = new JLabel("이름:");
        JTextField nameField = new JTextField(15);
        JLabel pwLabel = new JLabel("비밀번호:");
        JPasswordField pwField = new JPasswordField(15);
        JLabel pwConfirmLabel = new JLabel("비밀번호 확인:");
        JPasswordField pwConfirmField = new JPasswordField(15);
        JLabel birthLabel = new JLabel("생년월일(6자리):");
        JTextField birthField = new JTextField(15);
        JLabel emailLabel = new JLabel("이메일:");
        JTextField emailField = new JTextField(15);
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);

        JButton submitButton = new JButton("제출");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = idField.getText().trim();
                String userName = nameField.getText().trim();
                String password = new String(pwField.getPassword()).trim();
                String passwordConfirm = new String(pwConfirmField.getPassword()).trim();
                String birth = birthField.getText().trim();
                String email = emailField.getText().trim();

                if (userId.isEmpty() || userName.isEmpty() || password.isEmpty() ||
                    passwordConfirm.isEmpty() || birth.isEmpty() || email.isEmpty()) {
                    messageLabel.setText("모든 필드를 입력해주세요.");
                    return;
                }

                if (!password.equals(passwordConfirm)) {
                    messageLabel.setText("비밀번호가 일치하지 않습니다.");
                    return;
                }

                if (birth.length() != 6) {
                    messageLabel.setText("생년월일을 6자리로 입력해주세요.");
                    return;
                }

                HashMap<String, String> user = new HashMap<>();
                user.put("ID", userId);
                user.put("이름", userName);
                user.put("비밀번호", password);
                user.put("생년월일", birth);
                user.put("이메일", email);
                user.put("방문횟수", "0");
                user.put("구매횟수", "0");
                users.add(user);

                messageLabel.setText(userName + "님 가입을 환영합니다.");
                clearFields(idField, nameField, pwField, pwConfirmField, birthField, emailField);

                // 가입 후 첫 화면으로 돌아가기
                Timer timer = new Timer(2000, event -> cardLayout.show(mainPanel, "welcome"));
                timer.setRepeats(false);
                timer.start();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(pwLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(pwField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(pwConfirmLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(pwConfirmField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(birthLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(birthField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(submitButton, gbc);

        return panel;
    }

    // Clear input fields
    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    // Check visit panel
    private JPanel createCheckVisitPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel instructionLabel = new JLabel("회원의 ID, 이름, 이메일 또는 생년월일을 입력하세요:", SwingConstants.CENTER);
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("검색");
        searchButton.setPreferredSize(new Dimension(80, 30));
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchKey = searchField.getText().trim();
                boolean found = false;

                for (HashMap<String, String> user : users) {
                    if (user.get("ID").equalsIgnoreCase(searchKey) ||
                        user.get("이름").equalsIgnoreCase(searchKey) ||
                        user.get("이메일").equalsIgnoreCase(searchKey) ||
                        user.get("생년월일").equalsIgnoreCase(searchKey)) {

                        found = true;
                        int visitCount = Integer.parseInt(user.get("방문횟수"));
                        int purchaseCount = Integer.parseInt(user.get("구매횟수"));

                        // 방문 횟수 증가
                        visitCount++;
                        user.put("방문횟수", String.valueOf(visitCount));

                        // 구매 여부 확인 다이얼로그
                        int dialogResult = JOptionPane.showConfirmDialog(panel, "구매를 하셨습니까?", "구매 확인", JOptionPane.YES_NO_OPTION);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            purchaseCount++;
                            user.put("구매횟수", String.valueOf(purchaseCount));
                        }

                        // 결과 출력
                        StringBuilder result = new StringBuilder();
                        result.append("회원 ").append(user.get("이름")).append("을(를) 찾았습니다.\n");
                        result.append("방문 횟수: ").append(visitCount).append("\n");
                        result.append("구매 횟수: ").append(purchaseCount).append("\n");

                        int totalVisits = visitCount + purchaseCount;
                        String grade = "BRONZE";
                        if (totalVisits > 3 && totalVisits <= 9) {
                            grade = "SILVER";
                        } else if (totalVisits > 9) {
                            grade = "GOLD";
                        }
                        result.append("등급: ").append(grade).append("\n");

                        resultLabel.setText(result.toString());
                        break;
                    }
                }

                if (!found) {
                    resultLabel.setText("회원을 찾을 수 없습니다.");
                }
            }
        });

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        topPanel.add(instructionLabel);
        topPanel.add(searchField);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(searchButton, BorderLayout.CENTER);
        panel.add(resultLabel, BorderLayout.SOUTH);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
