package iss.ci.ga.entity;

/**
 * Created by HieuTranNgoc on 27/10/16.
 */
public class Project {
    private int id;
    private int emp;
    private int start;
    private int end;
    private int duration;

    public Project(int id, int duration, int emp) {
        this.id = id;
        this.emp = emp;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmp() {
        return emp;
    }

    public void setEmp(int emp) {
        this.emp = emp;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Project copy() {
        return new Project(id, duration, emp);
    }

    @Override
    public String toString() {
        return "Project {" +
                "id = " + id +
                ", emp = " + emp +
                ", start = " + start +
                ", end = " + end +
                ", duration = " + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;

        Project project = (Project) o;

        if (id != project.id) return false;
        if (emp != project.emp) return false;
        if (start != project.start) return false;
        if (end != project.end) return false;
        return duration == project.duration;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + emp;
        result = 31 * result + start;
        result = 31 * result + end;
        result = 31 * result + duration;
        return result;
    }
}
