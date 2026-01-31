package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private static Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();

            int cmd = scanner.nextInt();
            switch (cmd) {
                case 1:
                    scanner.nextLine();

                    System.out.println("Введите название тренировки");
                    String training = scanner.nextLine();

                    Age age;
                    while (true) {
                        System.out.print("Введите тип тренировки (CHILD/ADULT): ");
                        String ageInput = scanner.nextLine().toUpperCase();
                        try {
                            age = Age.valueOf(ageInput);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка! Введите ADULT или CHILD");
                        }
                    }

                    System.out.println("Введите продолжительность тренировки");
                    int duration = scanner.nextInt();
                    scanner.nextLine();

                    Group group = new Group(training, age, duration);

                    System.out.println("Введите фамилию тренера");
                    String surname = scanner.nextLine();

                    System.out.println("Введите имя тренера");
                    String name = scanner.nextLine();

                    System.out.println("Введите отчество тренера");
                    String middleName = scanner.nextLine();

                    Coach coach = new Coach(surname, name, middleName);

                    DayOfWeek dayOfWeek;
                    while (true) {
                        System.out.print("Введите день тренировки");
                        System.out.println("MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY");
                        String dayOfWeekInput = scanner.nextLine().toUpperCase();
                        try {
                            dayOfWeek = DayOfWeek.valueOf(dayOfWeekInput);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка! Введите день недели по образцу");
                        }
                    }

                    System.out.println("Введите время начала тренировки (часы): ");
                    int startHour = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Введите время начала тренировки (минуты): ");
                    int startMin = scanner.nextInt();
                    scanner.nextLine();

                    TimeOfDay timeOfDay = new TimeOfDay(startHour, startMin);

                    addNewTrainingSession(new TrainingSession(group, coach, dayOfWeek, timeOfDay));
                    System.out.println("Тренировка успешно добавлена!");
                    break;
                case 2:
                    scanner.nextLine();

                    DayOfWeek dayOfWeek1;
                    while (true) {
                        System.out.println("На какой день недели Вы хотели бы увидеть расписание?");
                        System.out.println("MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY");

                        String dayOfWeek1Input = scanner.nextLine().trim().toUpperCase();
                        try {
                            dayOfWeek1 = DayOfWeek.valueOf(dayOfWeek1Input);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка! Введите день недели по образцу");
                        }
                    }
                    getTrainingSessionsForDay(dayOfWeek1);
                    break;
                case 3:
                    scanner.nextLine();

                    DayOfWeek dayOfWeek2;
                    while (true) {
                        System.out.println("На какой день недели Вы хотели бы увидеть расписание?");
                        System.out.println("MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY");

                        String dayOfWeek2Input = scanner.nextLine().trim().toUpperCase();
                        try {
                            dayOfWeek2 = DayOfWeek.valueOf(dayOfWeek2Input);
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка! Введите день недели по образцу");
                        }
                    }

                    System.out.println("Какое время Вас интересует?");
                    System.out.println("Введите время начала тренировки (часы): ");
                    int startHour1 = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Введите время начала тренировки (минуты): ");
                    int startMin1 = scanner.nextInt();
                    scanner.nextLine();

                    TimeOfDay timeOfDay1 = new TimeOfDay(startHour1, startMin1);
                    getTrainingSessionsForDayAndTime(dayOfWeek2, timeOfDay1);
                    break;
                case 4:
                    getCountByCoaches();
                    break;
                case 0:
                    System.out.println("Всего доброго!");
                    return;
                default:
                    System.out.println("Извините, такую команду мы еше не придумали.");
                    System.out.println();
                    break;


            }

        }
    }

    static void printMenu() {

        System.out.println("Добрый день! Как я могу Вам помочь?");
        System.out.println("1 - Добавить новое занятие в расписании.");
        System.out.println("2 - Показать расписание тренировок на определенный день.");
        System.out.println("3 - Показать расписание тренировок на определенный день и час.");
        System.out.println("4 - Показать количество тренировок за неделю у каждого тренера.");
        System.out.println("0 - Выйти");

        System.out.println("~".repeat(90));

    }

     static void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> dayTrainings = timetable.get(dayOfWeek);
        if (Objects.isNull(dayTrainings)) {
            dayTrainings = new TreeMap<>();
            timetable.put(dayOfWeek, dayTrainings);
        }

        List<TrainingSession> trainingSessions = dayTrainings.get(timeOfDay);
        if (Objects.isNull(trainingSessions)) {
            trainingSessions = new ArrayList<>();
            dayTrainings.put(timeOfDay, trainingSessions);
        }

        trainingSessions.add(trainingSession);

    }

     static TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (!timetable.containsKey(dayOfWeek)) {
            System.out.println("На выбранный день тренировок нет. ");
            return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
        } else {
            System.out.println(timetable.get(dayOfWeek));
            return timetable.get(dayOfWeek);
        }
    }

     static List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {

        Map<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        if (daySchedule == null) {
            System.out.println("На выбранный день тренировок нет. ");
            return Collections.emptyList();
        }

        List<TrainingSession> timeSchedule = daySchedule.get(timeOfDay);
        if (timeSchedule == null) {
            System.out.println("На выбранное время тренировок нет. ");
            return Collections.emptyList();
        }
        System.out.println(timeSchedule);
        return timeSchedule;
    }

     static List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachTrainingsCount = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> daySchedule : timetable.values()) {
            for (List<TrainingSession> sessions : daySchedule.values()) {
                if (sessions != null) {
                    for (TrainingSession session : sessions) {
                        Coach coach = session.getCoach();
                        coachTrainingsCount.put(coach, coachTrainingsCount.getOrDefault(coach, 0) + 1);

                    }
                }
            }
        }
        List<CounterOfTrainings> sum = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachTrainingsCount.entrySet()) {
            CounterOfTrainings counterOfTrainings = new CounterOfTrainings(entry.getValue(), entry.getKey());
            sum.add(counterOfTrainings);
        }
        sum.sort((a, b) -> b.getTrainingCount() - a.getTrainingCount());
        System.out.println(sum);
        return sum;

    }
    public static void clearTimetable() {
        timetable.clear();
    }

}



