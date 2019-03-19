package ru.otus.homework.models.dto;

import lombok.*;
import ru.otus.homework.models.Author;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, includeFieldNames = true)
public class AuthorBookIdDto extends AuthorDto
{
    private String bookId;

    public AuthorBookIdDto(Author author)
    {
        super(author);
    }
}
