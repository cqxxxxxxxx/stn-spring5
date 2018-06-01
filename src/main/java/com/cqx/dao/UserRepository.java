package com.cqx.dao;

import com.cqx.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Created by BG307435 on 2017/8/14.
 */
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {


}
