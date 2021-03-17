package com.github.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by alex on 2018/5/29.
 */
@Entity
@Table(name = "TB_USER")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @GeneratedValue(generator = "generator")
    private String id;

    @Column(name = "LOG_NAME")
    private String logName;

    @Column(name = "REMOVED")
    private String removed;
}
