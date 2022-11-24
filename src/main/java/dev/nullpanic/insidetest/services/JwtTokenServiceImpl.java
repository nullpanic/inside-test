package dev.nullpanic.insidetest.services;

import dev.nullpanic.insidetest.exceptions.GeneralException;
import dev.nullpanic.insidetest.persist.models.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class JwtTokenServiceImpl implements JwtTokenService {
    @Value("${jwt.accessToken.ExpTime}")
    private String tokenExpTime;
    @Value("${jwt.key}")
    private String jwtKey;

    @Override
    public String getToken(User user, String secret) throws GeneralException {

        Map<String, Object> tokenData = new HashMap<>();

        tokenData.put("userId", user.getId().toString());
        tokenData.put("name", user.getName());
        tokenData.put("token_create_date", new Date().getTime());
        tokenData.put("token_expiration_date", new Date().getTime() + Long.parseLong(tokenExpTime));

        Calendar calendar = Calendar.getInstance();

        JwtBuilder jwtBuilder = Jwts.builder()
                .setExpiration(calendar.getTime())
                .setClaims(tokenData);

        return jwtBuilder.signWith(SignatureAlgorithm.HS512, jwtKey).compact();
    }

    @Override
    public Boolean tokenIsValid(User user, String token) throws GeneralException {
        token = splitToken(token);

        return token.equals(user.getToken());
    }


    @Override
    public String splitToken(String token) throws GeneralException {
        String[] splitArray = token.split("_");

        if (splitArray.length < 2) {
            throw new GeneralException("Invalid token", HttpStatus.UNAUTHORIZED);
        }
        return splitArray[1];
    }
}
