package org.demo.post.repository.post_queue;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.demo.post.repository.entity.comment.QCommentEntity;
import org.demo.post.repository.entity.like.QLikeEntity;
import org.demo.post.repository.entity.post.QPostEntity;
import org.demo.post.repository.entity.post.QUserPostQueueEntity;
import org.demo.post.ui.dto.GetContentResponseDto;
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
    QCommentEntity commentEntity = QCommentEntity.commentEntity;
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
                .join(postEntity).on(userPostQueueEntity.postId.eq(postEntity.id))
                .join(userEntity).on(userPostQueueEntity.authorId.eq(userEntity.id))
                .leftJoin(likeEntity).on(hasLikePost(userId))
                .where(
                        userPostQueueEntity.userId.eq(userId),
                        hasLastData(lastPostId)
                )
                .orderBy(userPostQueueEntity.postId.desc())
                .limit(20)
                .fetch();
    }

    @Override
    public List<GetContentResponseDto> getCommentContentList(Long userId, Long postId) {
        return queryFactory
                .select(Projections.fields(
                        GetContentResponseDto.class,
                        commentEntity.id.as("id"),
                        commentEntity.content.as("content"),
                        userEntity.id.as("userId"),
                        userEntity.name.as("userName"),
                        userEntity.profileImageUrl.as("userProfileImage"),
                        commentEntity.regDt.as("regDt"),
                        commentEntity.updDt.as("updDt"),
                        commentEntity.likeCount.as("likeCount"),
                        likeEntity.isNotNull().as("isLikedByMe")
                ))
                .from(commentEntity)
                .join(userEntity).on(userEntity.id.eq(userId))
                .leftJoin(likeEntity).on(hasLikeComment(userId))
                .where(
                        commentEntity.post.id.eq(postId)
                )
                .orderBy(commentEntity.regDt.asc())
                .fetch();
    }

    private BooleanExpression hasLastData(Long lastId) {
        if (lastId == null) {
            return null;
        }

        return postEntity.id.lt(lastId);
    }

    private BooleanExpression hasLikePost(Long userId) {
        if (userId == null) {
            return null;
        }

        return postEntity.id
                .eq(likeEntity.id.targetId)
                .and(likeEntity.id.targetType.eq("POST"))
                .and(likeEntity.id.userId.eq(userId));
    }

    private BooleanExpression hasLikeComment(Long userId) {
        if (userId == null) {
            return null;
        }

        return commentEntity.id
                .eq(likeEntity.id.targetId)
                .and(likeEntity.id.targetType.eq("COMMENT"))
                .and(likeEntity.id.userId.eq(userId));
    }

}
