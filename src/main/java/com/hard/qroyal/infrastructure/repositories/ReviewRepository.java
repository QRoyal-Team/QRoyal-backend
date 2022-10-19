package com.hard.qroyal.infrastructure.repositories;

import com.hard.qroyal.domain.entities.Review;
import com.hard.qroyal.infrastructure.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends GenericRepository<Review> {

}
