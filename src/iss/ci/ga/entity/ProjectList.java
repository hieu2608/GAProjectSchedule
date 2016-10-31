package iss.ci.ga.entity;

import java.util.ArrayList;

/**
 * Created by HieuTranNgoc on 27/10/16.
 */
public class ProjectList {
    //Hold list of projects
    public static ArrayList<Project> projects = new ArrayList<Project>();
    private static int id;

    //Add project to list
    public static void addProject(Project p) {
        projects.add(p);
    }

    public static void addProject(int duration, int emp){
        id++;
        addProject(new Project(id, duration, emp));
    }

    //Get a project based on index;
    public static Project getProject(int index) {
        return projects.get(index).copy();
    }

    //Get size of project
    public static int size() {
        return projects.size();
    }
}
