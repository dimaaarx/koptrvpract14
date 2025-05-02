import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class MiniDiary {
    static String[] dates = new String[50];
    static String[] texts = new String[50];
    static int count = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String dateFormat = "yyyy-MM-dd HH:mm"; // формат за замовчуванням
        boolean run = true;

        System.out.println("Оберіть режим:");
        System.out.println("1. Новий щоденник");
        System.out.println("2. Завантажити існуючий щоденник");
        String mode = sc.nextLine();

        if (mode.equals("2")) {
            System.out.print("Введіть шлях до файлу: ");
            String filePath = sc.nextLine();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line;
                String tempDate = "";
                String tempText = "";
                while ((line = reader.readLine()) != null) {
                    if (line.length() == 0 && tempDate.length() > 0) {
                        dates[count] = tempDate;
                        texts[count] = tempText;
                        count++;
                        tempDate = "";
                        tempText = "";
                    } else if (tempDate.length() == 0) {
                        tempDate = line;
                    } else {
                        tempText += line + "\n";
                    }
                }
                if (tempDate.length() > 0) {
                    dates[count] = tempDate;
                    texts[count] = tempText;
                    count++;
                }
                reader.close();
                System.out.println("Щоденник завантажено.");
            } catch (Exception e) {
                System.out.println("Не вдалося завантажити файл.");
            }
        }

        System.out.print("Введіть бажаний формат дати (наприклад, yyyy-MM-dd HH:mm): ");
        String userFormat = sc.nextLine();
        if (userFormat.length() > 0) {
            dateFormat = userFormat;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

        while (run) {
            System.out.println("\nМІНІ-ЩОДЕННИК");
            System.out.println("1. Додати запис");
            System.out.println("2. Видалити запис");
            System.out.println("3. Показати всі записи");
            System.out.println("4. Вийти");
            System.out.print("Ваш вибір: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                if (count >= 50) {
                    System.out.println("Щоденник заповнений.");
                    continue;
                }

                String date = LocalDateTime.now().format(formatter);

                System.out.println("Введіть текст запису (порожній рядок — завершити):");
                String text = "";
                while (true) {
                    String line = sc.nextLine();
                    if (line.length() == 0) break;
                    text += line + "\n";
                }

                dates[count] = date;
                texts[count] = text;
                count++;
                System.out.println("Запис додано.");

            } else if (choice.equals("2")) {
                System.out.print("Введіть дату для видалення (в точному форматі): ");
                String d = sc.nextLine();
                boolean found = false;

                for (int i = 0; i < count; i++) {
                    if (dates[i].equals(d)) {
                        for (int j = i; j < count - 1; j++) {
                            dates[j] = dates[j + 1];
                            texts[j] = texts[j + 1];
                        }
                        count--;
                        found = true;
                        System.out.println("Запис видалено.");
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Запис не знайдено.");
                }

            } else if (choice.equals("3")) {
                if (count == 0) {
                    System.out.println("Записів немає.");
                } else {
                    for (int i = 0; i < count; i++) {
                        System.out.println("Дата: " + dates[i]);
                        System.out.println("Запис:\n" + texts[i]);
                        System.out.println("-----------");
                    }
                }

            } else if (choice.equals("4")) {
                System.out.print("Бажаєте зберегти щоденник у файл? (так/ні): ");
                String save = sc.nextLine();
                if (save.equalsIgnoreCase("так")) {
                    System.out.print("Введіть шлях до файлу: ");
                    String filePath = sc.nextLine();
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                        for (int i = 0; i < count; i++) {
                            writer.write(dates[i]);
                            writer.newLine();
                            writer.write(texts[i]);
                            writer.newLine();
                        }
                        writer.close();
                        System.out.println("Щоденник збережено.");
                    } catch (Exception e) {
                        System.out.println("Не вдалося зберегти файл.");
                    }
                }
                run = false;
            } else {
                System.out.println("Невірний вибір.");
            }
        }

        sc.close();
    }
}
   }