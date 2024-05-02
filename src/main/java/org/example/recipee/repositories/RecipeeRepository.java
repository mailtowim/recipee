package org.example.recipee.repositories;

import org.example.recipee.domain.Recipee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeeRepository extends JpaRepository<Recipee, String> {
}
