package main.java;

public class Sandbox {

    public static void main(String[] args) {

        String test = "!fortniteStats Jeezy_x (pc) [solo]";
        String username = test.substring(test.indexOf("!fortniteStats") + 15);
        if (username.contains("(")) {
            username = username.substring(0, username.indexOf("(") - 1);
        }
        String platform = "";
        try {
            platform = test.substring(test.indexOf("(") + 1, test.indexOf(")"));
        } catch (IndexOutOfBoundsException e) {

        }
        String mode = "";
        try {
            mode = test.substring(test.indexOf("[") + 1,test.indexOf("]"));
        } catch (IndexOutOfBoundsException e) {

        }
        System.out.println(username + " " + platform + " " + mode);
    }
}
