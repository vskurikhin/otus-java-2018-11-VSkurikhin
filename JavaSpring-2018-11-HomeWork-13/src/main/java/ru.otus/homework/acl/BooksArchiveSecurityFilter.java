package ru.otus.homework.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.otus.homework.models.Book;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BooksArchiveSecurityFilter
{
    private AclService aclService;

    @Autowired
    public BooksArchiveSecurityFilter(AclService aclService)
    {
        this.aclService = aclService;
    }

    private boolean filter(List<Sid> sids, Book book)
    {
        List<Permission> permissions = Collections.singletonList(BasePermission.READ);
        ObjectIdentity oid = new ObjectIdentityImpl(Book.class, book.getId());
        Acl acl;

        try {
            acl = aclService.readAclById(oid);
        } catch (NotFoundException e) {
            return true; // Not only in the archive.
        }

        return acl.isGranted(permissions, sids, false);
    }

    public boolean filterBooks(Authentication authentication, List<Book> books)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Stream<Sid> head = Stream.of(new PrincipalSid(userDetails.getUsername()));
        Stream<Sid> grantedSids = userDetails.getAuthorities().stream().map(GrantedAuthoritySid::new);
        List<Sid> sids = Stream.concat(head, grantedSids).collect(Collectors.toList());

        books.removeIf(book -> ! filter(sids, book));

        return true;
    }
}
