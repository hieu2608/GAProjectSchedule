package iss.ci.ga.entity;

import iss.ci.ga.GASchedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

/**
 * Created by HieuTranNgoc on 27/10/16.
 */
public class Schedule {
    private ArrayList<Project> projects;
    private int resource;
    private int fitness = 0;
    private int start = 0;

    public Schedule(ArrayList<Project> projects, int resource) {
        this.projects = projects;
        this.resource = resource;
    }

    public Schedule(int resource) {
        //Construct blank list of projects
        this.projects = new ArrayList<Project>();
        for (int i = 0; i < ProjectList.size(); i++) {
            projects.add(null);
        }
        this.resource = resource;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public int size() {
        return projects.size();
    }

    public boolean containProject(Project project) {
        return projects.contains(project);
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    // Creates a random individual
    public void generateIndividual() {
        // Loop through all our projects and add them to our schedule
        for (int index = 0; index < ProjectList.size(); index++) {
            setProject(index, ProjectList.getProject(index));
        }
        // Randomly reorder the tour
        Collections.shuffle(projects);
    }

    // Gets a project from the schedule
    public Project getProject(int index) {
        if (projects.get(index) != null) {
            return projects.get(index).copy();
        } else {
            return null;
        }
    }

    // Sets a project in a certain position within a schedule
    public void setProject(int index, Project proj) {
        projects.set(index, proj);
        // If the tours been altered we need to reset the fitness and distance
        fitness = 0;
    }

    public int getFitness() {
        if (fitness == 0) {
            int res = resource;
            start = 0;
            Comparator<Project> byEndDate = (Project p1, Project p2) -> Integer.compare(p1.getEnd(), p2.getEnd());
            for (Project proj : projects) {
                while (proj.getEmp() > res) {
                    Optional<Project> next = projects.stream()
                            .filter(f -> f.getEnd() > start)
                            .min(byEndDate);
                    if (!next.isPresent()) {
                        next = projects.stream()
                                .filter(e -> e.getEnd() == start)
                                .min(byEndDate);
                    }
                    start = next.get().getEnd();
                    //Release all resource with same end date as our current start date
                    res = res + projects.stream()
                                            .filter(e -> e.getEnd() == start)
                                            .mapToInt(e -> e.getEmp())
                                            .sum();
                }
                res = res - proj.getEmp();
                proj.setStart(start);
                proj.setEnd(start + proj.getDuration());
            }
            fitness = projects.stream()
                    .max(byEndDate)
                    .get().getEnd();
        }
        return fitness;
    }

    public void print() {
        projects.stream()
                .forEach(e -> System.out.println(e.toString()));
    }
}
