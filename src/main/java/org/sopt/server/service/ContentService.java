package org.sopt.server.service;

import org.sopt.server.dto.Content;
import org.sopt.server.model.ContentReq;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.Pagination;

import java.util.List;

/**
 * Created by ds on 2018-10-23.
 */

public interface ContentService {
    DefaultRes<List<Content>> findAll(final Pagination pagination);

    DefaultRes<Content> findByContentIdx(final int auth, final int contentIdx);

    DefaultRes save(final ContentReq contentReq);

    DefaultRes likes(final int auth, final int contentIdx);

    DefaultRes update(final int contentIdx, final ContentReq contentReq);

    DefaultRes deleteByContentIdx(final int auth, final int contentIdx);
}
