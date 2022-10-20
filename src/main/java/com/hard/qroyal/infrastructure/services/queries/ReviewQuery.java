package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.domain.entities.Review;
import com.hard.qroyal.infrastructure.repositories.respositories.ReviewRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewQuery extends BaseQuery<Review, ReviewRepository> implements ReviewService {

}
