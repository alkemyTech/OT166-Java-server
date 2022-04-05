package com.alkemy.ong.application.service.abstraction;

import com.alkemy.ong.application.rest.response.SlideResponse;
import java.util.List;

public interface IGetSlideService {

  List<SlideResponse> list();

  SlideResponse getBy(Long id);

}
