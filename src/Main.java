import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            RepairWorkDatabase database = new RepairWorkDatabase("repair_works.dat");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Выберите действие:");
                System.out.println("1. Добавить ремонтную работу");
                System.out.println("2. Вывести все ремонтные работы");
                System.out.println("3. Поиск по фирме");
                System.out.println("4. Поиск по виду работ");
                System.out.println("5. Поиск по дате исполнения");
                System.out.println("6. Удалить ремонтную работу");
                System.out.println("0. Выход");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addRepairWork(database, scanner);
                        break;
                    case 2:
                        listAllRepairWorks(database);
                        break;
                    case 3:
                        searchByFirm(database, scanner);
                        break;
                    case 4:
                        searchByWorkType(database, scanner);
                        break;
                    case 5:
                        searchByExecutionDate(database, scanner);
                        break;
                    case 6:
                        deleteRepairWork(database, scanner);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Неправильный выбор. Повторите попытку.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addRepairWork(RepairWorkDatabase database, Scanner scanner) {
        try {
            System.out.print("Фирма: ");
            String firm = scanner.next();
            System.out.print("Вид работ: ");
            String workType = scanner.next();
            System.out.print("Единица измерения: ");
            String unit = scanner.next();
            System.out.print("Стоимость за единицу: ");
            String costPerUnit = String.valueOf(scanner.nextDouble());
            System.out.print("Дата исполнения (yyyy-MM-dd): ");
            Date executionDate = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.next());
            System.out.print("Объем: ");
            double volume = scanner.nextDouble();

            RepairWork repairWork = new RepairWork(firm, workType, unit, costPerUnit, executionDate, volume);
            database.addRepairWork(repairWork);

            System.out.println("Ремонтная работа добавлена.");
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void listAllRepairWorks(RepairWorkDatabase database) {
        try {
            List<RepairWork> repairWorks = database.getAllRepairWorks();

            if (repairWorks.isEmpty()) {
                System.out.println("Список ремонтных работ пуст.");
            } else {
                System.out.println("Список ремонтных работ:");
                for (int i = 0; i < repairWorks.size(); i++) {
                    System.out.println(i + ". " + repairWorks.get(i));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void searchByFirm(RepairWorkDatabase database, Scanner scanner) {
        System.out.print("Введите фирму для поиска: ");
        String firm = scanner.next();
        List<RepairWork> repairWorks = database.getRepairWorksByFirm(firm);

        if (repairWorks.isEmpty()) {
            System.out.println("Ремонтных работ по фирме '" + firm + "' не найдено.");
        } else {
            System.out.println("Ремонтные работы по фирме '" + firm + "':");
            for (int i = 0; i < repairWorks.size(); i++) {
                System.out.println(i + ". " + repairWorks.get(i));
            }
        }
    }

    private static void searchByWorkType(RepairWorkDatabase database, Scanner scanner) {
        System.out.print("Введите вид работ для поиска: ");
        String workType = scanner.next();
        List<RepairWork> repairWorks = database.getRepairWorksByWorkType(workType);

        if (repairWorks.isEmpty()) {
            System.out.println("Ремонтных работ по виду работ '" + workType + "' не найдено.");
        } else {
            System.out.println("Ремонтные работы по виду работ '" + workType + "':");
            for (int i = 0; i < repairWorks.size(); i++) {
                System.out.println(i + ". " + repairWorks.get(i));
            }
        }
    }

    private static void searchByExecutionDate(RepairWorkDatabase database, Scanner scanner) {
        try {
            System.out.print("Введите дату исполнения (yyyy-MM-dd) для поиска: ");
            Date executionDate = new SimpleDateFormat("yyyy-MM-dd").parse(scanner.next());
            List<RepairWork> repairWorks = database.getRepairWorksByExecutionDate(executionDate);

            if (repairWorks.isEmpty()) {
                System.out.println("Ремонтных работ по дате исполнения '" + executionDate + "' не найдено.");
            } else {
                System.out.println("Ремонтные работы по дате исполнения '" + executionDate + "':");
                for (int i = 0; i < repairWorks.size(); i++) {
                    System.out.println(i + ". " + repairWorks.get(i));
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void deleteRepairWork(RepairWorkDatabase database, Scanner scanner) {
        System.out.print("Введите индекс ремонтной работы для удаления: ");
        int index = scanner.nextInt();

        try {
            database.removeRepairWork(index);
            System.out.println("Ремонтная работа с индексом " + index + " удалена.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
