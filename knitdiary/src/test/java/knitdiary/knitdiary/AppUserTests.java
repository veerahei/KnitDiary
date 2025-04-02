package knitdiary.knitdiary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import knitdiary.knitdiary.domain.AppUser;
import knitdiary.knitdiary.domain.AppUserRepository;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AppUserTests {

    @Autowired
    private AppUserRepository auRepository;

    // Test get appusers
    @Test
    public void getAppUsersReturnsAtleastOneAppUser() {
        assertThat(auRepository.count()).isGreaterThan(0);
    }

    // Test save user
    @Test
    public void saveAppUser() {
        AppUser user = new AppUser("TEST", "test", null);
        auRepository.save(user);

        assertThat(user.getUserId()).isNotNull();
    }

    // Test update user
    @Test
    public void updateUserRole() {
        AppUser user = new AppUser("TEST", "test", "TEST");
        auRepository.save(user);

        auRepository.save(user);

        AppUser updatedUser = auRepository.findByUsername("TEST");
        assertThat(updatedUser.getRole()).isEqualTo("TEST");
    }

}
