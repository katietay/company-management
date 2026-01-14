package edu.colostate.cs415.model;

import java.util.HashSet;
import java.util.Set;

public final class Company {

	private String name;
	private Set<Worker> employees;
	private Set<Worker> available;
	private Set<Worker> assigned;
	private Set<Project> projects;
	private Set<Qualification> qualifications;

	public Company(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		this.name = name;

		 // Initialize the employees set so getEmployedWorkers() isn't null
        this.employees = new HashSet<>();
		this.qualifications = new HashSet<>();
		this.available = new HashSet<>();
		this.assigned = new HashSet<>();
		this.projects = new HashSet<>();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || getClass() != other.getClass()) {
			return false;
		} else {
			Company comp = (Company) other;
			return this.name.equals(comp.getName());
		}
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		int availableWorkers = 0;
		availableWorkers = this.getAvailableWorkers().size();
		int projectCount = 0;
		projectCount = this.projects.size();
		return String.format("%s:%d:%d", this.name, availableWorkers, projectCount);
	}

	public String getName() {
		return name;
	}

	public Set<Worker> getEmployedWorkers() {
		Set<Worker> employed = new HashSet<>();
		for (Worker w : employees) {
			employed.add(w);
		}
		return employed; 
    }

	public Set<Worker> getAvailableWorkers() {
		Set<Worker> availableWorkers = new HashSet<>();
		for (Worker w : employees) {
			if (w != null && w.isAvailable()) {
				availableWorkers.add(w);
			}
		}
		return availableWorkers;
	}

	public Set<Worker> getUnavailableWorkers() {
		Set <Worker> unavailable = new HashSet<>();
		for (Worker w : employees){
			if (w != null && !w.isAvailable()){
				unavailable.add(w);
			}

		}
		return unavailable;
	
	}

	public Set<Worker> getAssignedWorkers() {
		Set<Worker> assignedWorkers = new HashSet<>();
		for (Worker w: assigned){
			if(w != null && !w.getProjects().isEmpty()){
				assignedWorkers.add(w);
			}
		}
		return assignedWorkers;
	}

	public Set<Worker> getActualAssignedWorkers() {
		Set <Worker> actualUnassigned = new HashSet<>();
		for (Worker w : assigned){
			actualUnassigned.add(w);
		}
		return actualUnassigned;
	}

	public Set<Worker> getUnassignedWorkers() {
		Set<Worker> unassigned = new HashSet<>();
        for (Worker w : employees) {
            if (w != null && w.getProjects().isEmpty()) {
                unassigned.add(w);
            }
        }
        return unassigned;
	}

	public Set<Project> getProjects() {
		return new HashSet<>(projects);
	}

	public Set<Qualification> getQualifications() {
		Set <Qualification> quals = new HashSet<>();
		for (Qualification w : qualifications){
			quals.add(w);
		}
		return quals;
	}

	public Worker createWorker(String name, Set<Qualification> qualifications, double salary) {
		if (name == null || name.trim().isEmpty()) {return null;}
		if (qualifications == null || qualifications.isEmpty() || this.qualifications.isEmpty()) {return null;}
		if (!this.qualifications.containsAll(qualifications)) {return null;}
		if (salary < 0) {return null;}

		Worker newWorker = new Worker(name, qualifications, salary);
		employees.add(newWorker);
		available.add(newWorker);
		for (Qualification companyQualification : this.qualifications) {
			if (qualifications.contains(companyQualification)) {
				companyQualification.addWorker(newWorker);
			}
		}
		return newWorker;
	}

	public Qualification createQualification(String description) {
		if (description == null || description.trim().isEmpty()){
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        Qualification qaual = new Qualification(description);
        qualifications.add(qaual);
        return qaual;
	}

	public Project createProject(String name, Set<Qualification> qualifications, ProjectSize size) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Project name cannot be null or empty");
		}
		if (qualifications == null || qualifications.isEmpty()){
			throw new IllegalArgumentException("Qualification set cannot be null or empty");
		}
		if (size == null ){
			throw new IllegalArgumentException("Project Size cannot be null");
		}
		if (!this.qualifications.containsAll(qualifications)) {
			throw new IllegalArgumentException("Some qualifications are not present in the company");
		}
		Project newProject = new Project(name, new HashSet<>(), size);
		
		for (Qualification q : qualifications) {
			newProject.addQualification(q);
		}
		projects.add(newProject);
		return newProject;
		
		}
	

	public void start(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Null is not a project");
		}
		if (project.getStatus().equals(ProjectStatus.ACTIVE) || project.getStatus().equals(ProjectStatus.FINISHED)) {
			return;
		}
		if(!project.getMissingQualifications().isEmpty()) {
			return;
		}
		project.setStatus(ProjectStatus.ACTIVE);
	}

	public void finish(Project project) {
		if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        // Only ACTIVE projects are finished.
		// others remain unchanged.
        if (!project.getStatus().equals(ProjectStatus.ACTIVE)) {
            return;
        }
        //  the set of workers assigned to this project.
        Set<Worker> assignedWorkers = new HashSet<>(project.getWorkers());
        // For each worker, remove the project from their project list.
        for (Worker w : assignedWorkers) {
            w.removeProject(project);
            // If a worker now has no projects, update company's assigned and available sets.
            if (w.getProjects().isEmpty()) {
                assigned.remove(w);
                available.add(w);
            }
        }
        
        project.removeAllWorkers();
        project.setStatus(ProjectStatus.FINISHED);
	}

	public void assign(Worker worker, Project project) {
		if (worker == null || project == null) {
			throw new IllegalArgumentException("Company.assign(): Neither worker nor project can be null");
		}
		if (!getAvailableWorkers().contains(worker)) {
			return;
		}
		if (project.getStatus().equals(ProjectStatus.ACTIVE) || project.getStatus().equals(ProjectStatus.FINISHED)) {
			return;
		}
		if (!project.isHelpful(worker)) {
			return;
		}
		
		worker.addProject(project);
		project.addWorker(worker);
		assigned.add(worker);
	}

	public void unassign(Worker worker, Project project) {
		if (worker == null || project == null) {
			throw new IllegalArgumentException("Company.unassign(): Neither worker nor project can be null");
		}
		if (!project.getWorkers().contains(worker)) {
			return;
		}

		worker.removeProject(project);
		project.removeWorker(worker);
		if (worker.getWorkload() == 0) {assigned.remove(worker);}
		available.add(worker);
		

		if (project.getStatus() == ProjectStatus.ACTIVE && !project.getMissingQualifications().isEmpty()) {
			project.setStatus(ProjectStatus.SUSPENDED);
		}
	}

	public void unassignAll(Worker worker) {
		if (worker == null) {
            throw new IllegalArgumentException("Worker cannot be null");
        }
        
        Set<Project> projectsAssigned = new HashSet<>(worker.getProjects());
    
        for (Project project : projectsAssigned) {
            unassign(worker, project);
        }
      
        assigned.remove(worker);
	}
}
