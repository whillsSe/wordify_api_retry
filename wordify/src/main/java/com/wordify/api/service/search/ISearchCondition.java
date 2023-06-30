package com.wordify.api.service.search;

import java.util.List;

public interface ISearchCondition {
    String getRangeSubquery();
    List<String> getParams();
}
