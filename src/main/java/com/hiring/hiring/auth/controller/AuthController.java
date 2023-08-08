package com.hiring.hiring.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @GetMapping("verify-email/{token}")
    public String verifyEmail(@PathVariable("token") String token) {
        // Vérifier si le jeton de vérification est valide et actif
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            return "Le jeton de vérification est invalide ou expiré";
        }
        // Activer le compte utilisateur
        userService.registerNewUser(verificationToken.getSignUpRequest());
        // Rediriger l'utilisateur vers la page de connexion
        return "Votre adresse e-mail a été vérifiée avec succès. Veuillez vous connecter.";
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        LocalUser localUser = (LocalUser) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, GeneralUtils.buildUserInfo(localUser)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws ServletException, IOException {
        VerificationToken verificationToken;
        try {
            // Envoyer un e-mail de vérification à l'utilisateur
            verificationToken = userService.sendVerificationToken(signUpRequest);
        } catch (UserAlreadyExistAuthenticationException e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }
        // Retourner une réponse avec le jeton de vérification
        return ResponseEntity.ok(verificationToken);
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request) throws UserNotFoundException, UnsupportedEncodingException, MessagingException {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        userService.updateResetPasswordToken(token, email);
        String resetPasswordLink = "http://localhost:4200/reset?token=" + token;
        sendEmail(email, resetPasswordLink);
        System.out.println(resetPasswordLink);
        return token;
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") String id){
        Optional<User> user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "Reset Password Request";
        Context context = new Context();
        context.setVariable("link", link);

        // Generate the email text from the template
        String emailText = templateEngine.process("reset.html", context);
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("c@gmail.com");
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(emailText, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            // Log the error

            throw new RuntimeException("Failed to send email", e);
        }
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.getByResetPasswordToken(token);
        userService.updatePassword(user, password);
        return "message";
    }

    @GetMapping("/get_user")
    public  User getAuthenticatedUser() {
        return userService.getAuthenticatedUser();
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllEmployees () {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/validateToken/{token}")
    public ResponseEntity<Boolean> verifyToken(@PathVariable String  token) {
        return  new ResponseEntity<>( tokenProvider.validateToken(token),HttpStatus.OK);
    }

    @GetMapping("/search/{displayName}")
    public  ResponseEntity<List<User>> getUserByDisplayName(@PathVariable("displayName") String displayName){
        List<User> users = userService.findUsersByDisplayName(displayName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateEmployee(@RequestBody User userDetails, @PathVariable("id") String id) {
        User updateUser = userService.updateUser(userDetails, id);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
    @PutMapping("/updatespecialty/{id}")
    public ResponseEntity<User> updateUserTotrainer(@RequestBody User userDetails, @PathVariable String id) {
        User updateUser = userService.updateUserTotrainer(userDetails, id);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }


    @PutMapping("/updaterole/{id}")
    public ResponseEntity<User> updateEmployeeRole(@RequestBody User userDetails, @PathVariable String id,String roleName) {
        User updateUser = userService.updateUserRole(userDetails, id,roleName);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
    @PostMapping("follow/{userId}/{currentUser}")
    public  ResponseEntity<Void>  followUser(@PathVariable String userId, @PathVariable String currentUser )
    {
        userService.FollowUser(userId, currentUser);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("unfollow/{userId}/{currentUser}")
    public  ResponseEntity<Void>  unfollowUser(@PathVariable String userId,@PathVariable String currentUser )
    {
        userService.UnFollowUser(userId,currentUser);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/verifMail/{email}")
    public boolean  verifMail(@PathVariable("email") String email)  {
        return userService.verifMail(email);
    }


    @PutMapping("/{userId}/business")
    public ResponseEntity<User> updateUserWithBusiness(@PathVariable String userId, @RequestBody Business business) {
        try {
            User updatedUser = userService.updateUserWithBusiness(userId, business);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

