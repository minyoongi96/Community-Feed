package org.demo.post.repository;

import org.demo.post.repository.entity.post.PostEntity;
import org.demo.post.repository.post_queue.UserPostQueueQueryRepository;
import org.demo.post.ui.dto.GetPostContentResponseDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("test")
public class FakeUserPostQueueQueryRepository implements UserPostQueueQueryRepository {

    private final FakeUserPostQueueRedisRepository fakeUserPostQueueRedisRepository;

    public FakeUserPostQueueQueryRepository(FakeUserPostQueueRedisRepository fakeUserPostQueueRedisRepository) {
        this.fakeUserPostQueueRedisRepository = fakeUserPostQueueRedisRepository;
    }

    @Override
    public List<GetPostContentResponseDto> getPostContentList(Long userId, Long lastPostId) {
        List<PostEntity> postEntityList = fakeUserPostQueueRedisRepository.getPostListByUserId(userId);
        List<GetPostContentResponseDto> result = new ArrayList<>();

        for (PostEntity postEntity : postEntityList) {
            GetPostContentResponseDto res = GetPostContentResponseDto.builder()
                    .id(postEntity.getId())
                    .build();
            result.add(res);
        }
        return result;
    }
}
