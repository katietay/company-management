package edu.colostate.cs415.model;

import java.util.Objects;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

import edu.colostate.cs415.dto.ProjectDTO;

public final class Project {

	private String name;
	private ProjectSize size;
	private ProjectStatus status;
	private Set<Worker> workers;
	private Set<Qualification> qualifications;

	public Project(String name, Set<Qualification> qualifications, ProjectSize size) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
        }
		if (qualifications == null) {
			throw new IllegalArgumentException("Qualifications cannot be null");
        }
		if (size == null) {
			throw new IllegalArgumentException("Size cannot be null");
        }
		this.status = ProjectStatus.PLANNED;
		this.name = name;
		Set<Qualification> newQuals = new HashSet<>();
		newQuals.addAll(qualifications);
		this.qualifications = newQuals;
		this.size = size;
		this.workers = new HashSet<>();

	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || !(other instanceof Project)) {
			return false;
		}
		Project project = (Project) other;
		return this.name.equals(project.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return name + ":" + workers.size() + ":" + status.name();
	}

	public String getName() {
		return this.name;
	}

	public ProjectSize getSize() {
		return this.size;
	}

	public ProjectStatus getStatus() {
		return this.status;
	}

	public void setStatus(ProjectStatus status) {
		if (status == null) {
			throw new IllegalArgumentException("Status cannot be null");
        }
		this.status = status;
	}

	public void addWorker(Worker worker) {
		if (worker == null) {
            throw new IllegalArgumentException("Worker cannot be null");
        }
        workers.add(worker);
    }

	public void removeWorker(Worker worker) {
		if (worker == null) {
            throw new IllegalArgumentException("Worker cannot be null");
        }
        workers.remove(worker);
    }

	public Set<Worker> getWorkers() {
		return new HashSet<>(workers);
	}

	public void removeAllWorkers() {
		workers.clear();
	}

	public Set<Qualification> getRequiredQualifications() {
		return new HashSet<>(qualifications);
	}

	public void addQualification(Qualification qualification) {
		if(qualification == null) {
			throw new IllegalArgumentException("Qualification cannot be null");
		}
		qualifications.add(qualification);
	}

	public Set<Qualification> getMissingQualifications() {
		Set<Qualification> missingQualifications = new HashSet<>(qualifications);

		for(Worker worker : workers){
			missingQualifications.removeAll(worker.getQualifications());
		}
		return missingQualifications;
	}

	public boolean isHelpful(Worker worker) {
		if(worker == null){
			throw new IllegalArgumentException("Null Worker!");
		}
		
		Set<Qualification> missingQualifications = getMissingQualifications();
		
		for (Qualification qual : worker.getQualifications()){
			if (missingQualifications.contains(qual)){
				return true;
			}
		}
		return false;
	}

	public ProjectDTO toDTO() {
		String[] workerNames = new String[workers.size()];
		int index = 0;

		for(Worker worker : workers){
			workerNames[index++] = worker.getName();
		}

		String[] qualificationNames = new String[qualifications.size()];
		index = 0;

		for(Qualification qualification : qualifications){
			qualificationNames[index++] = qualification.toString();
		}

		String[] missingQualificationNames = new String[getMissingQualifications().size()];
		index = 0;

		for(Qualification missingQual : getMissingQualifications()){
			missingQualificationNames[index++] = missingQual.toString();
		}
		return new ProjectDTO(this.name, this.size, this.status, workerNames, qualificationNames, missingQualificationNames);
	}
}
