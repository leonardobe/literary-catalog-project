package br.com.literarycatalog.repository;

import br.com.literarycatalog.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByGutendexId(Long gutendexId);

    @Query("""
        SELECT b FROM Book b
        WHERE :language MEMBER OF b.languages
        """)
    List<Book> findByLanguage(String language);

    Optional<Book> findByTitle(String title);

    @Query("""
        SELECT COUNT(b) > 0
        FROM Book b
        JOIN b.authors a
        WHERE LOWER(b.title) = LOWER(:title)
            AND a.name IN :authorNames
        """)
    boolean findByAuthorAndTitleName(@Param("title") String title, @Param("authorNames") List<String> authorNames);
}
