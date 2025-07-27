package org.example.view;

import org.example.controller.DeveloperController;
import org.example.controller.SkillController;
import org.example.controller.SpecialtyController;
import org.example.model.Developer;
import org.example.model.Skill;
import org.example.model.Specialty;
import org.example.model.Status;
import java.util.Arrays;
import java.util.Scanner;

public class View {

    private final DeveloperController developerController = new DeveloperController();
    private final SpecialtyController specialtyController = new SpecialtyController();
    private final SkillController skillController = new SkillController();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        int input;

        do {
            extracted();
            input = scanner.nextInt();

            if (input == 1) {
                System.out.println("Выйти с приложения");

            } else if (input == 2) {
                System.out.println("Текущая коллекция разработчиков: ");
                System.out.println(developerController.getAllDevelopers());
                System.out.println();

            } else if (input == 3) {
                try {
                    System.out.println("Введите ID разработчика: ");
                    Long id = scanner.nextLong();
                    System.out.println(developerController.getDeveloperById(id));
                } catch (NullPointerException e) {
                    System.out.println("ID не найден");
                }

            } else if (input == 4) {
                scanner.nextLine(); // Очистка буфера

                // Ввод имени
                System.out.print("Имя разработчика: ");
                String firstName = scanner.nextLine();

                // Ввод фамилии
                System.out.print("Фамилия разработчика: ");
                String lastName = scanner.nextLine();

                // Создание объекта
                Developer dev = new Developer();
                dev.setFirstName(firstName);
                dev.setLastName(lastName);
                dev.setStatus(Status.ACTIVE);

                // Вывод списка всех навыков
                System.out.println("\nДоступные навыки:");
                skillController.getAllSkills().forEach(s -> System.out.println("- " + s.getName()));

                // Ввод навыков
                System.out.print("\nВведите названия нужных навыков через запятую: ");
                String[] neededSkills = scanner.nextLine().split(",");
                for (String skillName : neededSkills) {
                    skillController.getAllSkills().stream()
                            .filter(s -> s.getName().equalsIgnoreCase(skillName.trim()))
                            .findFirst()
                            .ifPresent(skill -> dev.getSkills().add(skill));
                }

                // Вывод списка всех специальностей
                System.out.println("\nДоступные специальности:");
                specialtyController.getAllSpecialties().forEach(s -> System.out.println("- " + s.getName()));

                // Ввод специальностей
                System.out.print("\nВведите названия нужных специальностей через запятую: ");
                String[] neededSpecs = scanner.nextLine().split(",");
                for (String specName : neededSpecs) {
                    specialtyController.getAllSpecialties().stream()
                            .filter(s -> s.getName().equalsIgnoreCase(specName.trim()))
                            .findFirst()
                            .ifPresent(spec -> dev.getSpecialties().add(spec));
                }

                // Сохранение разработчика
                developerController.saveDeveloper(dev);
                System.out.println("\nРазработчик успешно добавлен!");

            } else if (input == 5) {
                System.out.println("Введите ID разработчика для измений данных");
                Developer dev = developerController.getDeveloperById(scanner.nextLong());

                scanner.nextLine();

                System.out.println("Введите новое имя разработчика");
                String newFirstName = scanner.nextLine();

                System.out.println("Введите новую фамилию разработчика");
                String newLastName = scanner.nextLine();

                if (!newFirstName.isEmpty()) {
                    dev.setFirstName(newFirstName);
                }

                if (!newLastName.isEmpty()) {
                    dev.setLastName(newLastName);
                }

                developerController.updateDeveloper(dev);
                System.out.println("Разработчик изменен");

            } else if (input == 6) {
                System.out.println("Введите ID разработчика для удаления");
                Long id = scanner.nextLong();
                developerController.deleteDeveloper(id);
                System.out.println("Разработчик удален");

            } else if (input == 7) {
                System.out.println("Введите ID разработчика для установки статуса: ");
                Developer dev = developerController.getDeveloperById(scanner.nextLong());
                if (dev == null) {
                    System.out.println("Разработчик с таким ID не найден");
                    return;
                }

                System.out.println("Выберите статус разработчика: ");
                System.out.println("1 - ACTIVE");
                System.out.println("2 - DELETED");

                int choice = scanner.nextInt();

                if (choice == 1) {
                    dev.setStatus(Status.ACTIVE);
                    System.out.println("Установлен статус разработчику: ACTIVE");
                } else if (choice == 2) {
                    dev.setStatus(Status.DELETED);
                    System.out.println("Установлен статус разработчику: DELETED");
                } else {
                    System.out.println("Ошибка выбора! \nВведите первый или второй вариант");
                    return;
                }
                developerController.updateDeveloper(dev);

            } else if (input == 8) {
                System.out.println("Текущая коллекция навыков: ");
                System.out.println(skillController.getAllSkills());
                System.out.println();

            } else if (input == 9) {
                try {
                    System.out.println("Введите ID навыка: ");
                    Long id = scanner.nextLong();
                    System.out.println(skillController.getSkillById(id));
                } catch (NullPointerException e) {
                    System.out.println("ID не найден");
                }

            } else if (input == 10) {
                scanner.nextLine();

                System.out.println("Введите название навыка: ");
                String name = scanner.nextLine();

                Status status = Status.ACTIVE;

                Skill skill = new Skill();
                skill.setName(name);
                skill.setStatus(status);

                skillController.saveSkill(skill);
                System.out.println("Навык" + " " + skill.getName() + " " + "добавлен");

            } else if (input == 11) {
                System.out.println("Введите ID навыка для изменения данных");
                Skill skill = skillController.getSkillById(scanner.nextLong());

                scanner.nextLine();

                System.out.println("Введите новое название навыка");
                String newName = scanner.nextLine();

                if(!newName.isEmpty()) {
                    skill.setName(newName);
                }

                skillController.updateSkill(skill);
                System.out.println("Навык изменен");

            } else if (input == 12) {
                System.out.println("Введите ID навыка для удаления: ");
                Long id = scanner.nextLong();
                skillController.deleteSkill(id);
                System.out.println("Навык удален");

            } else if (input == 13) {
                System.out.println("Введите ID навыка для установки статуса: ");
                Skill skill = skillController.getSkillById(scanner.nextLong());
                if (skill == null) {
                    System.out.println("Навык с таким ID не найден");
                    return;
                }

                System.out.println("Выберите статус навыка: ");
                System.out.println("1 - ACTIVE");
                System.out.println("2 - DELETED");

                int choice = scanner.nextInt();

                if (choice == 1) {
                    skill.setStatus(Status.ACTIVE);
                    System.out.println("Установлен статус навыку: ACTIVE");
                } else if (choice == 2) {
                    skill.setStatus(Status.DELETED);
                    System.out.println("Установлен статус навыку: DELETED");
                } else {
                    System.out.println("Ошибка выбора! \nВведите первый или второй вариант");
                    return;
                }
                skillController.updateSkill(skill);

            } else if (input == 14) {
                System.out.println("Текущая коллекция специальностей: ");
                System.out.println(specialtyController.getAllSpecialties());
                System.out.println();

            } else if (input == 15) {
                try {
                    System.out.println("Введите ID специальности: ");
                    Long id = scanner.nextLong();
                    System.out.println(specialtyController.getSpecialtyById(id));
                } catch (NullPointerException e) {
                    System.out.println("ID не найден");
                }

            } else if (input == 16) {
                scanner.nextLine();

                System.out.println("Введите название специальности: ");
                String name = scanner.nextLine();

                Status status = Status.ACTIVE;

                Specialty specialty = new Specialty();
                specialty.setName(name);
                specialty.setStatus(status);

                specialtyController.saveSpecialty(specialty);
                System.out.println("Навык" + " " + specialty.getName() + " " + "добавлен");

            } else if (input == 17) {
                System.out.println("Введите ID спецаильность для изменения данных: ");
                Specialty specialty = specialtyController.getSpecialtyById(scanner.nextLong());

                scanner.nextLine();

                System.out.println("Введите новое название специальности");
                String newName = scanner.nextLine();

                if(!newName.isEmpty()) {
                    specialty.setName(newName);
                }
                specialtyController.updateSpecialty(specialty);
                System.out.println("Специальность изменена");

            } else if (input == 18) {
                System.out.println("Введите ID специальности для удаления: ");
                Long id = scanner.nextLong();
                specialtyController.deleteSpecialty(id);
                System.out.println("Специальность удалена");

            } else if (input == 19) {
                System.out.println("Введите ID специальности для установки статуса: ");
                Specialty specialty = specialtyController.getSpecialtyById(scanner.nextLong());
                if (specialty == null) {
                    System.out.println("Специальность с таким ID не найдена");
                    return;
                }

                System.out.println("Выберите статус специальности: ");
                System.out.println("1 - ACTIVE");
                System.out.println("2 - DELETED");

                int choice = scanner.nextInt();

                if (choice == 1) {
                    specialty.setStatus(Status.ACTIVE);
                    System.out.println("Установлен статус специальности: ACTIVE");
                } else if (choice == 2) {
                    specialty.setStatus(Status.DELETED);
                    System.out.println("Установлен статус специальности: DELETED");
                } else {
                    System.out.println("Ошибка выбора! \nВведите первый или второй вариант");
                    return;
                }
                specialtyController.updateSpecialty(specialty);
            }

        } while (input != 1) ;

    }

    private static void extracted() {
        for (String s : Arrays.asList(
                "Меню", "1. Выход", "2. Вывести всех разработчиков",
                "3. Вывести разработчика по ID", "4. Добавить разработчика",
                "5. Изменить разработчика", "6. Удалить разработчика по ID",
                "7. Установить статус разработчику (ACTIVE, DELETED)", "8. Вывести все навыки разработчиков",
                "9. Вывести навыки по ID", "10. Добавить навык", "11. Изменить навык",
                "12. Удалить навык разработчика по ID", "13. Установить статус навыку (ACTIVE, DELETED)",
                "14. Вывести все специальности разработчиков", "15. Вывести специальности по ID",
                "16. Добавить специальность", "17. Изменить специальность",
                "18. Удалить специальность разработчика по ID",
                "19. Установить статус специальности (ACTIVE, DELETED)")) {
            System.out.println(s);
        }
    }
}