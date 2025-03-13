package knitdiary.knitdiary.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Yarn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long yarnId;

    @ManyToMany(mappedBy = "projectYarns")
    private List<Project> projects;

    private String name;
    private String brand;
    private String color;

    // Added maybe later
    // @ManyToOne
    // @JoinColumn(name = "weightId")
    // private Weight weight;

    public Yarn() {

    }

    public Yarn(String name, String brand, String color) {
        this.name = name;
        this.brand = brand;
        this.color = color;

    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Yarn [yarnId=" + yarnId + ", project=" + ", name=" + name + ", brand=" + brand + ", color="
                + color + "]";
    }

}
