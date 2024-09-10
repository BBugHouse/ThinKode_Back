package com.bughouse.thinkode.domain.room.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomEntity {

    public String id;

    /*
    * false : not started
    * true : started
    * */
    @NotNull
    public Boolean status;

    // password가 null이면 어차피 공개방이니깐 일단 주석처리
    /*@NotNull
    public Boolean isPrivate;*/

    @NotBlank
    public String name;

    public String password;

    @NotNull
    public String[] players;

    @NotBlank
    public String questionId;

}
