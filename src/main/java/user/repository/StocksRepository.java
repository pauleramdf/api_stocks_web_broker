package user.repository;

import user.model.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long> {
}
