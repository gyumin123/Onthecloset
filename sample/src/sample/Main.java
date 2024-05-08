package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("=============================");
        System.out.println("회원 등록");
        System.out.println("=============================");

        boolean register = false;
        Scanner sc = new Scanner(System.in);

        // 자료 구조를 활용한 회원 정보 관리
        ArrayList<HashMap<String, String>> users = new ArrayList<>();

        while (!register) {
            System.out.println("회원가입을 하시겠습니까?\nY: 진행 N: 취소");
            System.out.println(" >> ");
            String registerInput = sc.nextLine();

            // 대문자 소문자 상관없음! equalsIgnoreCase
            if (registerInput.equalsIgnoreCase("y")) {
                register = true;
                System.out.println("=============================");
                System.out.println("회원가입이 진행됩니다.");
                System.out.println("=============================");

            } else if (registerInput.equalsIgnoreCase("n")) {
                System.out.println("=============================");
                System.out.println("회원가입이 종료됩니다.");
                System.out.println("=============================");
                System.exit(0);

            } else {
                System.out.println("입력값을 확인해주세요.");
            }

            while (true) {
                HashMap<String, String> user = new HashMap<>();

                //ID
                System.out.print("ID: ");
                String userId = sc.nextLine();
                user.put("ID", userId);

                // Name
                System.out.println("Name: ");
                String userName = sc.nextLine();
                user.put("이름", userName);

                //pw
                String password = "";
                while (true) {
                    System.out.println("PW: ");
                    password = sc.nextLine();
                    System.out.print("PW확인: ");
                    String passwordConfirm = sc.nextLine();

                    if (password.equals(passwordConfirm)) {
                        break;
                    } else {
                        System.out.println("=============================");
                        System.out.println("비밀번호가 일치하지 않습니다. \n 비밀번호를 다시 입력해주세요.😍");
                        System.out.println("=============================");
                    }
                }
                user.put("비밀번호", password);

                // 생년월일 6자리만 받음.
                String birth = "";
                while (true) {
                    System.out.println("👆birth: ");
                    birth = sc.nextLine();

                    if (birth.length() == 6) {
                        break;
                    } else {
                        System.out.println("=============================");
                        System.out.println("6자리 생년월일을 다시 입력해주세요.😍");
                        System.out.println("=============================");
                    }
                }
                user.put("생년월일", birth);

                // 이메일
                System.out.println("이메일: ");
                String email = sc.nextLine();
                user.put("이메일", email);

                user.put("방문횟수", "0"); // 방문횟수 초기화
                user.put("구매횟수", "0"); // 구매횟수 초기화

                users.add(user);

                System.out.println("=============================");
                System.out.println(user.get("이름") + "님 가입을 환영합니다.");
                System.out.println("ID는 " + user.get("ID") + "입니다.");
                System.out.println("=============================");

                System.out.println("회원가입을 이어서 하시겠습니까?\ny: 진행 n: 취소");
                System.out.print(">>");
                String registerAgain = sc.nextLine();

                if (registerAgain.equalsIgnoreCase("n")) {
                    break;
                }
            }

            // 회원 목록을 출력한 후, 방문 횟수 확인 여부를 묻는 부분
            System.out.println("회원 목록을 출력했습니다. 방문 횟수를 확인하시겠습니까?");
            System.out.println("확인하려는 회원의 아이디, 이름, 이메일, 또는 생년월일을 입력해주세요. (종료: 'exit')");
            String input = sc.nextLine();

            // 'exit'를 입력할 때까지 반복하여 사용자로부터 입력 받음
            while (!input.equalsIgnoreCase("exit")) {
                // 회원 목록에서 아이디나 이름, 이메일, 생년월일을 기준으로 회원 조회
                findUserAndPrintVisitCount(users, input, sc);

                System.out.println("회원 목록을 출력했습니다. 방문 횟수를 확인하시겠습니까?");
                System.out.println("확인하려는 회원의 아이디, 이름, 이메일, 또는 생년월일을 입력해주세요. (종료: 'exit')");
                input = sc.nextLine();
            }
        }
    }

    // 회원을 조회하여 방문 횟수와 구매 횟수를 출력하는 메서드
    private static void findUserAndPrintVisitCount(ArrayList<HashMap<String, String>> users, String searchKey, Scanner sc) {
        boolean found = false;

        for (HashMap<String, String> user : users) {
            // 검색 기준이 아이디, 이름, 이메일, 또는 생년월일 중에 하나와 일치하는 경우에 해당 회원을 출력
            if (user.get("ID").equalsIgnoreCase(searchKey) || user.get("이름").equalsIgnoreCase(searchKey) ||
                    user.get("이메일").equalsIgnoreCase(searchKey) || user.get("생년월일").equalsIgnoreCase(searchKey)) {

                // 회원을 찾은 경우 방문 횟수와 구매 횟수 출력
                System.out.println("회원을 찾았습니다.");
                int visitCount = Integer.parseInt(user.get("방문횟수"));
                int purchaseCount = Integer.parseInt(user.get("구매횟수"));

                System.out.println("방문 횟수: " + visitCount);
                System.out.println("구매 횟수: " + purchaseCount);

                // 등급 출력
                int totalVisits = visitCount + purchaseCount;
                System.out.print("등급: ");
                if (totalVisits <= 3) {
                    System.out.println("BRONZE");
                } else if (totalVisits <= 9) {
                    System.out.println("SILVER");
                } else {
                    System.out.println("GOLD");
                }

                // 구매 횟수를 묻고, 사용자의 대답에 따라 구매 횟수 업데이트
                System.out.println("구매하셨습니까? (Y/N)");
                String purchaseInput = sc.nextLine();
                if (purchaseInput.equalsIgnoreCase("y")) {
                    // 구매 횟수 증가
                    user.put("구매횟수", String.valueOf(purchaseCount + 1));
                }

                // 방문 횟수 증가
                user.put("방문횟수", String.valueOf(visitCount + 1));

                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("회원을 찾을 수 없습니다.");
        }
    }
}
