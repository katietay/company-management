package edu.colostate.cs415.dto;

import java.util.Objects;
import java.util.Arrays;


public class QualificationDTO {
    private String description;
    private String[] workers;

    public QualificationDTO() {
    }

    public QualificationDTO(String description, String[] workers) {
        this.description = description;
        this.workers = (workers == null) ? null : Arrays.copyOf(workers, workers.length);
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getWorkers() {
        return (workers == null) ? null : Arrays.copyOf(this.workers, this.workers.length);
    }

    public void setWorkers(String[] workers) {
        this.workers = (workers == null) ? null : Arrays.copyOf(workers, workers.length);

    }

    public QualificationDTO description(String description) {
        setDescription(description);
        return this;
    }

    public QualificationDTO workers(String[] workers) {
        setWorkers(workers);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof QualificationDTO)) {
            return false;
        }
        QualificationDTO qualificationDTO = (QualificationDTO) o;
        return Objects.equals(description, qualificationDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return "{" +
                " description='" + getDescription() + "'" +
                ", workers=" + Arrays.toString(getWorkers()) +
                "}";
    }

}
