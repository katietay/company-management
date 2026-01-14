package edu.colostate.cs415.dto;

import java.util.Objects;
import java.util.Arrays;

public class WorkerDTO {
    private String name;
    private double salary;
    private int workload;
    private String[] projects;
    private String[] qualifications;

    public WorkerDTO() {
    }

    public WorkerDTO(String name, double salary, int workload, String[] projects, String[] qualifications) {
        this.name = name;
        this.salary = salary;
        this.workload = workload;
        this.projects = (projects == null) ? null : Arrays.copyOf(projects, projects.length);
        this.qualifications = (qualifications == null) ? null : Arrays.copyOf(qualifications, qualifications.length);
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return this.salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getWorkload() {
        return this.workload;
    }

    public void setWorkload(int workload) {
        this.workload = workload;
    }

    public String[] getProjects() {
        return (projects == null) ? null : Arrays.copyOf(projects, projects.length);
    }


    public void setProjects(String[] projects) {
        this.projects = (projects == null) ? null : Arrays.copyOf(projects, projects.length);
    }

    public String[] getQualifications() {
        return (qualifications == null) ? null : Arrays.copyOf(qualifications, qualifications.length);
    }

    public void setQualifications(String[] qualifications) {
        this.qualifications = (qualifications == null) ? null : Arrays.copyOf(qualifications, qualifications.length);
    }


    public WorkerDTO name(String name) {
        setName(name);
        return this;
    }

    public WorkerDTO salary(double salary) {
        setSalary(salary);
        return this;
    }

    public WorkerDTO workload(int workload) {
        setWorkload(workload);
        return this;
    }

    public WorkerDTO projects(String[] projects) {
        setProjects(projects);
        return this;
    }

    public WorkerDTO qualifications(String[] qualifications) {
        setQualifications(qualifications);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WorkerDTO)) {
            return false;
        }
        WorkerDTO workerDTO = (WorkerDTO) o;
        return Objects.equals(name, workerDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
                ", salary='" + getSalary() + "'" +
                ", workload='" + getWorkload() + "'" +
                ", projects=" + Arrays.toString(getProjects()) +
                ", qualifications=" + Arrays.toString(getQualifications()) +
                "}";
    }

}
