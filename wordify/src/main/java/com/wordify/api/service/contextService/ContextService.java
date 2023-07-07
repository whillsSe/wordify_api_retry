package com.wordify.api.service.contextService;

import com.wordify.api.dto.ContextDto;
import com.wordify.api.dto.params.ContextQuery;

public interface ContextService {
  public ContextDto getContext(ContextQuery query);
}
