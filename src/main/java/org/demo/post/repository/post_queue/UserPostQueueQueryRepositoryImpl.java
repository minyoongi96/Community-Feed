package org.demo.post.repository.post_queue;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.demo.post.repository.entity.like.QLikeEntity;
import org.demo.post.repository.entity.post.QPostEntity;
import org.demo.post.repository.entity.post.QUserPostQueueEntity;
import org.demo.post.ui.dto.GetPostContentResponseDto;
import org.demo.user.repository.entity.QUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserPostQueueQueryRepositoryImpl implements UserPostQueueQueryRepository {
    private final JPAQueryFactory queryFactory;

    QUserEntity userEntity = QUserEntity.userEntity;
    QPostEntity postEntity = QPostEntity.postEntity;
    QLikeEntity likeEntity = QLikeEntity.likeEntity;
    QUserPostQueueEntity userPostQueueEntity = QUserPostQueueEntity.userPostQueueEntity;

    @Override
    public List<GetPostContentResponseDto> getPostContentList(Long userId, Long lastPostId) {
        return queryFactory
                .select(Projections.fields(
                        GetPostContentResponseDto.class,
                        postEntity.id.as("id"),
                        postEntity.content.as("content"),
                        userEntity.id.as("userId"),
                        userEntity.name.as("userName"),
                        userEntity.profileImageUrl.as("userProfileImage"),
                        postEntity.regDt.as("regDt"),
                        postEntity.updDt.as("updDt"),
                        postEntity.likeCount.as("likeCount"),
                        postEntity.commentCount.as("commentCount"),
                        likeEntity.isNotNull().as("isLikedByMe")
                ))
                .from(userPostQueueEntity)
                .join(userEntity).on(userPostQueueEntity.userId.eq(userEntity.id))
                .join(postEntity).on(userPostQueueEntity.postId.eq(postEntity.id))
                .leftJoin(likeEntity).on(hasLike(userId))
                .where(
                        userPostQueueEntity.userId.eq(userId),
                        hasLastData(lastPostId)
                )
                .orderBy(userPostQueueEntity.postId.desc())
                .limit(20)
                .fetch();
    }

    private BooleanExpression hasLastData(Long lastId) {
        if (lastId == null) {
            return null;
        }

        return postEntity.id.lt(lastId);
    }

    private BooleanExpression hasLike(Long userId) {
        if (userId == null) {
            return null;
        }

        return postEntity.id
                .eq(likeEntity.id.targetId)
                .and(likeEntity.id.targetType.eq("POST"))
                .and(likeEntity.id.userId.eq(userId));
    }

}
