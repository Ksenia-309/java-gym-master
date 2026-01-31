package ru.yandex.practicum.gym;

public class CounterOfTrainings {

    private Coach coach;
    private int trainingCount;

    public CounterOfTrainings(int trainingCount, Coach coach) {
        this.trainingCount = trainingCount;
        this.coach = coach;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getTrainingCount() {
        return trainingCount;
    }

    public String getFullName() {
        return coach.getSurname() + " " + coach.getName() + " " + coach.getMiddleName();
    }

    @Override
    public String toString() {
        return getFullName() + ": " + trainingCount;
    }
}
