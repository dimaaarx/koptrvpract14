import java.util.Scanner;
import java.io.*;
import java.text.*;
import java.util.Date;

public class MiniDiary {
    static String[] dates = new String[50];
    static String[] texts = new String[50];
    static int count = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String format = "";
        System.out.println("1. yyyy-MM-dd\n2. dd.MM.yyyy\n3. Власний формат");
        while (true) {
            System.out.print("Оберіть формат дати: ");
            String c = sc.nextLine();
            if (c.equals("1")) { format = "yyyy-MM-dd"; break; }
            if (c.equals("2")) { format = "dd.MM.yyyy"; break; }
            if (c.equals("3")) {
                System.out.print("Введіть формат: ");
                format = sc.nextLine(); break;
            }
        }

        System.out.print("1. Створити новий щоденник\n2. Завантажити з файлу\nВаш вибір: ");
        String op = sc.nextLine();
        if (op.equals("2")) {
            System.out.print("Шлях до файлу: ");
            String path = sc.nextLine();
            try {
                BufferedReader r = new BufferedReader(new FileReader(path));
                while (true) {
                    String d = r.readLine(); if (d == null) break;
                    String t = r.readLine(); if (t == null) break;
                    dates[count] = d;
                    texts[count] = t;
                    count++;
                    r.readLine();
                }
                r.close();
            } catch (IOException e) {
                System.out.println("Помилка завантаження.");
            }
        }

        boolean run = true;
        while (run) {
            System.out.println("\n1. Додати\n2. Видалити\n3. Показати\n4. Вийти");
            System.out.print("Ваш вибір: ");
            String ch = sc.nextLine();
            if (ch.equals("1")) {
                if (count >= 50) continue;
                System.out.print("Введіть дату (" + format + "): ");
                String d = sc.nextLine();
                try { new SimpleDateFormat(format).parse(d); } catch (Exception e) { System.out.println("Невірна дата."); continue; }
                System.out.println("Введіть текст (порожній рядок - кінець):");
                String t = "", l;
                while (!(l = sc.nextLine()).equals("")) t += l + " ";
                dates[count] = d;
                texts[count] = t;
                count++;
            } else if (ch.equals("2")) {
                System.out.print("Дата: ");
                String d = sc.nextLine();
                for (int i = 0; i < count; i++) {
                    if (dates[i].equals(d)) {
                        for (int j = i; j < count - 1; j++) {
                            dates[j] = dates[j + 1];
                            texts[j] = texts[j + 1];
                        }
                        count--;
                        break;
                    }
                }
            } else if (ch.equals("3")) {
                for (int i = 0; i < count; i++) {
                    System.out.println(dates[i] + ": " + texts[i]);
                }
            } else if (ch.equals("4")) {
                System.out.print("Зберегти у файл? (так/ні): ");
                if (sc.nextLine().equals("так")) {
                    System.out.print("Файл: ");
                    try {
                        BufferedWriter w = new BufferedWriter(new FileWriter(sc.nextLine()));
                        for (int i = 0; i < count; i++) {
                            w.write(dates[i]); w.newLine();
                            w.write(texts[i]); w.newLine();
                            w.newLine();
                        }
                        w.close();
                    } catch (IOException e) {
                        System.out.println("Помилка збереження.");
                    }
                }
                run = false;
            }
        }

        sc.close();
    }
}
