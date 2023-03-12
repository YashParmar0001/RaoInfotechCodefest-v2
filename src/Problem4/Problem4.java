package Problem4;

import java.io.*;
import java.util.*;

public class Problem4 {
    public static void main(String[] args) {
        File inputSmall = new File("D:\\raoinfotech\\src\\Problem4\\TMW_small.txt");
        File outputSmall = new File("D:\\raoinfotech\\src\\Problem4\\P4OutputSmall.txt");

        File inputLarge = new File("D:\\raoinfotech\\src\\Problem4\\TMW_large.txt");
        File outputLarge = new File("D:\\raoinfotech\\src\\Problem4\\P4OutputLarge.txt");

        mathWizard(inputSmall, outputSmall);
        mathWizard(inputLarge, outputLarge);
    }

    static void mathWizard(File inputFile, File outputFile) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String line = br.readLine();
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
            PrintWriter pw = new PrintWriter(bw);

            int noOfTestCase = Integer.parseInt(line);

            for (int i = 0; i < noOfTestCase; i++) {
                line = br.readLine();
                boolean ans = getNumberString(line);
                pw.printf("Case #%d: %b\n", (i+1), ans);
            }
            pw.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean getNumberString(String numberString) {
        HashMap<String, Double> numberMap = new HashMap<>();
        numberMap.put("one", 1D);
        numberMap.put("two", 2D);
        numberMap.put("three", 3D);
        numberMap.put("four", 4D);
        numberMap.put("five", 5D);
        numberMap.put("six", 6D);
        numberMap.put("seven", 7D);
        numberMap.put("eight", 8D);
        numberMap.put("nine", 9D);
        numberMap.put("ten", 10D);
        numberMap.put("eleven", 11D);
        numberMap.put("twelve", 12D);
        numberMap.put("thirteen", 13D);
        numberMap.put("fourteen", 14D);
        numberMap.put("fifteen", 15D);
        numberMap.put("sixteen", 16D);
        numberMap.put("seventeen", 17D);
        numberMap.put("eighteen", 18D);
        numberMap.put("nineteen", 19D);
        numberMap.put("twenty", 20D);
        numberMap.put("thirty", 30D);
        numberMap.put("forty", 40D);
        numberMap.put("fifty", 50D);
        numberMap.put("sixty", 60D);
        numberMap.put("seventy", 70D);
        numberMap.put("eighty", 80D);
        numberMap.put("ninety", 90D);
        numberMap.put("hundred", 100D);

        HashMap<String, String> signMap = new HashMap<>();
        signMap.put("plus", "+");
        signMap.put("and", "+");
        signMap.put("substract", "-");
        signMap.put("division", "/");
        signMap.put("multiple", "*");
//        signMap.put("equals", "=");
        signMap.put("+", "+");
        signMap.put("-", "-");
        signMap.put("/", "/");
        signMap.put("*", "*");

        String[] op = numberString.split(" ");

        StringBuilder sb = new StringBuilder();
        StringBuilder expression = new StringBuilder();

        for (String n: op) {
            if (n.equals("and")) {
            } else if (signMap.containsKey(n)) {
                expression.append(getNumber(sb.toString())).append(" ");
                expression.append(signMap.get(n)).append(" ");
                sb = new StringBuilder();
            }else if (n.equals("equals") || n.equals("=")) {
                expression.append(getNumber(sb.toString()));
                break;
            } else {
                if (isNumeric(n)) {
                    sb.append(n).append(" ");
                }else {
                    sb.append(numberMap.get(n)).append(" ");
                }
            }
        }

        double ans = Double.parseDouble(op[op.length-1]);
        double countAns = evaluate(expression.toString());

        return ans == countAns;
    }

    static double evaluate(String expression) {
        String[] exp = expression.split(" ");
        Stack<String> stack = new Stack<>();

        // For multiplication and division
        for (String e: exp) {
            if (!isSign(e)) {
                if (stack.isEmpty()) {
                    stack.push(e);
                }else {
                    String op = stack.peek(); // *
                    if (op.equals("/")) {
                        stack.pop();
                        Double a1 = Double.parseDouble(stack.pop());
                        Double a2 = Double.parseDouble(e);
                        stack.push("" + (a1 / a2));
                    }else if(op.equals("*")) {
                        stack.pop();
                        Double a1 = Double.parseDouble(stack.pop());
                        Double a2 = Double.parseDouble(e);
                        stack.push("" + (a1 * a2));
                    }else {
                        stack.push(e);
                    }
                }
            }else {
                stack.push(e);
            }
        }
        Stack<String> exp1 = new Stack<>();
        while (!stack.isEmpty()) {
            exp1.push(stack.pop());
        }

        StringBuilder finalExp = new StringBuilder();
        while (!exp1.isEmpty()) {
            finalExp.append(exp1.pop()).append(" ");
        }

        // For addition and subtraction
        String[] ex = finalExp.toString().split(" ");

        for (String e: ex) {
            if (exp1.isEmpty()) {
                exp1.push(e);
            }else {
                if (isSign(e)) {
                    exp1.push(e);
                }else {
                    Double a2 = Double.parseDouble(e);
                    String sign = exp1.pop();
                    Double a1 = Double.parseDouble(exp1.pop());
                    if (sign.equals("+")) {
                        exp1.push("" + (a1+a2));
                    }else {
                        exp1.push("" + (a1-a2));
                    }
                }
            }
        }

        return Double.parseDouble(exp1.pop());
    }

    static boolean isSign(String s) {
        return (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/"));
    }

    static double getNumber(String s) {
        double num = 0;
        String[] nums = s.split(" ");
        for (String n: nums) {
            double d = Double.parseDouble(n);
            if (d == 100.0) {
                if (num == 0) {
                    num = 100;
                }else {
                    num = num * 100;
                }
            }else {
                num += d;
            }
        }
        return num;
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}