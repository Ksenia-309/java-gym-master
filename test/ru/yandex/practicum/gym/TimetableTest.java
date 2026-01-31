package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    private Timetable timetable;

    @BeforeEach
    void setUp() {
        Timetable.clearTimetable();
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForMonday =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, trainingSessionsForMonday.keySet().size());

        List<TrainingSession> trainingSessions = trainingSessionsForMonday.get(new TimeOfDay(13, 0));
        Assertions.assertEquals(1, trainingSessions.size());


        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForTuesday =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, trainingSessionsForTuesday.keySet().size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForMonday =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, trainingSessionsForMonday.keySet().size());

        List<TrainingSession> trainingSessions = trainingSessionsForMonday.get(new TimeOfDay(13, 0));
        Assertions.assertEquals(1, trainingSessions.size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForThursday =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, trainingSessionsForThursday.keySet().size());

        List<TimeOfDay> keys = new ArrayList<>(trainingSessionsForThursday.keySet());
        Assertions.assertEquals(new TimeOfDay(13, 0), keys.get(0));
        Assertions.assertEquals(new TimeOfDay(20, 0), keys.get(1));

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForTuesday =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(0, trainingSessionsForTuesday.keySet().size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(12, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 12:00 вернулось одно занятие
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForMonday =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertTrue(trainingSessionsForMonday.containsKey(new TimeOfDay(12, 0)));

        List<TrainingSession> sessionsOnMonday = trainingSessionsForMonday.get(new TimeOfDay(12, 0));
        Assertions.assertEquals(1, sessionsOnMonday.size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        TreeMap<TimeOfDay, List<TrainingSession>> trainingSessionsForMondayAnotherTime =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        Assertions.assertFalse(trainingSessionsForMondayAnotherTime.containsKey(new TimeOfDay(14, 0)));
    }

    @Test
    void testGetCountByCoaches() {

        Coach coach = new Coach("Иванов", "Иван", "Иванович");

        Group groupAdult = new Group("Гимнастика для взрослых", Age.ADULT, 60);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(18, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Гимнастика для детей", Age.CHILD, 45);
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(thursdayChildTrainingSession);

        Coach coachTwo = new Coach("Петров", "Никита", "Вадимович");

        Group groupChildTwo = new Group("Акробатика для детей", Age.CHILD, 30);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChildTwo, coachTwo,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession wednesdayChildTrainingSession = new TrainingSession(groupChildTwo, coachTwo,
                DayOfWeek.WEDNESDAY, new TimeOfDay(13, 0));
        TrainingSession fridayChildTrainingSession = new TrainingSession(groupChildTwo, coachTwo,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(wednesdayChildTrainingSession);
        timetable.addNewTrainingSession(fridayChildTrainingSession);

        //Проверить, что занятия для каждого тренера считаются правильно
        List<CounterOfTrainings> result = timetable.getCountByCoaches();
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(3, result.get(0).getTrainingCount()); //кол-во занятий Петрова = 3
        Assertions.assertEquals(2, result.get(1).getTrainingCount());//кол-во занятий Иванова = 2

        //Проверить, что список тренеров правильно сортируется по убыванию
        Assertions.assertEquals(3, result.get(0).getTrainingCount()); //должен быть Петров с 3 занятиями
        Assertions.assertEquals(2, result.get(1).getTrainingCount());//должен быть Иванов с 2

        //Проверить тренера в списке
        CounterOfTrainings findIvanov = findCoach(result, coach);
        CounterOfTrainings findPetrov = findCoach(result, coachTwo);

        Assertions.assertNotNull(findIvanov);
        Assertions.assertNotNull(findPetrov);
    }

    private CounterOfTrainings findCoach(List<CounterOfTrainings> list, Coach coach) {
        for (CounterOfTrainings findCoach : list) {
            if (findCoach.getCoach().equals(coach)) {
                return findCoach;
            }
        }
        return null;
    }

}
