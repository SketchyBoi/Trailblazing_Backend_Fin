package com.nighthawk.spring_portfolio.mvc.upload;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String fileName;

    @Column
    private String imageEncoder;

    public Upload(String fileName, String imageEncoder) {
        this.fileName = fileName;
        this.imageEncoder = imageEncoder;
    }
    @Column
    private int like;
    @Column
    private int dislike;
}