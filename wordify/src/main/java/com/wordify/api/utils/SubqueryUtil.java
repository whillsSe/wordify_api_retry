package com.wordify.api.utils;

import java.util.ArrayList;
import java.util.List;

import com.wordify.api.dto.payloads.EntrySearchPayload;
import com.wordify.api.dto.payloads.ISearchScopePayload;
import com.wordify.api.dto.payloads.params.ICustomParam;
import com.wordify.api.dto.payloads.params.IntParam;
import com.wordify.api.dto.payloads.params.StringParam;
import com.wordify.api.dto.utils.SubqueryResult;

public class SubqueryUtil {
    public static SubqueryResult createSubquery(ISearchScopePayload scope){
        StringBuilder builder = new StringBuilder("SELECT definition_id,user_id AS collector_id FROM ");
        List<ICustomParam> parameter = new ArrayList<>();
        switch(scope.getScope()){
            case "self":
                builder.append("collection c WHERE user_id = ?");
                parameter.add(new IntParam(scope.getUserId()));
            break;
            case "follows":
                builder.append("collection c WHERE user_id IN (SELECT follwing FROM follows WHERE follower = ?)");
                parameter.add(new IntParam(scope.getUserId()));
                break;
            case "specific":
                builder.append("collection c WHERE user_id = ?");
                parameter.add(new IntParam(scope.getScopeId()));//本当なら、そのユーザの投稿の閲覧権限があるのかを確認する必要がありそう。
            break;
            case "dictionary":
                builder.append("dictionary d WHERE id = ?");
                parameter.add(new IntParam(scope.getScopeId()));
            break;
        }
        return new SubqueryResult(builder.toString(),parameter);
    }
    public static SubqueryResult createEntryWhereClause(EntrySearchPayload query){
        //wordで検索なのかphoneticで検索なのか、タグも併用するのかを分析する必要
        //「かつ」のみの実装とする？全部ひらがなの時に「または」が必要か…
        //ひとまず、「word」or「phonetic」かつ「tag」とする！
        //word,phonetic,tagの順に参照して、あるものから代入して、次のものは接続詞つけて…
        StringBuilder builder = new StringBuilder(" WHERE ");
        int count = 0;
        List<ICustomParam> params = new ArrayList<>();
        String wordString = query.getWordString();
        String phoneticString = query.getPhoneticString();
        List<String> tagsStrings = query.getTagsStrings();

        if(!wordString.isEmpty()){
            builder.append("w.word LIKE ?");
            StringBuilder param = new StringBuilder(wordString);
            SQLUtils.appendPercentSymbol(param);
            params.add(new StringParam(param.toString()));
            count++;
        }
        if(!phoneticString.isEmpty()){
            if(count != 0) builder.append(" AND ");
            builder.append("p.phonetic LIKE ?");
            StringBuilder param = new StringBuilder(phoneticString);
            SQLUtils.appendPercentSymbol(param);
            params.add(new StringParam(param.toString()));
            count++;
        }
        if(!tagsStrings.isEmpty()){
            if(count != 0) builder.append(" AND ");
            builder.append("d.id IN (SELECT definition_id FROM definitions_tags dt JOIN tags t ON dt.tag_id = t.id WHERE tag IN (");
            SQLUtils.prepareQueryForElements(tagsStrings.size(), builder);
            builder.append(")");
            builder.append(" GROUP BY dt.definition_id HAVING COUNT(DISTINCT t.tag) = ");
            builder.append(tagsStrings.size());
            builder.append(")");
            for(String tag : tagsStrings){
                params.add(new StringParam(tag));
            }
        }
        return new SubqueryResult(builder.toString(),params); 
    }
}
