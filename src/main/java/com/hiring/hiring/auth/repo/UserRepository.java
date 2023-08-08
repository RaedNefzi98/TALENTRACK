package com.hiring.hiring.auth.repo;



@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    User findByResetPasswordToken(String token);

    List<User> findByDisplayName(String displayName);

    User findUserById(String  id);
    User findOnByDisplayName(String displayName);


}

