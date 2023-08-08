package com.hiring.hiring.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/user/me")
    public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
        return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getContent() {
        return ResponseEntity.ok("Public content goes here");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_TRAINEE')")
    public ResponseEntity<?> getUserContent() {
        return ResponseEntity.ok("User content goes here");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAdminContent() {
        return ResponseEntity.ok("Admin content goes here");
    }






    @PostMapping("follow/{userId}/{currentUser}")
    public  ResponseEntity  followUser(@PathVariable String userId, @PathVariable String currentUser )
    {
        userService.FollowUser(userId, currentUser);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("unfollow/{userId}/{currentUser}")
    public  ResponseEntity  unfollowUser(@PathVariable("userId") String userId,@PathVariable("currentUser") String currentUser )
    {
        userService.UnFollowUser(userId,currentUser);
        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping("/addUser")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')")
    public String addUser(@RequestBody SignUpRequest request) throws MessagingException {
        return userService.addUser(request);

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/checkMail/{email}")
    public boolean isEmailUnique(@PathVariable("email") String email){

        return userService.verifMail(email);
    }

    @PutMapping("/enableDisable/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ADMIN')")
    public ResponseEntity enableDisableUser (@PathVariable("id") String id){
        userService.enableDisableUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}

