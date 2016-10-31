package iss.ci.ga.model;

import iss.ci.ga.entity.Schedule;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Population for GA model
 */
public class Population {
    // Holds population of schedules
    ArrayList<Schedule> schedules;
    private int resource;

    // Construct a population
    public Population(int populationSize, int resource) {
        schedules = new ArrayList<Schedule>();
        for (int i = 0; i < populationSize; i++) {
            schedules.add(null);
        }
        this.resource = resource;
    }

    //Initialize the list of schedule
    public void initialize() {
        for (int i = 0; i < size(); i++) {
            Schedule schedule = new Schedule(resource);
            schedule.generateIndividual();
            saveSchedule(i, schedule);
        }
    }

    // Saves a schedule
    public void saveSchedule(int index, Schedule schedule) {
        schedules.set(index, schedule);
    }

    // Gets a schedule from population
    public Schedule getSchedule(int index) {
        return schedules.get(index);
    }

    // Gets the best schedule in the population
    public Schedule getFittest() {
        return schedules.stream()
                        .min((p1, p2) -> Integer.compare(p1.getFitness(), p2.getFitness()))
                        .get();
    }

    // Gets population size
    public int size() {
        return schedules.size();
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }
}
