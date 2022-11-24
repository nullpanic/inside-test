package dev.nullpanic.insidetest.services;

import dev.nullpanic.insidetest.exceptions.GeneralException;
import dev.nullpanic.insidetest.persist.models.User;

public interface JwtTokenService {

    String getToken(User User, String secret) throws GeneralException;

    Boolean tokenIsValid(User user, String token) throws GeneralException;

    String splitToken(String token) throws GeneralException;
}
