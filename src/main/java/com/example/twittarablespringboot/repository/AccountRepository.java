package com.example.twittarablespringboot.repository;

import com.example.twittarablespringboot.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String userName);

}
