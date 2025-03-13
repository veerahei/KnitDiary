/*
 * package knitdiary.knitdiary.domain;
 * 
 * import java.util.List;
 * 
 * import jakarta.persistence.CascadeType;
 * import jakarta.persistence.Entity;
 * import jakarta.persistence.GeneratedValue;
 * import jakarta.persistence.GenerationType;
 * import jakarta.persistence.Id;
 * import jakarta.persistence.OneToMany;
 * 
 * @Entity
 * public class Weight {
 * 
 * @Id
 * 
 * @GeneratedValue(strategy = GenerationType.AUTO)
 * private Long weightId;
 * 
 * private String weight;
 * 
 * @OneToMany(cascade = CascadeType.ALL, mappedBy = "yarnId")
 * private List<Yarn> yarns;
 * 
 * public Weight() {
 * 
 * }
 * 
 * public Weight(String weight) {
 * this.weight = weight;
 * }
 * 
 * public Long getWeightId() {
 * return weightId;
 * }
 * 
 * public void setWeightId(Long weightId) {
 * this.weightId = weightId;
 * }
 * 
 * public String getWeight() {
 * return weight;
 * }
 * 
 * public void setWeight(String weight) {
 * this.weight = weight;
 * }
 * 
 * @Override
 * public String toString() {
 * return "Weight [weightId=" + weightId + ", weight=" + weight + "]";
 * }
 * 
 * }
 */