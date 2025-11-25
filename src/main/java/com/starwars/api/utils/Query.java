package com.starwars.api.utils;

public class Query
{
    public static final String FIND_BY_USERNAME_AND_PASSWORD = "SELECT * FROM user WHERE username = ?1 AND password = ?2";
}
