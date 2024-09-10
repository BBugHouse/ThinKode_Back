package com.bughouse.thinkode.domain.room.repository;

import com.bughouse.thinkode.domain.room.entity.RoomEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<RoomEntity, String> {
}
