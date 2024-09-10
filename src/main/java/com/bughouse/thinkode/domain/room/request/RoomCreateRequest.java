package com.bughouse.thinkode.domain.room.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoomCreateRequest {

    @NotBlank
    public String name;

    public String password;

    public String questionId;
}
