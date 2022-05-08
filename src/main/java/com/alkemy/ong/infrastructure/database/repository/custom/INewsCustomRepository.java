package com.alkemy.ong.infrastructure.database.repository.custom;

import java.util.List;
import javax.persistence.Tuple;

public interface INewsCustomRepository {

  List<Tuple> findByCriteria(Long id);

}
