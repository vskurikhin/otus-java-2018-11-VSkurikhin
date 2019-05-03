package ru.otus.homework.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.models.UserProfile;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserProfileDao extends CrudRepository<UserProfile, Long>
{
    Optional<UserProfile> findByLogin(String login);
}
