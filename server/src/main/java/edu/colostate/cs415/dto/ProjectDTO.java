package edu.colostate.cs415.dto;

import java.util.Objects;
import java.util.Arrays;

import edu.colostate.cs415.model.ProjectSize;
import edu.colostate.cs415.model.ProjectStatus;

public class ProjectDTO {
    private String name;
    private ProjectSize size;
    private ProjectStatus status;
    private String[] workers;
    private String[] qualifications;
    private String[] missingQualifications;

    public ProjectDTO() {
    }

    public ProjectDTO(String name, ProjectSize size, ProjectStatus status, String[] workers, String[] qualifications,
            String[] missingQualifications) {
        this.name = name;
        this.size = size;
        this.status = status;
        this.workers = (workers == null) ? null : Arrays.copyOf(workers, workers.length);
        this.qualifications = (qualifications == null) ? null : Arrays.copyOf(qualifications, qualifications.length);
        this.missingQualifications = (missingQualifications == null) ? null : Arrays.copyOf(missingQualifications, missingQualifications.length);
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectSize getSize() {
        return this.size;
    }

    public void setSize(ProjectSize size) {
        this.size = size;
    }

    public ProjectStatus getStatus() {
        return this.status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public String[] getWorkers() {
        return (workers == null) ? null : Arrays.copyOf(workers, workers.length);
    }


    public void setWorkers(String[] workers) {
        this.workers = (workers == null) ? null : Arrays.copyOf(workers, workers.length);
    }

    public String[] getQualifications() {
         return (qualifications == null) ? null : Arrays.copyOf(qualifications, qualifications.length);
    }

    public void setQualifications(String[] qualifications) {
        this.qualifications = (qualifications == null) ? null : Arrays.copyOf(qualifications, qualifications.length);
    }

    public String[] getMissingQualifications() {
        return (missingQualifications == null) ? null : Arrays.copyOf(missingQualifications, missingQualifications.length);
    }

    public void setMissingQualifications(String[] missingQualifications) {
        this.missingQualifications = (missingQualifications == null) ? null : Arrays.copyOf(missingQualifications, missingQualifications.length);
    }

    public ProjectDTO name(String name) {
        setName(name);
        return this;
    }

    public ProjectDTO size(ProjectSize size) {
        setSize(size);
        return this;
    }

    public ProjectDTO status(ProjectStatus status) {
        setStatus(status);
        return this;
    }

    public ProjectDTO workers(String[] workers) {
        setWorkers(workers);
        return this;
    }

    public ProjectDTO qualifications(String[] qualifications) {
        setQualifications(qualifications);
        return this;
    }

    public ProjectDTO missingQualifications(String[] missingQualifications) {
        setMissingQualifications(missingQualifications);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProjectDTO)) {
            return false;
        }
        ProjectDTO projectDTO = (ProjectDTO) o;
        return Objects.equals(name, projectDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
                ", size='" + getSize() + "'" +
                ", status='" + getStatus() + "'" +
                ", workers=" + Arrays.toString(getWorkers()) +
                ", qualifications=" + Arrays.toString(getQualifications()) +
                ", missingQualifications=" + Arrays.toString(getMissingQualifications()) +
                "}";
    }

}
