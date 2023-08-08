package com.hiring.hiring.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public VerificationToken sendVerificationToken(SignUpRequest signUpRequest) {


        // Générer un jeton de vérification unique
        String token = UUID.randomUUID().toString();
        // Enregistrer le jeton de vérification dans la base de données
        VerificationToken verificationToken = new VerificationToken(signUpRequest, token);
        verificationToken.setId(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        // Construire le message e-mail
        String subject = "Mail Verification";
        String link = "http://localhost:8000/api/auth/verify-email/"+token ;

        Context context = new Context();
        context.setVariable("link", link);
        String htmlContent = templateEngine.process("verification-email", context);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("c@gmail.com");
            helper.setTo(signUpRequest.getEmail());
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
	      /*String text = "<p>Hello,</p>"
		            + "<p>This process is to verify your Email.</p>"
		            + "<p>Click the link below to change your password:</p>"
		            + "<p><a href=\"" + link + "\">Verify my email</a></p>"
		            + "<br>"
		            + "<p>Ignore this email if you have not made the request. </p>";

	      SimpleMailMessage message = new SimpleMailMessage();
	      message.setFrom("siwarzrelli180959@gmail.com");
	      message.setTo(signUpRequest.getEmail());
	      message.setSubject(subject);
	      message.setText(text);*/
            // Envoyer l'e-mail
            javaMailSender.send(message);
            // Retourner le jeton de vérification
            return verificationToken;
        } catch (MessagingException e) {
            // Log the error

            throw new RuntimeException("Failed to send email", e);
        }
    }


    @Override
    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }


    @Override
    @Transactional(value = "transactionManager")
    public User registerNewUser(final SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException {
        if (signUpRequest.getUserID() != null && userRepository.existsById(signUpRequest.getUserID())) {
            throw new UserAlreadyExistAuthenticationException("User with User id " + signUpRequest.getUserID() + " already exist");
        } else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistAuthenticationException("User with email id " + signUpRequest.getEmail() + " already exist");
        }
        User user = buildUser(signUpRequest);
        Date now = Calendar.getInstance().getTime();
        user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        user.setCreatedDate(now);
        user.setModifiedDate(now);
        user = userRepository.save(user);
        //userRepository.flush();
        return user;
    }




    public User buildUser(final SignUpRequest formDTO) {

        User user = new User();


        final HashSet<Role> roles = new HashSet<Role>();


        user.setDisplayName(formDTO.getDisplayName());

        user.setProvider(formDTO.getSocialProvider().getProviderType());
        user.setEnabled(true);
        user.setProviderUserId(formDTO.getProviderUserId());


        Set<String> strRoles =  formDTO.getRoles();

        Set<String> specialitiesArray  =  formDTO.getSpecialty();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(Role.ROLE_TRAINEE);

            user.setDisplayName(formDTO.getDisplayName());
            user.setUniversity(formDTO.getUniversity());

            user.setSpecialty(specialitiesArray);
            user.setEmail(formDTO.getEmail());
            user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "a&dmin":
                        Role adminRole = roleRepository.findByName(Role.ROLE_ADMIN);
                        user.setEmail(formDTO.getEmail());
                        user.setPassword(passwordEncoder.encode(formDTO.getPassword()));

                        roles.add(adminRole);

                        user.setBusiness(null);


                        break;
                    case "superadmin":
                        Role superadminRole = roleRepository.findByName(Role.ROLE_SUPER_ADMIN);

                        user.setEmail(formDTO.getEmail());
                        user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
                        roles.add(superadminRole);

                        user.setBusiness(null);


                        break;



                    case "trainerRole":
                        Role trainerRole = roleRepository.findByName(Role.ROLE_TRAINER);

                        user.setEmail(formDTO.getEmail());
                        user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
                        user.setUniversity(formDTO.getUniversity());

                        user.setSpecialty(specialitiesArray);
                        roles.add(trainerRole);

                        user.setBusiness(null);


                        break;

                    case "trainee":
                        Role traineeRole = roleRepository.findByName(Role.ROLE_TRAINEE);

                        user.setEmail(formDTO.getEmail());
                        user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
                        user.setUniversity(formDTO.getUniversity());

                        user.setSpecialty(specialitiesArray);
                        roles.add(traineeRole);
                        user.setBusiness(null);


                        break;
                    case "Recruiter":
                        Role recruterRole = roleRepository.findByName(Role.ROLE_RECRUITER);


                        user.setEmail(formDTO.getEmail());
                        user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
                        Business business=new Business();
                        business.setAddress(formDTO.getBusiness().getAddress());
                        business.setCompanyName(formDTO.getBusiness().getCompanyName());
                        business.setCountry(formDTO.getBusiness().getCountry());
                        business.setDescription(formDTO.getBusiness().getDescription());
                        business.setFirstName(formDTO.getBusiness().getFirstName());
                        business.setIndustry(formDTO.getBusiness().getIndustry());
                        business.setLastName(formDTO.getBusiness().getLastName());
                        business.setLogo(formDTO.getBusiness().getLogo());
                        business.setNbEmployee(formDTO.getBusiness().getNbEmployee());
                        business.setPhone(formDTO.getBusiness().getPhone());
                        business.setRecrutementRole(formDTO.getBusiness().getRecrutementRole());
                        business.setWebsite(formDTO.getBusiness().getWebsite());

                        business.setVerified(isEmailDomainAssociatedWithCompany(formDTO.getEmail()));

                        user.setUniversity(formDTO.getUniversity());

                        user.setSpecialty(specialitiesArray);

                        user.setBusiness(business);


                        roles.add(recruterRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(Role.ROLE_TRAINEE);

                        user.setEmail(formDTO.getEmail());
                        user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
                        user.setUniversity(formDTO.getUniversity());
                        user.setSpecialty(specialitiesArray);
                        roles.add(userRole);

                        user.setBusiness(null);

                }
            });
        }




        //end code


        user.setRoles(roles);

        return user;
    }
    public String[] getFreeEmailProviders() {
        return new String[] {"gmail.com", "hotmail.com", "yahoo.com", "outlook.com", "aol.com", "protonmail.com", "mail.com", "zoho.com", "icloud.com", "yandex.com", "gmx.com", "tutanota.com", "mail.ru", "rediffmail.com", "inbox.com", "hushmail.com", "fastmail.com", "startmail.com", "runbox.com", "lavabit.com"};
    }

    public boolean isEmailDomainAssociatedWithCompany(String email) {
        String[] emailParts = email.split("@");
        String domain = emailParts[1];
        boolean verif = true;
        String[] freeEmailProviders = getFreeEmailProviders();

        for (String provider : freeEmailProviders) {
            if (domain.equalsIgnoreCase(provider)) {
                verif = false;
                System.out.println(provider+" "+domain);
            }

        }

        return verif;

    }


    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Transactional
    public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
        if (StringUtils.isEmpty(oAuth2UserInfo.getName())) {
            throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
        } else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        SignUpRequest userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
        User user = findUserByEmail(oAuth2UserInfo.getEmail());
        if (user != null) {
            if (!user.getProvider().equals(registrationId) && !user.getProvider().equals(SocialProvider.LOCAL.getProviderType())) {
                throw new OAuth2AuthenticationProcessingException(
                        "Looks like you're signed up with " + user.getProvider() + " account. Please use your " + user.getProvider() + " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(userDetails);
        }

        return LocalUser.create(user, attributes, idToken, userInfo);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setDisplayName(oAuth2UserInfo.getName());
        return userRepository.save(existingUser);
    }

    private SignUpRequest toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
        return SignUpRequest.getBuilder().addProviderUserID(oAuth2UserInfo.getId()).addDisplayName(oAuth2UserInfo.getName()).addEmail(oAuth2UserInfo.getEmail())
                .addSocialProvider(GeneralUtils.toSocialProvider(registrationId)).addPassword("changeit").build();
    }

    @Override
    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public List<User> findUsersByDisplayName(String displayName) {
        return userRepository.findByDisplayName(displayName);
    }


    @Override

    public  User getAuthenticatedUser() {
        String currName = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(currName);
        System.out.println("currName");
        return userRepository.findOnByDisplayName(currName);
    }


    @Transactional
    @Override
    public void FollowUser(String userIds, String currentUser )
    {
        User curUserObj= userRepository.findUserById(currentUser);
        curUserObj.addToFollowingList(userIds);
        User user = userRepository.findById(userIds).orElseThrow(()-> new ExpressionException("Invalid User - " + userIds));
        user.addToFollowersList(currentUser);
        userRepository.save(curUserObj);
        userRepository.save(user);

    }

    @Override
    public void UnFollowUser(String userIds , String currentUser )
    {
        User curUserObj= userRepository.findUserById(currentUser);
        curUserObj.removefromfollowedList(userIds);

        User user = userRepository.findById(userIds).orElseThrow(()-> new ExpressionException("Invalid User - " + userIds));
        user.removeFromFollowersList(currentUser);
        userRepository.save(curUserObj);
        userRepository.save(user);

    }

    public User updateUser(User userDetails, String id) {

        User user = userRepository.findUserById(id);
        if (!userDetails.getDisplayName().isEmpty()){
            user.setDisplayName(userDetails.getDisplayName());
        }
        if (!userDetails.getEmail().isEmpty()){
            user.setEmail(userDetails.getEmail());
        }
        if (!userDetails.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        if (!userDetails.getRoles().isEmpty()){
            user.setRoles(userDetails.getRoles());
        }
        if (!userDetails.getSpecialty().isEmpty() ){
            user.setSpecialty(userDetails.getSpecialty());
        }
        if (!userDetails.getUniversity().isEmpty() ){
            user.setUniversity(userDetails.getUniversity());
        }
        if (user.getRoles().contains(roleRepository.findByName(Role.ROLE_RECRUITER))){
            Business business=new Business();
            business.setAddress(userDetails.getBusiness().getAddress());
            business.setCompanyName(userDetails.getBusiness().getCompanyName());
            business.setCountry(userDetails.getBusiness().getCountry());
            business.setDescription(userDetails.getBusiness().getDescription());
            business.setFirstName(userDetails.getBusiness().getFirstName());
            business.setIndustry(userDetails.getBusiness().getIndustry());
            business.setLastName(userDetails.getBusiness().getLastName());
            business.setLogo(userDetails.getBusiness().getLogo());
            business.setNbEmployee(userDetails.getBusiness().getNbEmployee());
            business.setPhone(userDetails.getBusiness().getPhone());
            business.setRecrutementRole(userDetails.getBusiness().getRecrutementRole());
            business.setWebsite(userDetails.getBusiness().getWebsite());
            // Vérifier si le nom de domaine est associé à une entreprise
            business.setVerified(isEmailDomainAssociatedWithCompany(userDetails.getEmail()));



            user.setBusiness(business);
        }
        return userRepository.save(user);
    }
    //this method for update user to became trainer
    public User updateUserTotrainer(User userDetails, String id) {
        User user = userRepository.findById(id).get();
        try {
            user.setUniversity(userDetails.getUniversity());
            Set<String> specialitiesArray  =  userDetails.getSpecialty();
            user.setSpecialty(specialitiesArray);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("error"+e);
        }



        //user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }


    //this function to verif if the role in array roles or not
    public boolean containsRoleName(Set<Role> roles, String roleName) {
        return roles.stream().anyMatch(role -> role.getName().equals(roleName));
    }


    public User updateUserRole(User userDetails, String id,String roleName) {

        User user = userRepository.findById(id).get();

        if (roleName != null) {
            switch (roleName) {
                case "ROLE_TRAINER":
                    Role role = roleRepository.findByName(Role.ROLE_TRAINER);
                    System.out.println("role"+role);
                    if (!containsRoleName(user.getRoles(), roleName)) {
                        user.getRoles().add(role);
                    }
                    break;
                case "ROLE_TRAINEE":
                    Role role1 = roleRepository.findByName(Role.ROLE_TRAINEE);
                    System.out.println("role"+role1);

                    if (!containsRoleName(user.getRoles(), roleName)) {
                        user.getRoles().add(role1);
                    }
                    break;

                case "ROLE_RECRUITER":
                    Role role2 = roleRepository.findByName(Role.ROLE_RECRUITER);
                    System.out.println("role"+role2);

                    if (!containsRoleName(user.getRoles(), roleName)) {
                        user.getRoles().add(role2);
                    }
                    break;

            }}





        //user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }






    @Override
    public void deleteUser(String id) {

        User user = userRepository.findById(id).get();
        userRepository.delete(user);
    }

    @Override
    public String addUser(SignUpRequest request) throws MessagingException {

        User user = buildUser(request);
        Date now = Calendar.getInstance().getTime();
        user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        user.setCreatedDate(now);
        user.setModifiedDate(now);
        userRepository.save(user);
        // Send an email to the user
        String subject = "Welcome ";
        Context context = new Context();
        context.setVariable("displayName", user.getDisplayName());
        context.setVariable("username", user.getEmail());
        context.setVariable("password", request.getPassword());

        // Generate the email text from the template
        String emailText = templateEngine.process("credentials.html", context);
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("c@gmail.com");
            helper.setTo(request.getEmail());
            helper.setSubject(subject);
            helper.setText(emailText, true);

            javaMailSender.send(message);

            return user.getId();
        } catch (MessagingException e) {
            // Log the error

            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public boolean verifMail(String email) {
        // TODO Auto-generated method stub
        User user = userRepository.findByEmail(email);
        return user == null;
    }


    @Override
    @Transactional(value = "transactionManager")
    public User updateUserWithBusiness(String userId, Business business) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setBusiness(business);

        return userRepository.save(user);
    }
    @Override
    public void enableDisableUser (String id){
        User user = userRepository.findUserById(id);
        if (user.isEnabled()) {
            user.setEnabled(false);
        }else{
            user.setEnabled(true);
        }
        userRepository.save(user);

    }


}

