package knitdiary.knitdiary.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectId;

    private String name;

    @ManyToOne
    @JoinColumn(name = "userId")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "patternId")
    private Pattern pattern;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToMany
    @JoinTable(name = "ProjectYarn", joinColumns = @JoinColumn(name = "projectId"), inverseJoinColumns = @JoinColumn(name = "yarnId"))
    private List<Yarn> projectYarns;

    private String size;
    private String needles;
    private String image;
    private String notes;

    public Project() {

    }

    public Project(String name, AppUser appUser, Pattern pattern, Category category, List<Yarn> projectYarns,
            String size, String needles, String image, String notes) {
        this.name = name;
        this.appUser = appUser;
        this.pattern = pattern;
        this.category = category;
        this.projectYarns = projectYarns;
        this.size = size;
        this.needles = needles;
        this.image = image;
        this.notes = notes;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AppUser getUser() {
        return appUser;
    }

    public void setUser(AppUser user) {
        this.appUser = user;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNeedles() {
        return needles;
    }

    public void setNeedles(String needles) {
        this.needles = needles;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public List<Yarn> getProjectYarns() {
        return projectYarns;
    }

    public void setProjectYarns(List<Yarn> projectYarns) {
        this.projectYarns = projectYarns;
    }

    @Override
    public String toString() {
        return "Project [projectId=" + projectId + ", name=" + name + ", user=" + appUser + ", pattern=" + pattern
                + ", category=" + category + ", size=" + size + ", needles=" + needles + ", image=" + image + ", notes="
                + notes + "]";
    }

}
