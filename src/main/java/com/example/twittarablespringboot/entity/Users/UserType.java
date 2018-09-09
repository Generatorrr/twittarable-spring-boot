/*
 * Developed for Epson Europe BV by Softeq Development Corporation.
 * http://www.softeq.com
 */

package com.example.twittarablespringboot.entity.Users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * TODO: write a brief summary fragment.
 * <p>
 * TODO: write a detailed description.
 * <p>
 * Created on 9/7/18.
 * <p>
 * @author vmartinkov
 */
public enum UserType
{

    REDACTOR(Values.Redactor), VIEWER(Values.Viewer);

    private String value;

    UserType(String value)
    {
        if (!this.name().equals(value))
        {
            throw new IllegalArgumentException("Incorrect use of LocationType");
        }
        this.value = value;
    }

    public static class Values
    {
        public static final String Redactor = "Redactor";

        public static final String Viewer = "Viewer";
    }
    
}
