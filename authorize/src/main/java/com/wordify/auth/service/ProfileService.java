package com.wordify.auth.service;

import com.wordify.auth.dto.InitializeInfo;

public interface ProfileService {
    InitializeInfo getInitializeInfo(int userId) throws Exception;
}
