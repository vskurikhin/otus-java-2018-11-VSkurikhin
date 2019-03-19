package ru.otus.homework.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.homework.models.Author;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto
{
    private String id;

    private String firstName;

    private String lastName;

    public AuthorDto(Author author)
    {
        assert author != null;

        id = author.getId() == null ? null : author.getId().toString();
        firstName = author.getFirstName();
        lastName = author.getLastName();
    }

    public Author updateAuthor(Author author)
    {
        assert author != null;

        author.setId(id == null ? null : new BigInteger(id));
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
