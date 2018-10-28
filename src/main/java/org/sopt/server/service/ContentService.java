package org.sopt.server.service;

import org.sopt.server.dto.Content;
import org.sopt.server.model.DefaultRes;

import java.util.List;

/**
 * Created by ds on 2018-10-23.
 */

public interface ContentService {
    DefaultRes<List<Content>> findAll();

    DefaultRes<Content> findByContentIdx(final int auth, final int contentIdx);

    DefaultRes save(final Content content);

    DefaultRes likes(final int auth, final int contentIdx);

    DefaultRes update(final int contentIdx, final Content content);

    DefaultRes deleteByContentIdx(final int auth, final int contentIdx);
}
