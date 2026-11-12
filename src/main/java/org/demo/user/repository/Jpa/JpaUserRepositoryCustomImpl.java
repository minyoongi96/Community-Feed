package org.demo.user.repository.Jpa;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.demo.user.application.dto.GetUserListResponseDto;
import org.demo.user.repository.entity.QUserEntity;
import org.demo.user.repository.entity.QUserRelationEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaUserRepositoryCustomImpl implements JpaUserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    QUserEntity user = QUserEntity.userEntity;
    QUserRelationEntity userRelation = QUserRelationEntity.userRelationEntity;

    @Override
    public List<GetUserListResponseDto> getFollowingUserList(Long userId, Long lastFollowingId) {
        return queryFactory
                .select(Projections.constructor(
                        GetUserListResponseDto.class,
                        user.name,
                        user.profileImageUrl
                ))
                .from(userRelation)
                .join(user).on(userRelation.followingUserId.eq(userId))
                .where(
                        userRelation.followingUserId.eq(userId),
                        hasLastData(lastFollowingId)
                )
                .orderBy(user.id.desc())
                .limit(20)
                .fetch();
    }

    @Override
    public List<GetUserListResponseDto> getFollowerUserList(Long userId, Long lastFollowerId) {
        return queryFactory
                .select(Projections.constructor(
                        GetUserListResponseDto.class,
                        user.name,
                        user.profileImageUrl
                ))
                .from(userRelation)
                .join(user).on(userRelation.followerUserId.eq(userId))
                .where(
                        userRelation.followerUserId.eq(userId),
                        hasLastData(lastFollowerId)
                )
                .orderBy(user.id.desc())
                .limit(20)
                .fetch();
    }

    private BooleanExpression hasLastData(Long lastId) {
        if (lastId == null){
            return null;
        }
        return user.id.lt(lastId);
    }
}
