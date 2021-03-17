package com.github.entity;

import lombok.*;

/**
 * Created by alex on 2018/5/29.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User{

    private String id;

    private String logName;

    private String removed;
}
