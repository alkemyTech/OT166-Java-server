package com.alkemy.ong.infrastructure.database.repository.custom;

import com.alkemy.ong.infrastructure.database.entity.CommentEntity;
import com.alkemy.ong.infrastructure.database.entity.NewsEntity;
import com.alkemy.ong.infrastructure.database.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class INewsCustomRepositoryImpl implements INewsCustomRepository {

  private final EntityManager entityManager;
  private final CriteriaBuilder criteriaBuilder;

  public INewsCustomRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
    this.criteriaBuilder = entityManager.getCriteriaBuilder();
  }

  @Override
  public List<Tuple> findByCriteria(Long id) {
    CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
    Root<NewsEntity> newsRoot = criteriaQuery.from(NewsEntity.class);
    Root<CommentEntity> commentRoot = criteriaQuery.from(CommentEntity.class);

    Join<CommentEntity, NewsEntity> comment = commentRoot.join("news");
    Join<CommentEntity, UserEntity> user = commentRoot.join("user");
    Expression<String> userFullName = getUserFullName(user);

    criteriaQuery.multiselect(
            newsRoot.get("name"),
            commentRoot.get("id"),
            commentRoot.get("body"),
            userFullName,
            commentRoot.get("createTimestamp"))
        .where(getPredicate(newsRoot, id));

    return entityManager.createQuery(criteriaQuery).getResultList();
  }

  private Expression<String> getUserFullName(Join<CommentEntity, UserEntity> joinUser) {
    return criteriaBuilder.concat(joinUser.get("firstName"),
        criteriaBuilder.concat(" ", joinUser.get("lastName")));
  }

  private Predicate getPredicate(Root<NewsEntity> root, Long id) {
    List<Predicate> predicates = new ArrayList<>();
    predicates.add(criteriaBuilder.equal(root.get("softDeleted"), false));
    predicates.add(criteriaBuilder.equal(root.get("id"), id));

    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
  }
}
