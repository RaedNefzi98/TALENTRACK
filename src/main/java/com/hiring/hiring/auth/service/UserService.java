package com.hiring.hiring.auth.service;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    public VerificationToken sendVerificationToken(SignUpRequest signUpRequest) throws ServletException, IOException;
    public VerificationToken getVerificationToken(String token);

    public User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

    User findUserByEmail(String email);

    Optional<User> findUserById(String id);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    public User getByResetPasswordToken(String token);

    public void enableDisableUser (String id);

    public void updatePassword(User user, String newPassword);

    public List<User> findAllUsers();

    public List<User> findUsersByDisplayName(String displayName);
    public void FollowUser(String userId, String currentUser);
    public void UnFollowUser(String userId, String currentUser);
    public  User getAuthenticatedUser();
    public User updateUser(User userDetails, String id);
    //public boolean isEmailDomainAssociatedWithCompany(String email) ;
    public void deleteUser(String id);
    public String addUser(SignUpRequest request) throws MessagingException;
    public User updateUserRole(User userDetails, String id,String roleName);
    public User updateUserTotrainer(User userDetails, String id);

    public boolean verifMail(String mail);



    @Transactional(value = "transactionManager")
    User updateUserWithBusiness(String userId, Business business) throws UserNotFoundException;
}

