package knitdiary.knitdiary.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Pattern {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long patternId;

    private String name;
    private String designer;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pattern")
    private List<Project> projects;

    public Pattern() {

    }

    public Pattern(String name, String designer) {
        this.name = name;
        this.designer = designer;
    }

    public Long getPatternId() {
        return patternId;
    }

    public void setPatternId(Long patternId) {
        this.patternId = patternId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Pattern [patternId=" + patternId + ", name=" + name + ", designer=" + designer + "]";
    }

}
