import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Calendar;

public class Algo {
    private String name;
    private String surname;
    private String patronymic;
    private int dayOfBirth;
    private int monthOfBirth;
    private int yearOfBirth;
    private int sex; // 0 - ж, 1 - м, 2 - не опр.
    private int age;


    public Algo(String name_, String dateOfBirth_) {
        setName(name_);
        setDate(dateOfBirth_);
        setAge();
        setSex();

        System.out.print("Пол: ");
        if (sex == 0) {
            System.out.println("женский");
        }
        else {
            if (sex == 1) {
                System.out.println("мужской");
            }
            else {
                System.out.println("не определен");
            }
        }
        System.out.print("Возраст: ");
        System.out.print(age);
        if (age % 10 == 1 && age % 100 != 11) {
            System.out.println(" год");
        } else if (age % 10 >= 2 && age % 10 <= 4 && (age % 100 < 10 || age % 100 > 20)) {
            System.out.println(" года");
        } else {
            System.out.println(" лет");
        }

        System.out.print("Сокращённое имя: ");
        System.out.println(shortName());

    }

    private void setName(String name_) {
        StringTokenizer tokensName = new StringTokenizer(name_, " ");
        if (tokensName.countTokens() != 3) {
            throw new IllegalArgumentException("Имя должно состоять из трёх слов (имя, фамилия, отчество)");
        }
        if (name_.matches("[a-zA-Zа-яА-Я]+")) {
            throw new IllegalArgumentException("Имя может содержать только буквы");
        }
        String n = "";
        String s = "";
        String p = "";
        while (tokensName.hasMoreTokens()) {
            if (s.length() == 0) {
                s = s + (tokensName.nextToken());
                s = s.substring(0, 1).toUpperCase() + s.substring(1); // приводим в нормальный вид, первая буква заглавная, остальные - строчные
                continue;
            }
            if (n.length() == 0) {
                n = n + (tokensName.nextToken());
                n = n.substring(0, 1).toUpperCase() + n.substring(1);
                continue;
            }
            if (p.length() == 0) {
                p = p + (tokensName.nextToken());
                p = p.substring(0, 1).toUpperCase() + p.substring(1);
            }
        }
        name = n;
        surname = s;
        patronymic = p;
    }

    private void setSex() {
        if (patronymic.endsWith("а")) {
            sex = 0;
        }
        else {
            if (patronymic.endsWith("ч") || patronymic.endsWith("в")) {
                sex = 1;
            }
            else {
                sex = 2; // не определили пол
            }
        }
    }

    private String shortName() {
        String nameInitial = name.charAt(0) + ".";
        String patronymicInitial = patronymic.charAt(0) + ".";
        String answer = surname + " " + nameInitial + patronymicInitial;
        return answer;
    }

    private boolean checkLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private void setDate(String date_) {
        StringTokenizer tokensDate = new StringTokenizer(date_, "./ ");
        if (tokensDate.countTokens() != 3) {
            throw new IllegalArgumentException("Дата рождения должна состоять из дня, месяца и года");
        }

        int day = 0;
        int month = 0;
        int year = 0;

        try {
            while (tokensDate.hasMoreTokens()) {
                if (day == 0) {
                    day = Integer.parseInt(tokensDate.nextToken());
                    continue;
                }
                if (month == 0) {
                    month = Integer.parseInt(tokensDate.nextToken());
                    continue;
                }
                if (year == 0) {
                    year = Integer.parseInt(tokensDate.nextToken());
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Дата должна содержать только числа, разделенные точкой");
        }

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Неверная дата");
        }
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (day > 31 || day < 1) {
                throw new IllegalArgumentException("Неверная дата");
            }
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30 || day < 1) {
                throw new IllegalArgumentException("Неверная дата");
            }
        }

        if (year < 1) {
            throw new IllegalArgumentException("Неверная дата");
        }

        if (month == 2) {
            if (checkLeapYear(year)) {
                if (day > 29 || day < 1) {
                    throw new IllegalArgumentException("Неверная дата");
                }
            } else {
                if (day > 28 || day < 1) {
                    throw new IllegalArgumentException("Неверная дата");
                }
            }
        }

        dayOfBirth = day;
        monthOfBirth = month;
        yearOfBirth = year;

    }

    private void setAge() {
        Calendar today = Calendar.getInstance();
        int currentYear = today.get(Calendar.YEAR);
        int currentMonth = today.get(Calendar.MONTH);
        int currentDay = today.get(Calendar.DAY_OF_MONTH);

        if (yearOfBirth > currentYear ||
                (yearOfBirth == currentYear && monthOfBirth > currentMonth) ||
                (yearOfBirth == currentYear && monthOfBirth == currentMonth && dayOfBirth > currentDay)) {
            throw new IllegalArgumentException("Дата не может быть в будущем");
        }

        int age_ = currentYear - yearOfBirth;
        if (currentMonth < monthOfBirth || (currentMonth == monthOfBirth && currentDay < dayOfBirth)) {
            age_--;
        }

        age = age_;
    }
}
