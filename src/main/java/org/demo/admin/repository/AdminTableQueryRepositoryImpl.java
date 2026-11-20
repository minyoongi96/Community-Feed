package org.demo.admin.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.demo.admin.ui.dto.GetTableListResponse;
import org.demo.admin.ui.dto.posts.GetPostTableRequestDto;
import org.demo.admin.ui.dto.posts.GetPostTableResponseDto;
import org.demo.admin.ui.dto.users.GetUserTableRequestDto;
import org.demo.admin.ui.dto.users.GetUserTableResponseDto;
import org.demo.admin.ui.query.AdminTableQueryRepository;
import org.demo.auth.repository.entity.QUserAuthEntity;
import org.demo.post.repository.entity.post.QPostEntity;
import org.demo.user.repository.entity.QUserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminTableQueryRepositoryImpl implements AdminTableQueryRepository {

    private final JPAQueryFactory queryFactory;
    private static final QUserEntity userEntity = QUserEntity.userEntity;
    private static final QUserAuthEntity userAuthEntity = QUserAuthEntity.userAuthEntity;
    private static final QPostEntity postEntity = QPostEntity.postEntity;

    @Override
    public GetTableListResponse<GetUserTableResponseDto> getUserTableData(GetUserTableRequestDto dto) {

        int total = queryFactory.select(userEntity.id)
                .from(userEntity)
                .where(likeName(dto.getName()))
                .fetch()
                .size();

        List<Long> ids = queryFactory
                .select(userEntity.id)
                .from(userEntity)
                .where(
                        likeName(dto.getName())
                )
                .orderBy(userEntity.id.desc())
                .limit(dto.getLimit())
                .fetch();


        List<GetUserTableResponseDto> result = queryFactory
                .select(
                        Projections.fields(
                                GetUserTableResponseDto.class,
                                userEntity.id.as("id"),
                                userAuthEntity.email.as("email"),
                                userEntity.name.as("name"),
                                userAuthEntity.role.as("role"),
                                userEntity.regDt.as("createdAt"),
                                userEntity.updDt.as("updatedAt"),
                                userAuthEntity.lastLoginDt.as("lastLoginAt")
                        )
                )
                .from(userEntity)
                .join(userAuthEntity).on(userAuthEntity.userId.eq(userEntity.id))
                .where(
                        userEntity.id.in(ids)
                )
                .orderBy(userEntity.id.desc())
                .fetch();

        return new GetTableListResponse<>(total, result);
    }

    @Override
    public GetTableListResponse<GetPostTableResponseDto> getPostTableData(GetPostTableRequestDto dto) {
        int total = queryFactory
                .select(postEntity.id)
                .from(postEntity)
                .where(
                        eqPostId(dto.getPostId())
                )
                .fetch()
                .size();

        List<Long> ids = queryFactory
                .select(postEntity.id)
                .from(postEntity)
                .where(
                        eqPostId(dto.getPostId())
                )
                .orderBy(postEntity.id.desc())
                .offset(dto.getOffset())
                .limit(dto.getLimit())
                .fetch();

        List<GetPostTableResponseDto> result = queryFactory
                .select(Projections.fields(
                        GetPostTableResponseDto.class,
                        postEntity.id.as("postId"),
                        userEntity.id.as("userId"),
                        userEntity.name.as("userName"),
                        postEntity.content.as("content"),
                        postEntity.regDt.as("createdAt"),
                        postEntity.updDt.as("updatedAt")
                ))
                .from(postEntity)
                .join(userEntity).on(postEntity.author.id.eq(userEntity.id))
                .where(
                        postEntity.id.in(ids)
                )
                .orderBy(postEntity.id.desc())
                .fetch();

        return new GetTableListResponse<>(total, result);
    }

    private BooleanExpression likeName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        return userEntity.name.like(name + "%");
    }

    private BooleanExpression eqPostId(Long id) {
        if (id == null) {
            return null;
        }

        return postEntity.id.eq(id);
    }
}
