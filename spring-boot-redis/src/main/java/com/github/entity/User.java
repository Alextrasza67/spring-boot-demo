package com.github.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Created by alex on 2018/5/29.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{

    private String id;

    private String logName;

    private String removed;
}
