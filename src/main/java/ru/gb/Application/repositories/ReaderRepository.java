package ru.gb.Application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.Application.models.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

}
