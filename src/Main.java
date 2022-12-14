

import java.beans.Introspector;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {

    public static boolean areRimNums;

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        System.out.println("Введите выражение (a + b, a - b, a * b, a / b)");
        String query = in.nextLine();

        System.out.println(calc(query));

    }


    public static String calc(String input) throws Exception {
        String[] querySplit;
        int result = 0;

        String operator  = findOperator(input);

        querySplit = input.split(operator,2);
        if(querySplit[1].trim().matches("(.*)\\W(.*)+"))
            throw new Exception("Формат математической операции не удовлетворяет условиям");
        int[] operands = convert(querySplit[0].trim(), querySplit[1].trim());
        switch (operator) {
            case ("\\+"):
                result = operands[0] + operands[1];
                break;
            case ("-"):
                result = operands[0] - operands[1];
                break;
            case ("\\*"):
                result = operands[0] * operands[1];
                break;
            case ("/"):
                result = operands[0] / operands[1];
                break;
        }
        if(areRimNums) {
            if (result < 1) {
                throw new Exception("В римской системе нет отрицательных чисел");
            }
            return  convertToRim(result);
        }
        return Integer.toString(result);
    }

    public static void checkErrors (int num) throws Exception {
        if (num < 1 || num > 10){
            throw new Exception("Число должно быть от 1 до 10");
        }
    }

    public static int[] convert(String a, String b) throws Exception {
        boolean containRimNum = false;

        int returnA, returnB;

        String[] rightRims = {"I","V","X"} ;
        String[] wrongRims = {"L","C","D","M"};

        for (String item:wrongRims){
            if(a.contains(item) || b.contains(item)){
                throw new Exception("Число должно быть от I до X");
            }
        }

        for (String item:rightRims){
            for (String item2:rightRims){
                if(a.contains(item)){
                    returnA = convertToArab(a);
                    checkErrors(returnA);
                    if (b.contains(item2)){
                        returnB = convertToArab(b);
                        checkErrors(returnB);
                        return new int[]{returnA,returnB};
                    }
                }
            }
            if (isNumeric(a) ^ isNumeric(b)) {
                throw new Exception("Используются одновременно разные системы счисления");
            }
        }
        try {
            returnA = Integer.parseInt(a);
            returnB = Integer.parseInt(b);
        }
        catch (Exception ex)
        {
            throw new Exception("Необходимо использовать целые числа");
        }
        return new int[]{returnA,returnB};
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static String findOperator (String query) {
        for (int i = 0; i < query.length(); i++) {
            String operator = Character.toString(query.charAt(i));
            switch (operator) {
                case ("+"):
                    return "\\+";
                case ("-"):
                    return "-";
                case ("*"):
                    return "\\*";
                case ("/"):
                    return "/";
            }

        }
        return null;

    }

    public static String convertToRim (int numToConvert){
        String [][] rimNums = {{"I", "1"},{"IV","4"},{"V","5"},{"IX","9"},{"X","10"},{"XL","40"},{"L","50"},{"XC","90"},{"C","100"}};
        String result = "";
        for(int i = 0; i <= numToConvert; i++){
            for(int y = 8; y <= rimNums.length ; y--){
                if(y<0)
                    break;
                int num = Integer.parseInt(rimNums[y][1]);
                String rim = rimNums[y][0];
                if ((numToConvert-num)>=0){
                    numToConvert = numToConvert-num;
                    result += rim;
                    y++;
                }

            }
        }
        return result;
    }

    public static int convertToArab (String stringToConvert){
        String [][] rimNums = {{"I", "1"},{"IV","4"},{"V","5"},{"IX","9"},{"X","10"},{"XL","40"},{"L","50"},{"XC","90"},{"C","100"}};
        int result = 0;
        for(int y = 8; y <= rimNums.length ; y--){
            if(y<0)
                break;
            int num = Integer.parseInt(rimNums[y][1]);
            String rim = rimNums[y][0];
            if (stringToConvert.contains(rim)){
                stringToConvert = stringToConvert.replaceFirst(rim, "");
                result += num;
                y++;
            }
            else if (stringToConvert =="")
                break;

        }
        areRimNums = true;
        return result;
    }

}
