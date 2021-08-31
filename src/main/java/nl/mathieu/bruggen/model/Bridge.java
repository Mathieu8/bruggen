package nl.mathieu.bruggen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder//(toBuilder = true)
@Entity
@Table(name = "bridge")
public class Bridge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @JsonIgnoreProperties("bridge")
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bridgeId"
                                , fetch = FetchType.LAZY)
    private Set<Maintenance> maintenance = new HashSet<>();

    private Boolean needsMaintenance;
}
