package app.leo.user.repositories;

import app.leo.user.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RefreshTokenRepository  extends JpaRepository<RefreshToken,Long> {

    RefreshToken findByUsername(String username);
}
