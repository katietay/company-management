package edu.colostate.cs415.model;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import edu.colostate.cs415.dto.QualificationDTO;


public class Qualification {

    private String description;
    private Set<Worker> workers = new HashSet<>();

    public Qualification(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = description;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false; 
        }
        Qualification that = (Qualification) other;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public String toString() {
        return description;
    }

     public Set<Worker> getWorkers() {
        return Collections.unmodifiableSet(workers);
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
    
    public QualificationDTO toDTO() {
        String[] workerNames = new String[workers.size()];
        int index = 0;
        for (Worker worker : workers) {
            workerNames[index++] = worker.getName(); 
            }
            return new QualificationDTO(description, workerNames);
    }

}