package edu.colostate.cs415.model;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import edu.colostate.cs415.dto.WorkerDTO;

public final class Worker {

	public static final int MAX_WORKLOAD = 12;

	private String name;
	private double salary;
	private Set<Project> projects;
	private Set<Qualification> qualifications;

	public Worker(String name, Set<Qualification> qualifications, double salary) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
        }
		if (qualifications == null) {
			throw new IllegalArgumentException("Qualifications cannot be null");
        }
		this.name = name;
		Set<Qualification> newQuals = new HashSet<>();
		newQuals.addAll(qualifications);
		this.qualifications = newQuals;
		this.salary = salary;
		this.projects = new HashSet<>();
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof Worker) {
			if (this.name.equals(((Worker) other).getName())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
    public String toString() {
        return name + ":" + projects.size() + ":" + qualifications.size() + ":" + (int) salary;
    }

	public String getName() {
		return this.name;
	}

	public double getSalary() {
		return this.salary;
	}

	public void setSalary(double salary) {
		if(salary>=0) {
			this.salary = salary;
		}
	}

	public Set<Qualification> getQualifications() {
		Set <Qualification> quals = new HashSet<>();
		for (Qualification w : qualifications){
			quals.add(w);
		}
		return quals;
	}

	public void addQualification(Qualification qualification) {
		this.qualifications.add(qualification);
	}

	public Set<Project> getProjects() {
		Set <Project> projs = new HashSet<>();
		for (Project w : projects){
			projs.add(w);
		}
		return projs;
	}

	public void addProject(Project project) {
		this.projects.add(project);
	}

	public void removeProject(Project project) {
		this.projects.remove(project);
	}

	public int getWorkload() {
		int workload =0;
		for (Project project : projects){
			if (project.getStatus() != ProjectStatus.FINISHED){
				workload += project.getSize().getValue();
			}
		}
		return workload;
	}

	public boolean willOverload(Project project) {
		int projectSize = project.getSize().getValue();
		int workLoadWithNewProject = projectSize + this.getWorkload();
		if(workLoadWithNewProject>12) {
			return true;
		}
		return false;
	}

	public boolean isAvailable() {
		if(getWorkload()<12) {
			return true;
		}
		return false;
	}

	public WorkerDTO toDTO() {
		String[] projectName = projects.stream().map(Project::getName).toArray(String[]:: new);
		String[] qualificationNames = qualifications.stream().map(Qualification::toString).toArray(String[]::new);
		return new WorkerDTO(name, salary, getWorkload(), projectName, qualificationNames);
	}
}
