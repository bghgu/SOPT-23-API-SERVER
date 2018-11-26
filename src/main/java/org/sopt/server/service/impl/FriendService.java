package org.sopt.server.service.impl;

import org.sopt.server.dto.Content;
import org.sopt.server.dto.People;
import org.sopt.server.mapper.ContentMapper;
import org.sopt.server.mapper.PeopleMapper;
import org.sopt.server.model.DefaultRes;
import org.springframework.stereotype.Service;

/**
 * Created by ds on 2018-11-24.
 */

@Service
public class FriendService {

    private final PeopleMapper peopleMapper;

    private final ContentMapper contentMapper;

    public FriendService(final PeopleMapper peopleMapper, final ContentMapper contentMapper) {
        this.peopleMapper = peopleMapper;
        this.contentMapper = contentMapper;
    }

    public People getPeople(final int id) {
        return peopleMapper.findById(id);
    }

    public Content getContent(final int id) {
        return contentMapper.findByContentIdx(id);
    }
}
