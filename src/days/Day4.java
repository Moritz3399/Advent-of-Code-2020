package days;

import util.InputReader;

import java.util.ArrayList;
import java.util.Arrays;

public class Day4 {

    private Day4(String[] input) {
        System.out.printf("Part A: %s passports are valid by existing keys. \n", countValidPassportsByKeys(extractPassportsFromBatch(input)));
        System.out.printf("Part B: %s passports are valid by existing values. \n", countValidPassportsByValues(extractPassportsFromBatch(input)));
    }

    private String[] extractPassportsFromBatch(String[] batch) {
        ArrayList<String> list = new ArrayList<>();
        StringBuilder passport = new StringBuilder();
        for (String line : batch) {
            if ("".equals(line)) {
                list.add(passport.toString().trim());
                passport = new StringBuilder();
            } else {
                passport.append(" ").append(line.trim());
            }
        }
        if (!passport.toString().equals("")) list.add(passport.toString().trim());
        return list.toArray(new String[0]);
    }

    private int countValidPassportsByKeys(String[] passports) {
        int cnt = 0;
        for (String passport : passports) if (validatePassportKeys(passport)) cnt++;
        return cnt;
    }

    private int countValidPassportsByValues(String[] passports) {
        int cnt = 0;
        for (String passport : passports) if (validatePassportKeys(passport) && validatePassportValues(passport)) cnt++;
        return cnt;
    }

    private boolean validatePassportKeys(String passport) {
        // Does not validate, if there are duplicate keys!
        int cnt = 0;
        String[] fields = passport.split(" ");
        for (String field : fields) {
            switch (field.substring(0, 3)) {
                case "byr":
                case "iyr":
                case "eyr":
                case "hgt":
                case "hcl":
                case "ecl":
                case "pid":
                    cnt++;
                    break;
            }
        }
        return cnt == 7;
    }

    private boolean validatePassportValues(String passport) {
        String[] fields = passport.split(" ");
        for (String field : fields) {
            switch (field.substring(0, 3)) {
                case "byr":
                    if (!validateByr(field.substring(4))) return false;
                    break;
                case "iyr":
                    if (!validateIyr(field.substring(4))) return false;
                    break;
                case "eyr":
                    if (!validateEyr(field.substring(4))) return false;
                    break;
                case "hgt":
                    if (!validateHgt(field.substring(4))) return false;
                    break;
                case "hcl":
                    if (!validateHcl(field.substring(4))) return false;
                    break;
                case "ecl":
                    if (!validateEcl(field.substring(4))) return false;
                    break;
                case "pid":
                    if (!validatePid(field.substring(4))) return false;
                    break;
            }
        }
        return true;
    }

    private boolean validateByr(String byr) {
        return validateYear(byr, 1920, 2002);
    }

    private boolean validateIyr(String iyr) {
        return validateYear(iyr, 2010, 2020);
    }

    private boolean validateEyr(String eyr) {
        return validateYear(eyr, 2020, 2030);
    }

    private boolean validateHgt(String hgt) {
        return (hgt.length() == 5
                && hgt.matches("1[5-9][0-9]cm")
                && Integer.parseInt(hgt.substring(0, 3)) < 194)
                ^
                (hgt.length() == 4
                        && hgt.matches("[5-7][0-9]in")
                        && Integer.parseInt(hgt.substring(0, 2)) < 77
                        && Integer.parseInt(hgt.substring(0, 2)) > 58);
    }

    private boolean validateHcl(String hcl) {
        return hcl.matches("#[a-f0-9]{6}");
    }

    private boolean validateEcl(String ecl) {
        return Arrays.asList(new String[]{"amb", "blu", "brn", "gry", "grn", "hzl", "oth"}).contains(ecl);
    }

    private boolean validatePid(String pid) {
        return pid.matches("[0-9]{9}");
    }

    private boolean validateYear(String yearStr, int min, int max) {
        if (!yearStr.matches("[0-9]{4}")) return false; // could have leading zeros
        try {
            int year = Integer.parseInt(yearStr);
            return (min <= year && year <= max);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("Day 4:");
        String[] input = InputReader.getLinesOfFile("input\\Day4.txt");
//        String[] input = InputReader.getLinesOfFile("input\\Day4.example.a.txt");
//        String[] input = InputReader.getLinesOfFile("input\\Day4.example.b.txt");
        new Day4(input);
//        Expected output for my input:
//        Part A: 247 passports are valid by existing keys.
//        Part B: 145 passports are valid by existing values.
    }
}
