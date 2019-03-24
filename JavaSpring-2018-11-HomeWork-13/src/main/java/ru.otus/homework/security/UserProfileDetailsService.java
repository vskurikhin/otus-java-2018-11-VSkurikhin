package ru.otus.homework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.UserProfileDao;
import ru.otus.homework.models.UserProfile;

import java.util.Optional;

@Service
public class UserProfileDetailsService implements UserDetailsService
{
    private final UserProfileDao userProfileDao;

    @Autowired
    public UserProfileDetailsService(UserProfileDao userProfileDao)
    {
        this.userProfileDao = userProfileDao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {
        Optional<UserProfile> optionalUser = userProfileDao.findByLogin(login);
        if ( ! optionalUser.isPresent()) {
            throw new UsernameNotFoundException(login);
        }

        return new UserProfileDetails(optionalUser.get());
    }
}
