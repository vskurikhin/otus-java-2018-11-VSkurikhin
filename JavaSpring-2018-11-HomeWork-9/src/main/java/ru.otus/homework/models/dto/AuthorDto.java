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
    private String id;

    private String firstName;

    private String lastName;

    public AuthorDto(Author author)
    {
        assert author != null;

        id = Long.toString(author.getId());
        firstName = author.getFirstName();
        lastName = author.getLastName();
    }

    public Author updateAuthor(Author author)
    {
        assert author != null;

        author.setFirstName(firstName);
        author.setLastName(lastName);

        return author;
    }

    public Author createAuthor()
    {
        Author author = new Author();
        author.setId(0L);

        return updateAuthor(author);
    }
}
