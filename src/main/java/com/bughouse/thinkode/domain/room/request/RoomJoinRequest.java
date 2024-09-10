package com.bughouse.thinkode.domain.room.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoomJoinRequest {

    @NotBlank
    public String name;

    public String password;

    public String questionId;
}
