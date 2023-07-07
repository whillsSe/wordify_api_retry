package com.wordify.api.dao.entry;

import java.sql.Connection;
import java.util.List;

import com.wordify.api.dto.EntryDto;
import com.wordify.api.dto.params.EntryQuery;

public interface EntryDao {
    //EntryDao内で、Entry列挙取得とContext取得のメソッドを実装する
    //理由：共通部分が多い(共にrangeを使用する)等
    public List<EntryDto> getEntry(EntryQuery scope,Connection conn);
}
