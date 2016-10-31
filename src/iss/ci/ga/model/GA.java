package iss.ci.ga.model;

/*
* GA.java
* Manages algorithms for evolving population
*/

import iss.ci.ga.GASchedule;
import iss.ci.ga.entity.Project;
import iss.ci.ga.entity.Schedule;

public class GA {

    /* GA parameters */
    private static final double mutationRate = 0.015;
    private static final int crossoverSize = 5;
    private static final boolean elitism = true;


    // Evolves a population over one generation
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), GASchedule.RESOURCE);

        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.saveSchedule(0, pop.getFittest());
            elitismOffset = 1;
        }

        // Crossover population
        // Loop over the new population's size and create individuals from
        // Current population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            // Select parents
            Schedule parent1 = scheduleSelection(pop);
            Schedule parent2 = scheduleSelection(pop);
            // Crossover parents
            Schedule child = crossover(parent1, parent2);
            // Add child to new population
            newPopulation.saveSchedule(i, child);
        }

        // Mutate the new population a bit to add some new genetic material
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
            mutate(newPopulation.getSchedule(i));
        }

        return newPopulation;
    }

    /*
        Applies crossover to a set of parents and creates offspring
     */
    public static Schedule crossover(Schedule parent1, Schedule parent2) {
        // Create new child tour
        Schedule child = new Schedule(GASchedule.RESOURCE);

        // Get start and end sub schedule positions for parent1's schedule
        int startPos = (int) (Math.random() * parent1.size());
        int endPos = (int) (Math.random() * parent1.size());
        if (startPos > endPos) {
            int temp = startPos;
            endPos = startPos;
            startPos = temp;
        }

        // Loop and add the sub schedule from parent1 to our child
        for (int i = 0; i < child.size(); i++) {
            // If our start position is less than the end position
            if (i > startPos && i < endPos) {
                child.setProject(i, parent1.getProject(i));
            }
        }

        // Loop through parent2's project schedule
        for (int i = 0; i < parent2.size(); i++) {
            // If child doesn't have the project add it
            if (!child.containProject(parent2.getProject(i))) {
                // Loop to find a spare position in the child's schedule
                for (int ii = 0; ii < child.size(); ii++) {
                    // Spare position found, add project
                    if (child.getProject(ii) == null) {
                        child.setProject(ii, parent2.getProject(i));
                        break;
                    }
                }
            }
        }

        return child;
    }

    /*
        Mutate a schedule using swap mutation.
     */
    private static void mutate(Schedule schedule) {
        // Loop through projects in schedule
        for(int position1=0; position1 < schedule.size(); position1++){
            // Apply mutation rate
            if(Math.random() < mutationRate){
                // Get a second random position in the schedule
                int position2 = (int) (schedule.size() * Math.random());

                // Get the projects at target position in tour
                Project project1 = schedule.getProject(position1);
                Project project2 = schedule.getProject(position2);

                // Swap them around
                schedule.setProject(position2, project1);
                schedule.setProject(position1, project2);
            }
        }
    }

    // Selects candidate schedule for crossover
    private static Schedule scheduleSelection(Population pop) {
        // Create a crossover population
        Population crossover = new Population(crossoverSize, GASchedule.RESOURCE);
        // For each place in the crossover population get a random candidate schedule and
        // add it
        for (int i = 0; i < crossoverSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            crossover.saveSchedule(i, pop.getSchedule(randomId));
        }
        // Get the fittest tour
        Schedule fittest = crossover.getFittest();
        return fittest;
    }
}
