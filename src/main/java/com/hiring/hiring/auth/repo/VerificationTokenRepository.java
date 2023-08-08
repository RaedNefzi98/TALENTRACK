package com.hiring.hiring.auth.repo;


public interface VerificationTokenRepository extends MongoRepository<VerificationToken, Long>{

    VerificationToken findByToken(String token);

}
}
