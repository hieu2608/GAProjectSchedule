package iss.ci.ga;

import iss.ci.ga.entity.ProjectList;
import iss.ci.ga.entity.Schedule;
import iss.ci.ga.model.GA;
import iss.ci.ga.model.Population;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class GASchedule {
    public final static int RESOURCE = 7;
    public static boolean DEBUG = false;

    public static void main(String[] args) {
        //Load data from CSV file
        String csvFile = Paths.get(".").toAbsolutePath().normalize().toString().concat("/data/project.csv");
        String line = "";
        String cvsSplitBy = ",";
        System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String header = br.readLine();
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] csvLine = line.split(cvsSplitBy);
                ProjectList.addProject(Integer.valueOf(csvLine[0]), Integer.valueOf(csvLine[1]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize population
        Population pop = new Population(25, RESOURCE);
        pop.initialize();
        Schedule fittest = pop.getFittest();
        System.out.println("Inital Duration: " + fittest.getFitness());
        fittest.print();

        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
            pop = GA.evolvePopulation(pop);
        }

        // Print final results
        fittest = pop.getFittest();
        fittest.setFitness(0);
        DEBUG = true;
        System.out.println("\nFinished");
        System.out.println("Final Duration: " + fittest.getFitness());
        System.out.println("Solution:");
        fittest.print();
    }
}
