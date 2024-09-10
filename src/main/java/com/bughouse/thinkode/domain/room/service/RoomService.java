package com.bughouse.thinkode.domain.room.service;

import com.bughouse.thinkode.domain.room.entity.RoomEntity;
import com.bughouse.thinkode.domain.room.repository.RoomRepository;
import com.bughouse.thinkode.domain.room.request.RoomCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomEntity createRoom(RoomCreateRequest request) {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setName(request.getName());
        roomEntity.setPassword(request.getPassword());
        roomEntity.setQuestionId(request.getQuestionId());
        roomEntity.setStatus(false);
        roomEntity.setPlayers(new String[0]);
        return roomRepository.save(roomEntity);
    }

    public RoomEntity getRoom(String id) {
        return roomRepository.findById(id).orElse(null);
    }

    public List<RoomEntity> getRooms() {
        return roomRepository.findAll();
    }

    public RoomEntity joinRoom(String id, String username) {
        RoomEntity roomEntity = roomRepository.findById(id).orElse(null);
        if (roomEntity == null) {
            return null;
        }
        String[] players = roomEntity.getPlayers();
        if (players.length >= 2) {
            return null;
        }
        String[] newPlayers = new String[players.length + 1];
        System.arraycopy(players, 0, newPlayers, 0, players.length);
        newPlayers[players.length] = username;
        roomEntity.setPlayers(newPlayers);
        return roomRepository.save(roomEntity);
    }
}
