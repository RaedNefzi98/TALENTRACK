package com.hiring.hiring.auth.dto;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class SignUpRequest {

    private String userID;

    private String providerUserId;

    @NotEmpty(message="the display name is mandatory")
    @Pattern(regexp="^[A-Za-z][A-Za-z0-9]+$",message="displayName must not begin with a number nor contain special caracters")
    private String displayName;

    @NotEmpty(message="the email is mandatory")
    @Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message="adress email non valid")
    private String email;

    private SocialProvider socialProvider;
    @NotEmpty(message="the password must be not empty")
    @Size(min = 6, message = "{Size.userDto.password}")
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",message="adress password non valid")
    private String password;

    @NotEmpty(message="the matching password must be not empty")
    @Size(min = 6, message = "{Size.userDto.password}")
    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",message="adress matching password non valid")
    private String matchingPassword;

    private String university;
    @NotEmpty(message="the specialty must be not empty")
    private Set<String> specialty=new HashSet<>();

    //private Role roles;
    @NotEmpty(message="the role must be not empty")
    private Set<String> roles;
    @Valid
    private SignUpRequestForbusiness business;






    public SignUpRequest(String providerUserId,
                         @NotEmpty(message = "the display name is mandatory") @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]+$", message = "displayName must not begin with a number nor contain special caracters") String displayName,
                         @NotEmpty(message = "the email is mandatory") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "adress email non valid") String email,
                         SocialProvider socialProvider, @Size(min = 6, message = "{Size.userDto.password}") String password,
                         @NotEmpty Set<String> roles, SignUpRequestForbusiness business,String university, Set<String> specialty) {
        super();
        this.providerUserId = providerUserId;
        this.displayName = displayName;
        this.email = email;
        this.socialProvider = socialProvider;
        this.password = password;
        this.roles = roles;
        this.business = business;
        this.university=university;
        this.specialty=specialty;
    }








    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((matchingPassword == null) ? 0 : matchingPassword.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((providerUserId == null) ? 0 : providerUserId.hashCode());
        result = prime * result + ((socialProvider == null) ? 0 : socialProvider.hashCode());
        result = prime * result + ((userID == null) ? 0 : userID.hashCode());
        result = prime * result + ((userID == null) ? 0 : userID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SignUpRequest other = (SignUpRequest) obj;
        if (displayName == null) {
            if (other.displayName != null)
                return false;
        } else if (!displayName.equals(other.displayName))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (matchingPassword == null) {
            if (other.matchingPassword != null)
                return false;
        } else if (!matchingPassword.equals(other.matchingPassword))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (providerUserId == null) {
            if (other.providerUserId != null)
                return false;
        } else if (!providerUserId.equals(other.providerUserId))
            return false;
        if (socialProvider != other.socialProvider)
            return false;
        if (userID == null) {
            if (other.userID != null)
                return false;
        } else if (!userID.equals(other.userID))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "SignUpRequest [userID=" + userID + ", providerUserId=" + providerUserId + ", displayName=" + displayName
                + ", email=" + email + ", socialProvider=" + socialProvider + ", password=" + password
                + ", matchingPassword=" + matchingPassword + "]";
    }





    public static Builder getBuilder() {
        return new Builder();
    }



    public static class Builder {
        private String providerUserID;
        private String displayName;
        private String email;
        private String password;
        private SocialProvider socialProvider;
        private Set<String> roles;
        private SignUpRequestForbusiness business;
        private String university;
        private Set<String> specialty=new HashSet<>();

        public Builder addProviderUserID(final String userID) {
            this.providerUserID = userID;
            return this;
        }

        public Builder addDisplayName(final String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder addEmail(final String email) {
            this.email = email;
            return this;
        }

        public Builder addPassword(final String password) {
            this.password = password;
            return this;
        }

        public Builder addSocialProvider(final SocialProvider socialProvider) {
            this.socialProvider = socialProvider;
            return this;
        }
        public Builder addRole(final  Set<String>  roles) {
            this.roles = roles;
            return this;
        }
        public Builder addBusiness(final SignUpRequestForbusiness business) {
            this.business = business;
            return this;
        }
        public Builder addSpecialty(final  Set<String>  specialty) {
            this.specialty = specialty;
            return this;
        }
        public Builder addUniversity(final String university) {
            this.university = university;
            return this;
        }
        public SignUpRequest build() {
            return new SignUpRequest(providerUserID,displayName,email,socialProvider,password,roles,business,university,specialty);

        }
    }
}
