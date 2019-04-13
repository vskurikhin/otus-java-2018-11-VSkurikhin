package ru.otus.homework.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework.models.Author;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto
{
    private Long id;

    private String firstName;

    private String lastName;

    public AuthorDto(Author author)
    {
        assert author != null;

        id = author.getId();
        firstName = author.getFirstName();
        lastName = author.getLastName();
    }

    public Author updateAuthor(Author author)
    {
        assert author != null;

        author.setId(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);

        return author;
    }

    public Author createAuthor()
    {
        Author author = new Author();

        return updateAuthor(author);
    }
}
