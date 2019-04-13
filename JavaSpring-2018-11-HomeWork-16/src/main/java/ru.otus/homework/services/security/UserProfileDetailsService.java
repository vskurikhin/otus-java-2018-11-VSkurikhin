package ru.otus.homework.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.UserProfileDao;
import ru.otus.homework.models.UserProfile;
import ru.otus.homework.security.UserProfileDetails;

import java.util.Optional;

@Service
public class UserProfileDetailsService implements UserDetailsService
{
    private final UserProfileDao userProfileDao;

    @Autowired
    public UserProfileDetailsService(UserProfileDao dao)
    {
        userProfileDao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {
        Optional<UserProfile> mayBeUser = userProfileDao.findByLogin(login);

        return new UserProfileDetails(mayBeUser.orElseThrow(() -> new UsernameNotFoundException(login)));
    }
}
