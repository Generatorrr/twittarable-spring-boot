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

@Entity
@DiscriminatorValue(value = UserType.Values.Redactor)
public class UserRedactor extends User
{
}
