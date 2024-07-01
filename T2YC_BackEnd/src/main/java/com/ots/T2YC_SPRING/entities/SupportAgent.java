package com.ots.T2YC_SPRING.entities;

import com.ots.T2YC_SPRING.dto.SupportAgentDto;
import com.ots.T2YC_SPRING.dto.SupportAgentMiniDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "support_agent")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SupportAgent implements MessageSender, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "availability" , columnDefinition = "ENUM('AVAILABLE','BUSY','AWAY')")
    @Enumerated(EnumType.STRING)
    private Availabilities availability;
    @ManyToMany(fetch = FetchType.EAGER,
    cascade = {CascadeType.PERSIST,CascadeType.MERGE,
    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
        name="agent_product",
            joinColumns = @JoinColumn(name = "agent_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productThatAgentSupports = new ArrayList<>();
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ratedAgent")
    private List<Rating> ratings;
    @Column(name = "years_of_experience")
    private int yearsOfExperience;
    @Column(name = "role" , columnDefinition = "ENUM('CUSTOMER','SUPPORT_AGENT')")
    @Enumerated(EnumType.STRING)
    private final Role role = Role.SUPPORT_AGENT;
    @Column(name = "is_active")
    private boolean isActive;

    public SupportAgent(SupportAgentDto supportAgentDto) {
        this.id = supportAgentDto.getId();
        this.firstName = supportAgentDto.getFirstName();
        this.lastName = supportAgentDto.getLastName();
        this.email = supportAgentDto.getEmail();
        this.yearsOfExperience = supportAgentDto.getYearsOfExperience();
        this.productThatAgentSupports = supportAgentDto.getProductThatAgentSupports().stream()
                .map(Product::new)
                .toList();
    }
    public SupportAgent(SupportAgentMiniDto supportAgentMiniDto) {
        this.id = supportAgentMiniDto.getId();
        this.firstName = supportAgentMiniDto.getFirstName();
        this.lastName = supportAgentMiniDto.getLastName();
        this.email = supportAgentMiniDto.getEmail();
        this.yearsOfExperience = supportAgentMiniDto.getYearsOfExperience();
    }

    public double averageRating(){
        double avgRating = 0;
        for (Rating rating : ratings){
            avgRating = avgRating + rating.getRate();
        }
        avgRating = avgRating / ratings.size();
        return avgRating;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
