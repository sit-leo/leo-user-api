package app.leo.user.repositories;

import app.leo.user.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    Token findByUsernameAndToken(String username,String token);

    @Transactional
    void deleteByUsername(String username);
}
