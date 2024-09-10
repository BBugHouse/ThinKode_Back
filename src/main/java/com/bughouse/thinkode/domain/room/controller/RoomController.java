package com.bughouse.thinkode.domain.room.controller;

import com.bughouse.thinkode.domain.room.entity.RoomEntity;
import com.bughouse.thinkode.domain.room.request.RoomCreateRequest;
import com.bughouse.thinkode.domain.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("")
    public ResponseEntity<List<RoomEntity>> getRooms() {
        return ResponseEntity.ok(roomService.getRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomEntity> getRoom(@PathVariable String id) {
        RoomEntity room = roomService.getRoom(id);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }

    @PostMapping("")
    public ResponseEntity<RoomEntity> createRoom(@RequestBody RoomCreateRequest request) {
        RoomEntity result = roomService.createRoom(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<RoomEntity> joinRoom(@PathVariable String id, @RequestBody RoomJoinRequest request) {
        RoomEntity result = roomService.joinRoom(id);
        return ResponseEntity.ok(result);
    }

}
